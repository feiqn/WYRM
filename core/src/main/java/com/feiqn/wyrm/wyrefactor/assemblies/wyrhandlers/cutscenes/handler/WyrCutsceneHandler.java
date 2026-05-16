package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player.WyrCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrCutsceneHandler extends WyrHandler {

    protected final Array<WyrCutscene> cutscenes = new Array<>();
    protected WyrCutscenePlayer cutscenePlayer;

    public WyrCutsceneHandler() {}

    public WyrCutsceneHandler(MetaHandler metaHandler, WyrCutscenePlayer cutscenePlayer) {
        super(metaHandler);
        this.cutscenePlayer = cutscenePlayer;
    }

    public void addCutscene(WyrCutscene cutscene) {
//        Gdx.app.log("CS", "adding...");
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    protected void startCutscene(int index) {
        startCutscene(cutscenes.get(index));
    }

    protected void startCutscene(WyrCutscene script) {
//        Gdx.app.log("CS handle", "starting");

        if(cutsceneIsPlaying()) {
            queueCutscene(script);
            return;
        }

        // communicate w/ cs player to begin acting
        cutscenePlayer.playCutscene(script);
    }

    protected void queueCutscene(WyrCutscene script) {

    }

    public void endCutscene() {

    }

    public void playNext() {
        if(!cutsceneIsPlaying()) return;
        cutscenePlayer.playNext();
    }

    public boolean cutsceneIsPlaying() {
//        return false;
        return (cutscenePlayer.getActiveCutscene() != null && cutscenePlayer.getActiveCutscene().continues());
    }

    @Override
    public boolean isBusy() { return isBusy || cutsceneIsPlaying(); }

    /**
     * Checks
     */
    public void checkDeathTriggers(CharacterID roster) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkDeathTriggers(roster);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkAreaTriggers(CharacterID rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
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
//        Gdx.app.log("CS handle", "checking turns");
//        Gdx.app.log("CS", "cutscenes.size = " + cutscenes.size);

        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkTurnTriggers(turn);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkOtherCutsceneTriggers(otherID);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatStartTriggers(CharacterID rosterID, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatStartTriggers(CharacterID attacker, CharacterID defender) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(attacker, true);
            cutscene.checkCombatStartTriggers(defender, false);
            cutscene.checkCombatStartTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    private void checkCombatEndTriggers(CharacterID roster, boolean unitIsAggressor) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
    public void checkCombatEndTriggers(CharacterID attacker, CharacterID defender) {
        for(WyrCutscene cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(attacker, true);
            cutscene.checkCombatEndTriggers(defender, false);
            cutscene.checkCombatEndTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }


}
