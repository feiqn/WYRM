package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Leif_GettingInTheBallista extends ChoreographedDialogScript {

    public DScript_1A_Leif_GettingInTheBallista(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;

        choreographShortPause();

        // Leif "Okay, I can do this, just point and shoot..."

    }
}
