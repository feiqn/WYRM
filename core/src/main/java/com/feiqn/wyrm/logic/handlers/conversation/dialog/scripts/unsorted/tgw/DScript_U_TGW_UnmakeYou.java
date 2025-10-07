package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.unsorted.tgw;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_U_TGW_UnmakeYou extends DialogScript {

    @Override
    public void setSeries() {
        set(CharacterExpression.THE_GREAT_WYRM, "");
    }
}
