package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public class GridTalkInteraction extends GridInteraction {
    // TODO: This is test code referencing OLD_DATA,
    //  refactor to speak with WyrGridScreen and new
    //  WyrGridCutsceneHandler instead.

    private CutsceneScript script;
    private GridCutsceneScript GCSScript;

    public GridTalkInteraction(WYRMGame wyrmGame, GridActor parent, CutsceneScript script) { // TEST
        super(wyrmGame, parent, InteractionType.GRID_TALK,"Talk", "Begin a conversation.");
        clickableLabel.setColor(Color.GREEN);
        this.script = script;
    }


    public GridTalkInteraction(WYRMGame root, GridActor parent, GridCutsceneScript scriptToTrigger) {
        super(root, parent, InteractionType.GRID_TALK, "Talk", "Begin a conversation.");
        clickableLabel.setColor(Color.GREEN);
        this.GCSScript = scriptToTrigger;
    }

    @Override
    public void payload() {
        // TODO: start cutscene in WyrCSHandle abstract

        // TEST:
        if(root.activeGridScreen.activeUnit != null) {
            root.activeGridScreen.activeUnit.setCannotMove();
        }
        root.activeGridScreen.conditions().conversations().startCutscene(script);
        root.activeGridScreen.checkLineOrder();
    }

}
