package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.post;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1.OLDGridScreen_CUTSCENE_Leif_FoundAntal;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class DScript_1A_POST_Leif_ShouldFindAntal extends OLD_ChoreographedCutsceneScript {

    // Leif specifically fled east after helping Antal escape.

    public DScript_1A_POST_Leif_ShouldFindAntal(WYRMGame game) {
        super(game, Wyr.CutsceneID.CSID_1A_POST_LEIF_SHOULD_FIND_ANTAL);
    }

    @Override
    protected void declareTriggers() {
        // Started by CS screen.
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_EXHAUSTED, "We managed to escape...");

        set(OLD_CharacterExpression.LEIF_WORRIED, "And that knight, traveling alone on the road...");

        set(OLD_CharacterExpression.LEIF_THINKING, "...We should go find him.");

        choreographTransitionScreen(new OLDGridScreen_CUTSCENE_Leif_FoundAntal(game));

    }

}
