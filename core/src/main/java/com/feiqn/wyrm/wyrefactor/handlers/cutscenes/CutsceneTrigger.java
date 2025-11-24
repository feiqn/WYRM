package com.feiqn.wyrm.wyrefactor.handlers.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class CutsceneTrigger {

    public enum Type {
        AREA,
        TURN,
        DEATH,
        COMBAT_START,
        COMBAT_END,
        OTHER_CUTSCENE
    }

    protected boolean hasFired;
    protected boolean isCompound; // Requires 2 or more conditions to be met simultaneously.
    protected boolean defused; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.
    protected boolean requiresTeamAlignment;

    protected TeamAlignment requiredTeamAlignment;

    protected Type type;

    protected final Array<UnitRoster> triggerUnits;
    protected final Array<Vector2> triggerAreas;
    protected final Array<Integer> triggerTurns;
    protected final Array<CutsceneID> triggerCutscenes;
    protected final Array<CutsceneTrigger> defuseTriggers;

    protected int defuseThreshold;
    protected int defuseCount;

    public CutsceneTrigger(Integer turnToTrigger) {
        this();
        this.type = Type.TURN;
        triggerTurns.add(turnToTrigger);
    }

    public CutsceneTrigger(UnitRoster rosterID, boolean beforeCombat) {
        this();
        if(beforeCombat) {
            this.type = Type.COMBAT_START;
        } else {
            this.type = Type.COMBAT_END;
        }
        triggerUnits.add(rosterID);
    }

    public CutsceneTrigger(UnitRoster attacker, UnitRoster defender, boolean beforeCombat) {
        this();
        isCompound = true;
        if(beforeCombat) {
            this.type = Type.COMBAT_START;
        } else {
            this.type = Type.COMBAT_END;
        }
        triggerUnits.add(attacker, defender);
    }

    public CutsceneTrigger(UnitRoster deathOf) {
        this();
        this.type = Type.DEATH;
        triggerUnits.add(deathOf);
    }

    public CutsceneTrigger(CutsceneID otherID) {
        this();
        this.type = Type.OTHER_CUTSCENE;
        triggerCutscenes.add(otherID);
    }

    public CutsceneTrigger(UnitRoster rosterID, Array<Vector2> areas) {
        this();
        isCompound = true;
        this.type = Type.AREA;
        triggerUnits.add(rosterID);
        for(Vector2 vector : areas) {
            triggerAreas.add(vector);
        }
    }

    public CutsceneTrigger(UnitRoster rosterID, Vector2 area) {
        this();
        isCompound = true;
        this.type = Type.AREA;
        triggerUnits.add(rosterID);
        triggerAreas.add(area);
    }

    public CutsceneTrigger(Vector2 area) {
        this();
        this.type = Type.AREA;
        triggerAreas.add(area);
    }

    public CutsceneTrigger(Vector2 area, TeamAlignment requiredTeamAlignment) {
        this();
        this.type = Type.AREA;
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

    public CutsceneTrigger() {
        triggerUnits     = new Array<>();
        triggerAreas     = new Array<>();
        triggerTurns     = new Array<>();
        triggerCutscenes = new Array<>();
        defuseTriggers   = new Array<>();

        hasFired              = false;
        defused               = false;
        isCompound            = false;
        requiresTeamAlignment = false;

        defuseCount     = 0;
        defuseThreshold = 1;

        requiredTeamAlignment = TeamAlignment.PLAYER;
    }

    private void incrementDefuseCount() {
        if(defused) return;
        defuseCount++;
        if(defuseCount >= defuseThreshold) defused = true;
    }

    public void addDefuseTrigger(CutsceneTrigger trigger) {
        if(!defuseTriggers.contains(trigger,true)) defuseTriggers.add(trigger);
    }

    /**
     * CHECKERS (gotta eat)
     */
    public boolean checkDeathTrigger(UnitRoster roster) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.type != Type.DEATH) return false;

        for(CutsceneTrigger def : defuseTriggers) {
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

    public boolean checkAreaTrigger(UnitRoster rosterID, Vector2 tileCoordinate) {
        if(defused) return false;
        if(!isCompound) return false;
        if(hasFired) return false;
        if(this.type != Type.AREA) return false;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerAreas.contains(tileCoordinate, true) &&
           triggerUnits.contains(rosterID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkAreaTrigger(Vector2 tileCoordinate, TeamAlignment unitsAlignment) {
        if(defused) return false;
        if(hasFired) return false;
        if(requiresTeamAlignment && unitsAlignment != requiredTeamAlignment) return false;
        if(this.type != Type.AREA) return false;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }
        if(defused) return false;

        if(triggerAreas.contains(tileCoordinate, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkTurnTrigger(int turn) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.type != Type.TURN) return false;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return false;

        if(triggerTurns.contains(turn, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkOtherCutsceneTrigger(CutsceneID otherID) {
        if(defused) return false;
        if(hasFired) return false;
        if(isCompound) return false;
        if(this.type != Type.OTHER_CUTSCENE) return false;

        for(CutsceneTrigger def : defuseTriggers) {
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

    public boolean checkCombatStartTrigger(UnitRoster rosterID) {
        // This will trigger if the unit fights anyone.
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.type != Type.COMBAT_START) return false;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID)) {
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
        if(this.type != Type.COMBAT_START) return false;

        for(CutsceneTrigger def : defuseTriggers) {
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

    public boolean checkCombatEndTrigger(UnitRoster rosterID) {
        if(defused) return false;
        if(hasFired) return false;
        if(this.isCompound) return false;
        if(this.type != Type.COMBAT_END) return false;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID)) {
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
        if(this.type != Type.COMBAT_END) return false;

        for(CutsceneTrigger def : defuseTriggers) {
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


    /**
     * SETTERS
     */
    public void fire() {
        if(defused) return;
        hasFired = true;
    }

//    public void defuse() {
//        defused = true;
//    }

    public void setDefuseThreshold(int i) {
        defuseThreshold = i;
    }

    /**
     * GETTERS
     */
    public boolean hasFired() {
        return hasFired;
    }

//    public boolean isDefused() {
//        return defused;
//    }

    public Type getType() {
        return type;
    }
}
