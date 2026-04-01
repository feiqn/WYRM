package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.triggers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;

public class WyrCutsceneTrigger<
        T extends WyrActor
            > implements Wyr {

    public enum CSTriggerType {
        AREA,
        TURN,
        DEATH,
        COMBAT_START,
        COMBAT_END,
        OTHER_CUTSCENE,
        CAMPAIGN_FLAG,
    }

    protected boolean hasFired   = false;
    protected boolean isCompound = false; // Requires 2 or more conditions to be met simultaneously.
    protected boolean defused    = false; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.

    protected int defuseThreshold = 1;
    protected int defuseCount = 0;

    protected CSTriggerType TriggerType;

    protected CampaignFlags triggerFlag;

    protected final Array<UnitIDRoster> triggerUnits    = new Array<>();
    protected final Array<Vector2> triggerAreas         = new Array<>();
    protected final Array<Integer> triggerTurns         = new Array<>();
    protected final Array<CutsceneID> triggerCutscenes  = new Array<>();
    protected final Array<WyrCutsceneTrigger<T>> defuseTriggers = new Array<>();

    protected boolean requiresTeamAlignment = false;
    protected boolean requiresAggressor     = false;
    protected boolean exactTurn             = false; // Should turn trigger fire only on exact turns or any turn greater than?

    protected TeamAlignment requiredTeamAlignment = TeamAlignment.PLAYER;

    public WyrCutsceneTrigger(CampaignFlags triggerFlag) {
        this.TriggerType = CSTriggerType.CAMPAIGN_FLAG;
        this.triggerFlag = triggerFlag;
        // TODO: can broaden the scope on this to include a flag array later if need arrises
    }

    public WyrCutsceneTrigger(Integer turnToTrigger, boolean exactTurn) {
        this.TriggerType = CSTriggerType.TURN;
        this.exactTurn = exactTurn;
        triggerTurns.add(turnToTrigger);
    }

    public WyrCutsceneTrigger(UnitIDRoster rosterID, boolean beforeCombat, boolean requiresAggressor) {
        if(beforeCombat) {
            this.TriggerType = CSTriggerType.COMBAT_START;
        } else {
            this.TriggerType = CSTriggerType.COMBAT_END;
        }
        this.requiresAggressor = requiresAggressor;
        triggerUnits.add(rosterID);
    }

    public WyrCutsceneTrigger(UnitIDRoster attacker, UnitIDRoster defender, boolean beforeCombat) {
        isCompound = true;
        if(beforeCombat) {
            this.TriggerType = CSTriggerType.COMBAT_START;
        } else {
            this.TriggerType = CSTriggerType.COMBAT_END;
        }
        triggerUnits.add(attacker, defender);
    }

    public WyrCutsceneTrigger(UnitIDRoster deathOf) {
        this.TriggerType = CSTriggerType.DEATH;
        triggerUnits.add(deathOf);
    }

    public WyrCutsceneTrigger(TeamAlignment deathOf) {
        this.TriggerType = CSTriggerType.DEATH;

        requiresTeamAlignment = true;
        requiredTeamAlignment = deathOf;
    }

    public WyrCutsceneTrigger(CutsceneID otherID) {
        this.TriggerType = CSTriggerType.OTHER_CUTSCENE;
        triggerCutscenes.add(otherID);
    }

    public WyrCutsceneTrigger(UnitIDRoster rosterID, Array<Vector2> areas) {
        isCompound = true;
        this.TriggerType = CSTriggerType.AREA;
        triggerUnits.add(rosterID);
        for(Vector2 vector : areas) {
            triggerAreas.add(vector);
        }
    }

    public WyrCutsceneTrigger(UnitIDRoster rosterID, Vector2 area) {
        isCompound = true;
        this.TriggerType = CSTriggerType.AREA;
        triggerUnits.add(rosterID);
        triggerAreas.add(area);
    }

    public WyrCutsceneTrigger(Vector2 area) {
        this.TriggerType = CSTriggerType.AREA;
        triggerAreas.add(area);
    }

    public WyrCutsceneTrigger(Vector2 area, TeamAlignment requiredTeamAlignment) {
        this.TriggerType = CSTriggerType.AREA;
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

    protected void incrementDefuseCount() {
        if(defused) return;
        defuseCount++;
        if(defuseCount >= defuseThreshold) defused = true;
    }

    public void setDefuseThreshold(int i) {
        defuseThreshold = i;
    }

    public boolean hasFired() {
        if(defused) return false;
        return hasFired;
    }

    public void fire() {
        if(defused) return;
        hasFired = true;
    }

    public void addDefuseTrigger(WyrCutsceneTrigger<T> trigger) {
        if(!defuseTriggers.contains(trigger,true)) defuseTriggers.add(trigger);
    }

    /**
     * CHECKERS (gotta eat)
     */
    public boolean checkCampaignFlagTrigger(CampaignFlags flag) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.TriggerType != CSTriggerType.CAMPAIGN_FLAG) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkDeathTrigger(UnitIDRoster roster) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.TriggerType != CSTriggerType.DEATH) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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
        if(this.TriggerType != CSTriggerType.DEATH) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkAreaTrigger(UnitIDRoster rosterID, Vector2 tileCoordinate) {
        if(defused) return false;
        if(!isCompound) return false;
        if(hasFired) return false;
        if(this.TriggerType != CSTriggerType.AREA) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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
        if(this.TriggerType != CSTriggerType.AREA) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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
        if(this.TriggerType != CSTriggerType.TURN) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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
        if(this.TriggerType != CSTriggerType.OTHER_CUTSCENE) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkCombatStartTrigger(UnitIDRoster rosterID, boolean unitIsAggressor) {
        // This will trigger if the unit fights anyone.
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.requiresAggressor && !unitIsAggressor) return false;
        if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
        if(this.TriggerType != CSTriggerType.COMBAT_START) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkCombatStartTrigger(UnitIDRoster attacker, UnitIDRoster defender) {
        // This will only trigger if two specific units fight each other. (Regardless of who starts it.)
        if(defused) return false;
        if(hasFired) return false;
        if(this.TriggerType != CSTriggerType.COMBAT_START) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkCombatEndTrigger(UnitIDRoster rosterID, boolean unitIsAggressor) {
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.requiresAggressor && !unitIsAggressor) return false;
        if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
        if(this.TriggerType != CSTriggerType.COMBAT_END) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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

    public boolean checkCombatEndTrigger(UnitIDRoster attacker, UnitIDRoster defender) {
        if(defused) return false;
        if(hasFired) return false;
        if(this.TriggerType != CSTriggerType.COMBAT_END) return false;

        for(WyrCutsceneTrigger<T> def : defuseTriggers) {
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
