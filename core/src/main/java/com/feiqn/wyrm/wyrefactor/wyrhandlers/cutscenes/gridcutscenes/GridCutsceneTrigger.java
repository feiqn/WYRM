package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneTrigger;

public class GridCutsceneTrigger extends WyrCutsceneTrigger {

    public enum GridCSTriggerType {
        AREA,
        TURN,
        DEATH,
        COMBAT_START,
        COMBAT_END,
        OTHER_CUTSCENE,
        CAMPAIGN_FLAG,
    }

    protected GridCSTriggerType gridCSTriggerType;

    protected boolean requiresTeamAlignment;
    protected boolean requiresAggressor;
    protected boolean exactTurn; // Should turn trigger fire only on exact turns or any turn greater than?

    protected TeamAlignment requiredTeamAlignment;

    protected CampaignFlags triggerFlag; // TODO: consider abstracting this to WyrCSTrigger

    protected final Array<UnitRoster> triggerUnits;
    protected final Array<Vector2> triggerAreas;
    protected final Array<Integer> triggerTurns;
    protected final Array<CutsceneID> triggerCutscenes;
    protected final Array<GridCutsceneTrigger> defuseTriggers;

    public GridCutsceneTrigger(CampaignFlags triggerFlag) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.CAMPAIGN_FLAG;
        this.triggerFlag = triggerFlag;
        // TODO: can broaden the scope on this to include a flag array later if need arrises
    }

    public GridCutsceneTrigger(Integer turnToTrigger, boolean exactTurn) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.TURN;
        this.exactTurn = exactTurn;
        triggerTurns.add(turnToTrigger);
    }

    public GridCutsceneTrigger(UnitRoster rosterID, boolean beforeCombat, boolean requiresAggressor) {
        this();
        if(beforeCombat) {
            this.gridCSTriggerType = GridCSTriggerType.COMBAT_START;
        } else {
            this.gridCSTriggerType = GridCSTriggerType.COMBAT_END;
        }
        this.requiresAggressor = requiresAggressor;
        triggerUnits.add(rosterID);
    }

    public GridCutsceneTrigger(UnitRoster attacker, UnitRoster defender, boolean beforeCombat) {
        this();
        isCompound = true;
        if(beforeCombat) {
            this.gridCSTriggerType = GridCSTriggerType.COMBAT_START;
        } else {
            this.gridCSTriggerType = GridCSTriggerType.COMBAT_END;
        }
        triggerUnits.add(attacker, defender);
    }

    public GridCutsceneTrigger(UnitRoster deathOf) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.DEATH;
        triggerUnits.add(deathOf);
    }

    public GridCutsceneTrigger(TeamAlignment deathOf) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.DEATH;

        requiresTeamAlignment = true;
        requiredTeamAlignment = deathOf;
    }

    public GridCutsceneTrigger(CutsceneID otherID) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.OTHER_CUTSCENE;
        triggerCutscenes.add(otherID);
    }

    public GridCutsceneTrigger(UnitRoster rosterID, Array<Vector2> areas) {
        this();
        isCompound = true;
        this.gridCSTriggerType = GridCSTriggerType.AREA;
        triggerUnits.add(rosterID);
        for(Vector2 vector : areas) {
            triggerAreas.add(vector);
        }
    }

    public GridCutsceneTrigger(UnitRoster rosterID, Vector2 area) {
        this();
        isCompound = true;
        this.gridCSTriggerType = GridCSTriggerType.AREA;
        triggerUnits.add(rosterID);
        triggerAreas.add(area);
    }

    public GridCutsceneTrigger(Vector2 area) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.AREA;
        triggerAreas.add(area);
    }

    public GridCutsceneTrigger(Vector2 area, TeamAlignment requiredTeamAlignment) {
        this();
        this.gridCSTriggerType = GridCSTriggerType.AREA;
        isCompound = true;
        this.requiredTeamAlignment = requiredTeamAlignment;
        this.requiresTeamAlignment = true;

        triggerAreas.add(area);
    }

    /*
     Other constructor ideas:
     - deathOf TeamAlignment
     - combatBy TeamAlignment
     - combatBy two specific TeamAlignments (i.e., enemy and other)
     */

    protected GridCutsceneTrigger() {
        super(WyrType.GRIDWORLD);

        triggerUnits     = new Array<>();
        triggerAreas     = new Array<>();
        triggerTurns     = new Array<>();
        triggerCutscenes = new Array<>();
        defuseTriggers   = new Array<>();

        requiresTeamAlignment = false;
        requiresAggressor     = false;

        requiredTeamAlignment = TeamAlignment.PLAYER;
    }

    public void addDefuseTrigger(GridCutsceneTrigger trigger) {
        if(!defuseTriggers.contains(trigger,true)) defuseTriggers.add(trigger);
    }

    /**
     * CHECKERS (gotta eat)
     */
    public boolean checkCampaignFlagTrigger(CampaignFlags flag) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.CAMPAIGN_FLAG) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;

            if(def.checkCampaignFlagTrigger(flag)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(this.triggerFlag == flag) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkDeathTrigger(UnitRoster roster) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.DEATH) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;

            if(def.checkDeathTrigger(roster)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(this.triggerUnits.contains(roster, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkDeathTrigger(TeamAlignment alignment) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(!requiresTeamAlignment) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.DEATH) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;

            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(requiredTeamAlignment == alignment) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkAreaTrigger(UnitRoster rosterID, Vector2 tileCoordinate) {
        if(defused) return false;
        if(!isCompound) return false;
        if(hasFired) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.AREA) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        boolean found = false;

        for(Vector2 vector : triggerAreas) {
            if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                found = true;
                break;
            }
        }

        if(found && triggerUnits.contains(rosterID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkAreaTrigger(Vector2 tileCoordinate, TeamAlignment unitsAlignment) {
        if(defused) return false;
        if(hasFired) return false;
        if(requiresTeamAlignment && unitsAlignment != requiredTeamAlignment) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.AREA) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }
        if(defused) return false;

        boolean found = false;

        for(Vector2 vector : triggerAreas) {
            if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                found = true;
                break;
            }
        }

        if(found && requiredTeamAlignment == unitsAlignment) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkTurnTrigger(int turn) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.TURN) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(exactTurn) {
            if(triggerTurns.contains(turn, true)) {
                hasFired = true;
                return true;
            }
        } else {
            if(turn >= triggerTurns.get(0)) {
                hasFired = true;
                return true;
            }
        }

        return false;
    }

    public boolean checkOtherCutsceneTrigger(CutsceneID otherID) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.OTHER_CUTSCENE) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerCutscenes.contains(otherID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatStartTrigger(UnitRoster rosterID, boolean unitIsAggressor) {
        // This will trigger if the unit fights anyone.
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.requiresAggressor && !unitIsAggressor) return false;
        if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.COMBAT_START) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerUnits.contains(rosterID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatStartTrigger(UnitRoster attacker, UnitRoster defender) {
        // This will only trigger if two specific units fight each other. (Regardless of who starts it.)
        if(defused) return false;
        if(hasFired) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.COMBAT_START) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerUnits.contains(attacker, true) &&
           triggerUnits.contains(defender, true)) {

            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatEndTrigger(UnitRoster rosterID, boolean unitIsAggressor) {
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.requiresAggressor && !unitIsAggressor) return false;
        if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.COMBAT_END) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerUnits.contains(rosterID, true)) {

            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatEndTrigger(UnitRoster attacker, UnitRoster defender) {
        if(defused) return false;
        if(hasFired) return false;
        if(this.gridCSTriggerType != GridCSTriggerType.COMBAT_END) return false;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerUnits.contains(attacker, true) &&
           triggerUnits.contains(defender, true)) {

            hasFired = true;
            return true;
        }

        return false;
    }

}
