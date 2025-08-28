package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Antal_EscapingAlive extends ChoreographedDialogScript {


    public DScript_1A_Antal_EscapingAlive(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;

        choreographShortPause();

        // Antal "Th-thank you!!"
    }

}
