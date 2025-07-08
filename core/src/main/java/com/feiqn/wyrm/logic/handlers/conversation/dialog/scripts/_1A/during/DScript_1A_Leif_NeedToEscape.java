package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Leif_NeedToEscape extends ChoreographedDialogScript {

    public DScript_1A_Leif_NeedToEscape(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WINCING, "I've got to get out of here...");

        choreographRevealVictCon(CampaignFlags.STAGE_1A_CLEARED);
    }
}
