package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

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
