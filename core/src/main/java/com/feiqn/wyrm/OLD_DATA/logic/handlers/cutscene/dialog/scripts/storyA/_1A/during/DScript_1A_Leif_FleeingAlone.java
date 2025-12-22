package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1.OLDGridScreen_CUTSCENE_Leif_EscapedAlone;

public class DScript_1A_Leif_FleeingAlone extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_FleeingAlone(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_FLEEINGALONE);
    }

    @Override
    protected void declareTriggers() {
        // Handled by screen.
    }

    @Override
    public void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.LEIF_WORRIED, "I'm sorry...");
        set(CharacterExpression.LEIF_WORRIED, "I can't help you.");
        set(CharacterExpression.LEIF_WORRIED, "I've got to get out of here...");

        choreographFadeOut();

        choreographTransitionScreen(new OLDGridScreen_CUTSCENE_Leif_EscapedAlone(game));

    }

}
