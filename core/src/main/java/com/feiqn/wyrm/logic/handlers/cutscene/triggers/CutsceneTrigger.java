package com.feiqn.wyrm.logic.handlers.cutscene.triggers;

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
    protected boolean diffused; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.
    protected boolean requiresPlayerUnit;
    protected boolean requiresTeamAlignment;

    protected TeamAlignment requiredTeamAlignment;

    protected Type type;

    protected final Array<UnitRoster> triggerUnits;
    protected final Array<Vector2> triggerAreas;
    protected final Array<Integer> triggerTurns;
    protected final Array<CutsceneID> triggerCutscenes;

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

    public CutsceneTrigger(UnitRoster rosterID, Vector2 area) {
        this();
        isCompound = true;
        this.type = Type.AREA;
        triggerUnits.add(rosterID);
        triggerAreas.add(area);
    }

    public CutsceneTrigger(Vector2 area, boolean playerOnly) {
        this();
        this.type = Type.AREA;
        if(playerOnly) {
            isCompound = true;
            requiresPlayerUnit = true;
        }
        triggerAreas.add(area);
    }

    public CutsceneTrigger() {
        triggerUnits     = new Array<>();
        triggerAreas     = new Array<>();
        triggerTurns     = new Array<>();
        triggerCutscenes = new Array<>();

        hasFired              = false;
        diffused              = false;
        isCompound            = false;
        requiresPlayerUnit    = false;
        requiresTeamAlignment = false;

        requiredTeamAlignment = TeamAlignment.PLAYER;
    }


    /**
     * CHECKERS (gotta eat)
     */
    public boolean checkDeathTrigger(UnitRoster roster) {
        if(this.type != Type.DEATH) return false;
        if(hasFired) return false;

        if(this.triggerUnits.contains(roster, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkAreaTrigger(UnitRoster rosterID, Vector2 tileCoordinate) {
        if(!isCompound) return false;
        if(this.type != Type.AREA) return false;
        if(hasFired) return false;

        if(triggerAreas.contains(tileCoordinate, true) &&
           triggerUnits.contains(rosterID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    // TODO: maybe another check for if unit belongs to specific team

    public boolean checkAreaTrigger(Vector2 tileCoordinate, boolean isPlayerUnit) {
        if(requiresPlayerUnit && !isPlayerUnit) return false;
        if(this.type != Type.AREA) return false;
        if(hasFired) return false;

        if(triggerAreas.contains(tileCoordinate, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkTurnTrigger(int turn) {
        if(this.type != Type.TURN) return false;
        if(hasFired) return false;

        if(triggerTurns.contains(turn, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkOtherCutsceneTrigger(CutsceneID otherID) {
        if(this.type != Type.OTHER_CUTSCENE) return false;
        if(hasFired) return false;

        if(triggerCutscenes.contains(otherID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatStartTrigger(UnitRoster rosterID) {

        // This will trigger if the unit fights anyone.
        if(this.isCompound) return false;
        if(this.type != Type.COMBAT_START) return false;
        if(hasFired) return false;

        if(triggerUnits.contains(rosterID, true)) {
            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatStartTrigger(UnitRoster attacker, UnitRoster defender) {

        // This will only trigger if two specific units fight each other.

        if(this.type != Type.COMBAT_START) return false;
        if(hasFired) return false;

        if(triggerUnits.contains(attacker, true) &&
           triggerUnits.contains(defender, true)) {

            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatEndTrigger(UnitRoster rosterID) {
        if(this.isCompound) return false;
        if(this.type != Type.COMBAT_END) return false;
        if(hasFired) return false;

        if(triggerUnits.contains(rosterID, true)) {

            hasFired = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatEndTrigger(UnitRoster attacker, UnitRoster defender) {
        if(this.type != Type.COMBAT_END) return false;
        if(hasFired) return false;

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
        if(diffused) return;
        hasFired = true;
    }

    public void diffuse() {
        diffused = true;
    }

    /**
     * GETTERS
     */
    public boolean hasFired() {
        return hasFired;
    }

    public boolean isDiffused() {
        return diffused;
    }

    public Type getType() {
        return type;
    }
}
