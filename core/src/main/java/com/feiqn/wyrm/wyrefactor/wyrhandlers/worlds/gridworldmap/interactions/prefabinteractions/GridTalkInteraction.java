package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.badlogic.gdx.graphics.Color;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public final class GridTalkInteraction extends GridInteraction {
    // TODO: This is test code referencing OLD_DATA,
    //  refactor to speak with WyrGridScreen and new
    //  WyrGridCutsceneHandler instead.

    private CutsceneScript script;
    private GridCutsceneScript GCSScript;

    public GridTalkInteraction(GridActor parent, CutsceneScript script) { // TEST
        super(parent, InteractionType.TALK,1);
//        clickableLabel.setColor(Color.GREEN);
        this.script = script;
    }


    public GridTalkInteraction(GridActor parent, GridCutsceneScript scriptToTrigger) {
        super(parent, InteractionType.TALK, 1);
//        clickableLabel.setColor(Color.GREEN);
        this.GCSScript = scriptToTrigger;
    }

//    @Override
//    public void payload() {
//        // TODO: start cutscene in WyrCSHandle abstract
//
//        // TEST:
//        if(WYRMGame.activeOLDGridScreen.activeUnit != null) {
//            WYRMGame.activeOLDGridScreen.activeUnit.setCannotMove();
//        }
//        WYRMGame.activeOLDGridScreen.conditions().conversations().startCutscene(script);
//        WYRMGame.activeOLDGridScreen.checkLineOrder();
//    }

}
