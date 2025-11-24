package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.logic.screens.storyA.stage1.GridScreen_CUTSCENE_Leif_ShouldFindAntal;

public class DScript_1A_Leif_SavedAntal extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_SavedAntal(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_SAVEDANTAL);
    }

    @Override
    protected void declareTriggers() {
        // Handled by screen.
    }

    @Override
    public void setSeries() {
        if(ags == null) return;

        set(CharacterExpression.LEIF_WORRIED, "We made it!");

        choreographFadeOut();

        choreographTransitionScreen(new GridScreen_CUTSCENE_Leif_ShouldFindAntal(game));
    }

}
