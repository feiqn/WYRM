package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_1A_Leif_NeedToEscape extends DialogScript {

    public DScript_1A_Leif_NeedToEscape() {
        super();
    }

    @Override
    protected void setSeries() {
         set(CharacterExpression.LEIF_WINCING, "I've got to get out of here...");
    }
}
