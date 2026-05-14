package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player.WyrCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

public class WyrCutsceneHandler extends WyrHandler {

    protected final Array<WyrCutscene> cutscenes = new Array<>();
    protected WyrCutscene activeCutscene = null;
    protected boolean cutscenePlaying = false;
    protected WyrCutscenePlayer cutscenePlayer;

    public WyrCutsceneHandler() {}

    public WyrCutsceneHandler(MetaHandler metaHandler, WyrCutscenePlayer cutscenePlayer) {
        super(metaHandler);
        this.cutscenePlayer = cutscenePlayer;
    }

    public void addCutscene(WyrCutscene cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    protected void startCutscene(int index) {
        startCutscene(cutscenes.get(index));
    }

    protected void startCutscene(WyrCutscene script) {
        if(cutscenePlaying) {
            queueCutscene(script);
            return;
        }

        cutscenePlaying = true;

        // communicate w/ cs player to begin acting
        cutscenePlayer.playCutscene(script);
    }

    protected void queueCutscene(WyrCutscene script) {

    }

    public void endCutscene() {
        cutscenePlaying = false;
        activeCutscene = null;
    }

    /**
     * Checks
     */
    public void checkDeathTriggers(WYRM.Character roster) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkDeathTriggers(roster);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkAreaTriggers(WYRM.Character rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkAreaTriggers(rosterID, tileCoordinate);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
        final boolean isPlayerUnit = teamAlignment == TeamAlignment.PLAYER;
        checkAreaTriggers(tileCoordinate, teamAlignment);
    }
    private void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment teamAlignment) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkAreaTriggers(tileCoordinate, teamAlignment);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkTurnTriggers(int turn) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkTurnTriggers(turn);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkOtherCutsceneTriggers(WYRM.CutsceneID otherID) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkOtherCutsceneTriggers(otherID);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatStartTriggers(WYRM.Character rosterID, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatStartTriggers(WYRM.Character attacker, WYRM.Character defender) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(attacker, true);
            cutscene.checkCombatStartTriggers(defender, false);
            cutscene.checkCombatStartTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatEndTriggers(WYRM.Character roster, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatEndTriggers(WYRM.Character attacker, WYRM.Character defender) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(attacker, true);
            cutscene.checkCombatEndTriggers(defender, false);
            cutscene.checkCombatEndTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public boolean isBusy() { return cutscenePlaying || activeCutscene != null; }

}
