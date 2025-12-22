package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;

public abstract class ChoreographedCutsceneScript extends CutsceneScript {

    // includes relevant map and unit data for passing in runnable actions

    protected final WYRMGame game;

    protected final OLD_GridScreen ags;

    public ChoreographedCutsceneScript(WYRMGame game, CutsceneID id) {
        super(id);
        this.game = game;
        slideshow.clear();
        frameIndex = 0;
        ags = game.activeOLDGridScreen;
    }

}
