package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public class GridTalkInteraction extends GridInteraction {
    // TODO: This is test code referencing OLD_DATA,
    //  refactor to speak with WyrGridScreen and new
    //  WyrGridCutsceneHandler instead.

    public GridTalkInteraction(WYRMGame wyrmGame, CutsceneScript script) {
        super(wyrmGame, InteractionType.GRID_TALK);
        clickableLabel = new Label("Talk", root.assets().menuLabelStyle);
        clickableLabel.setColor(Color.GREEN);
        runnableInteraction.setRunnable(new Runnable() {
            @Override
            public void run() {
                if(wyrmGame.activeGridScreen.activeUnit != null) {
                    wyrmGame.activeGridScreen.activeUnit.setCannotMove();
                }
                wyrmGame.activeGridScreen.conditions().conversations().startCutscene(script);
            wyrmGame.activeGridScreen.checkLineOrder();
            }
        });
    }


    public GridTalkInteraction(WYRMGame root, GridCutsceneScript scriptToTrigger) {
        super(root, InteractionType.GRID_TALK);
        clickableLabel = new Label("Talk", root.assets().menuLabelStyle);
        clickableLabel.setColor(Color.GREEN);
        runnableInteraction.setRunnable(new Runnable() {
            @Override
            public void run() {
                // start cutscene in WyrGCSHandle
            }
        });
        applyListenerToLabel("Begin a conversation.");
    }

}
