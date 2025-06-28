package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Leif_GotAKillWithTheBallista extends ChoreographedDialogScript {

    public DScript_1A_Leif_GotAKillWithTheBallista(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;

        choreographShortPause();

        // Leif "Holy shit,"
        // Leif "That guy exploded..."
        // Leif "I killed that guy.
    }

}
