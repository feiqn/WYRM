package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversations.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;

public class DScript_1A_BallistaUnit_Poison extends ChoreographedDialogScript {

    public DScript_1A_BallistaUnit_Poison(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.GENERIC_SOLDIER, "The poison is too much...", SpeakerPosition.RIGHT, true);
    }

}
