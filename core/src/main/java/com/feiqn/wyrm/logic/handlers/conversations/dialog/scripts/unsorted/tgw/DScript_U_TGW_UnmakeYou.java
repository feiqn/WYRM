package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.unsorted.tgw;

import com.feiqn.wyrm.logic.handlers.conversations.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.DialogScript;

public class DScript_U_TGW_UnmakeYou extends DialogScript {

    @Override
    public void setSeries() {
        set(CharacterExpression.THE_GREAT_WYRM, "");
    }
}
