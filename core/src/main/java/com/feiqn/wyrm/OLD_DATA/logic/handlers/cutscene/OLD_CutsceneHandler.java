package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneScript;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class OLD_CutsceneHandler {

    private final WYRMGame root;

    private final Array<OLD_CutsceneScript> cutscenes;



    public OLD_CutsceneHandler(WYRMGame root) {
        this.root = root;
        this.cutscenes = new Array<>();
    }

    public void addCutscene(OLD_CutsceneScript cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    public void startCutscene(OLD_CutsceneScript DScript) {
        // TODO: persistent CutscenePlayer Actor
        WYRMGame.activeOLDGridScreen.startCutscene(new OLD_CutscenePlayer(root, DScript));
    }


    /**
     * Trigger checks
     */
    public void checkDeathTriggers(WYRM.Character roster) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkDeathTriggers(roster);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkAreaTriggers(WYRM.Character rosterID, Wyr.TeamAlignment teamAlignment, Vector2 tileCoordinate) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkAreaTriggers(rosterID, tileCoordinate);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
        final boolean isPlayerUnit = teamAlignment == Wyr.TeamAlignment.PLAYER;
        checkAreaTriggers(tileCoordinate, teamAlignment);
    }

    private void checkAreaTriggers(Vector2 tileCoordinate, Wyr.TeamAlignment teamAlignment) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkAreaTriggers(tileCoordinate, teamAlignment);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkTurnTriggers(int turn) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkTurnTriggers(turn);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkCampaignFlagTriggers(WYRM.CampaignFlag flag) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkCampaignFlagTriggers(flag);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkOtherCutsceneTriggers(WYRM.CutsceneID otherID) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkOtherCutsceneTriggers(otherID);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    private void checkCombatStartTriggers(WYRM.Character rosterID, boolean unitIsAggressor) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(rosterID, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkCombatStartTriggers(WYRM.Character attacker, WYRM.Character defender) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkCombatStartTriggers(attacker, true);
            cutscene.checkCombatStartTriggers(defender, false);
            cutscene.checkCombatStartTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    private void checkCombatEndTriggers(WYRM.Character roster, boolean unitIsAggressor) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(roster, unitIsAggressor);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }

    public void checkCombatEndTriggers(WYRM.Character attacker, WYRM.Character defender) {
        for(OLD_CutsceneScript cutscene : cutscenes) {
            cutscene.checkCombatEndTriggers(attacker, true);
            cutscene.checkCombatEndTriggers(defender, false);
            cutscene.checkCombatEndTriggers(attacker, defender);
            if(cutscene.isReadyToPlay()) startCutscene(cutscene);
        }
    }
}
