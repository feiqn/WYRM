package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1.OLDGridScreen_CUTSCENE_Leif_ShouldFindAntal;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

public class DScript_1A_Leif_SavedAntal extends OLD_ChoreographedCutsceneScript {

    public DScript_1A_Leif_SavedAntal(WYRMGame game) {
        super(game, WYRM.CutsceneID.CSID_1A_LEIF_SAVED_ANTAL);
    }

    @Override
    protected void declareTriggers() {
        // Handled by screen.
    }

    @Override
    public void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        set(OLD_CharacterExpression.LEIF_WORRIED, "We made it!");

        choreographFadeOut();

        choreographTransitionScreen(new OLDGridScreen_CUTSCENE_Leif_ShouldFindAntal(game));
    }

}
