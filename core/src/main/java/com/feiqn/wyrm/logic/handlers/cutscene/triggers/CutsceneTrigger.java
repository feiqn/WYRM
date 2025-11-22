package com.feiqn.wyrm.logic.handlers.cutscene.triggers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CutsceneTrigger {

    public enum Type {
        AREA,
        TURN,
        DEATH,
        COMBAT_START,
        COMBAT_END,
        OTHER_CUTSCENE
    }

    protected boolean hasTriggered;

    protected Type type;

    protected final Array<UnitRoster> triggerUnits;
    protected final Array<Vector2> triggerAreas;
    protected final Array<Integer> triggerTurns;
    protected final Array<CutsceneID> triggerCutscenes;

    protected final CutsceneScript scriptToTrigger;


    public CutsceneTrigger(CutsceneScript script, Integer turnToTrigger) {
        this(script);
        this.type = Type.TURN;
        triggerTurns.add(turnToTrigger);
    }

    public CutsceneTrigger(CutsceneScript script, UnitRoster attacker, UnitRoster defender, boolean before) {
        this(script);
        if(before) {
            this.type = Type.COMBAT_START;
        } else {
            this.type = Type.COMBAT_END;
        }
        triggerUnits.add(attacker, defender);
    }

    public CutsceneTrigger(CutsceneScript script, UnitRoster deathOf) {
        this(script);
        this.type = Type.DEATH;
        triggerUnits.add(deathOf);
    }

    public CutsceneTrigger(CutsceneScript script, CutsceneID otherID) {
        this(script);
        this.type = Type.OTHER_CUTSCENE;
        triggerCutscenes.add(otherID);
    }

    public CutsceneTrigger(CutsceneScript script, Vector2 area) {
        this(script);
        this.type = Type.AREA;
        triggerAreas.add(area);
    }

    public CutsceneTrigger(CutsceneScript script) {
        triggerUnits     = new Array<>();
        triggerAreas     = new Array<>();
        triggerTurns     = new Array<>();
        triggerCutscenes = new Array<>();

        scriptToTrigger = script;
        hasTriggered = false;
    }


    /**
     * CHECKERS
     */
    public boolean checkDeathTrigger(UnitRoster roster) {
        if(this.type != Type.DEATH) return false;
        if(hasTriggered) return false;

        if(this.triggerUnits.contains(roster, true)) {
            hasTriggered = true;
            return true;
        }

        return false;
    }

    public boolean checkAreaTrigger(Vector2 tileCoordinate) {
        if(this.type != Type.AREA) return false;
        if(hasTriggered) return false;

        if(triggerAreas.contains(tileCoordinate, true)) {
            hasTriggered = true;
            return true;
        }

        return false;
    }

    public boolean checkTurnTrigger(int turn) {
        if(this.type != Type.TURN) return false;
        if(hasTriggered) return false;

        if(triggerTurns.contains(turn, true)) {
            hasTriggered = true;
            return true;
        }

        return false;
    }

    public boolean checkOtherCutsceneTrigger(CutsceneID otherID) {
        if(this.type != Type.OTHER_CUTSCENE) return false;
        if(hasTriggered) return false;

        if(triggerCutscenes.contains(otherID, true)) {
            hasTriggered = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatStartTrigger(UnitRoster attacker, UnitRoster defender) {
        if(this.type != Type.COMBAT_START) return false;
        if(hasTriggered) return false;

        if(triggerUnits.contains(attacker, true) &&
           triggerUnits.contains(defender, true)) {

            hasTriggered = true;
            return true;
        }

        return false;
    }

    public boolean checkCombatEndTrigger(UnitRoster attacker, UnitRoster defender) {
        if(this.type != Type.COMBAT_END) return false;
        if(hasTriggered) return false;

        if(triggerUnits.contains(attacker, true) &&
           triggerUnits.contains(defender, true)) {

            hasTriggered = true;
            return true;
        }

        return false;
    }


    /**
     * SETTERS
     */
    public void trigger() {
        hasTriggered = true;
    }


    /**
     * GETTERS
     */
    public CutsceneScript getScript() {
        return scriptToTrigger;
    }

    public boolean hasTriggered() {
        return hasTriggered;
    }

    public Type getType() {
        return type;
    }
}
