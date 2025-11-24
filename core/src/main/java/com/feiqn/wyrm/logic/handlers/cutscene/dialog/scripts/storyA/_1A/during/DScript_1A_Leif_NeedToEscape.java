package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Leif_NeedToEscape extends ChoreographedCutsceneScript {

    // Opening CS for 1A.

    public DScript_1A_Leif_NeedToEscape(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_NEEDTOESCAPE);
    }

    @Override
    protected void declareTriggers() {
        armTurnCutsceneTrigger(1, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WINCING, "I've got to get out of here...");

        choreographRevealVictCon(CampaignFlags.STAGE_1A_CLEARED);
    }
}
