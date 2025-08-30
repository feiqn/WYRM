package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.screens.storyA.stage1.GridScreen_CUTSCENE_Leif_EscapedAlone;

public class DScript_1A_Leif_FleeingAlone extends ChoreographedDialogScript {

    public DScript_1A_Leif_FleeingAlone(WYRMGame game) {
        super(game);
    }

    @Override
    public void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WORRIED, "I'm sorry...");
        set(CharacterExpression.LEIF_WORRIED, "I can't help you.");
        set(CharacterExpression.LEIF_WORRIED, "I've got to get out of here...");

        choreographFadeOut();

        choreographTransitionScreen(new GridScreen_CUTSCENE_Leif_EscapedAlone(game));

    }

}
