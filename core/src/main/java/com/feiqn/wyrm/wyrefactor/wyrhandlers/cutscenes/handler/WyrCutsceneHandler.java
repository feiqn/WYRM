package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player.WyrCutscenePlayer;

public abstract class WyrCutsceneHandler<Actor extends WyrActor<?>, Script extends WyrCutscene<Actor>, Player extends WyrCutscenePlayer<Script>> extends WyrHandler {

    protected final Array<Script> cutscenes = new Array<>();
    protected Script activeCutscene = null;
    protected boolean cutscenePlaying = false;
    protected Player cutscenePlayer;

    public WyrCutsceneHandler() {}

    public void addCutscene(Script cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    protected void startCutscene(int index) {
        startCutscene(cutscenes.get(index));
    }

    protected void startCutscene(Script script) {
        if(cutscenePlaying) {
            queueCutscene(script);
            return;
        }

        cutscenePlaying = true;

        // communicate w/ cs player to begin acting
        cutscenePlayer.playCutscene(script);
    }

    protected void queueCutscene(Script script) {

    }

    public void endCutscene() {
        cutscenePlaying = false;
        activeCutscene = null;
    }

    /**
     * Checks
     */
    public void checkDeathTriggers(UnitIDRoster roster) {
        for(Script cutscene : cutscenes) {
            cutscene.checkDeathTriggers(roster);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkAreaTriggers(UnitIDRoster rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
        for(Script cutscene : cutscenes) {
            cutscene.checkAreaTriggers(rosterID, tileCoordinate);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
        final boolean isPlayerUnit = teamAlignment == TeamAlignment.PLAYER;
        checkAreaTriggers(tileCoordinate, teamAlignment);
    }
    private void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment teamAlignment) {
        for(Script cutscene : cutscenes) {
            cutscene.checkAreaTriggers(tileCoordinate, teamAlignment);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkTurnTriggers(int turn) {
        for(Script cutscene : cutscenes) {
            cutscene.checkTurnTriggers(turn);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        for(Script cutscene : cutscenes) {
            cutscene.checkOtherCutsceneTriggers(otherID);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatStartTriggers(UnitIDRoster rosterID, boolean unitIsAggressor) {
        for(Script cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatStartTriggers(UnitIDRoster attacker, UnitIDRoster defender) {
        for(Script cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(attacker, true);
            cutscene.checkCombatStartTriggers(defender, false);
            cutscene.checkCombatStartTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatEndTriggers(UnitIDRoster roster, boolean unitIsAggressor) {
        for(Script cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatEndTriggers(UnitIDRoster attacker, UnitIDRoster defender) {
        for(Script cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(attacker, true);
            cutscene.checkCombatEndTriggers(defender, false);
            cutscene.checkCombatEndTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public boolean isBusy() { return cutscenePlaying || activeCutscene != null; }

}
