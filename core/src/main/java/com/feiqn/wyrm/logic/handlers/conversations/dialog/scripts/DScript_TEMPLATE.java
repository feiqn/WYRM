package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;

public class DScript_TEMPLATE extends ChoreographedDialogScript {

    public DScript_TEMPLATE(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();


    }

}
