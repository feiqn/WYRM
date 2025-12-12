package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridCutsceneHandler extends WyrCutsceneHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final GridCutscenePlayer player;

    public GridCutsceneHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        player = new GridCutscenePlayer(metaHandler);
    }

    @Override
    public void startCutscene(WyrCutsceneScript WyrCSScript) {

        // communicate w/ cs player to begin acting
    }

//    public void checkDeathTriggers(UnitRoster roster) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkDeathTriggers(roster);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    public void checkAreaTriggers(UnitRoster rosterID, TeamAlignment teamAlignment, Vector2 tileCoordinate) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkAreaTriggers(rosterID, tileCoordinate);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//        final boolean isPlayerUnit = teamAlignment == TeamAlignment.PLAYER;
//        checkAreaTriggers(tileCoordinate, teamAlignment);
//    }
//
//    private void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment teamAlignment) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkAreaTriggers(tileCoordinate, teamAlignment);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    public void checkTurnTriggers(int turn) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkTurnTriggers(turn);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkOtherCutsceneTriggers(otherID);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    private void checkCombatStartTriggers(UnitRoster rosterID, boolean unitIsAggressor) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    public void checkCombatStartTriggers(UnitRoster attacker, UnitRoster defender) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkCombatStartTriggers(attacker, true);
//            cutscene.checkCombatStartTriggers(defender, false);
//            cutscene.checkCombatStartTriggers(attacker, defender);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    private void checkCombatEndTriggers(UnitRoster roster, boolean unitIsAggressor) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }
//
//    public void checkCombatEndTriggers(UnitRoster attacker, UnitRoster defender) {
//        for(WyrCutsceneScript cutscene : cutscenes) {
//            cutscene.checkCombatEndTriggers(attacker, true);
//            cutscene.checkCombatEndTriggers(defender, false);
//            cutscene.checkCombatEndTriggers(attacker, defender);
//            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
//        }
//    }

}
