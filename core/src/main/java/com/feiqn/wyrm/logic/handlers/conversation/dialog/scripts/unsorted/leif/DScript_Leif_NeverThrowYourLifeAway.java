package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.unsorted.leif;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_Leif_NeverThrowYourLifeAway extends ChoreographedDialogScript {

    public DScript_Leif_NeverThrowYourLifeAway(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();


    }

}
