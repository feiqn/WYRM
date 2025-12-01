package com.feiqn.wyrm.logic.handlers.cutscene.dialog;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression.LEIF_SMILING;

public abstract class ChoreographedCutsceneScript extends CutsceneScript {

    // includes relevant map and unit data for passing in runnable actions

    protected final WYRMGame game;

    protected final GridScreen ags;

    public ChoreographedCutsceneScript(WYRMGame game, CutsceneID id) {
        super(id);
        this.game = game;
        slideshow.clear();
        frameIndex = 0;
        ags = game.activeGridScreen;
    }

}
