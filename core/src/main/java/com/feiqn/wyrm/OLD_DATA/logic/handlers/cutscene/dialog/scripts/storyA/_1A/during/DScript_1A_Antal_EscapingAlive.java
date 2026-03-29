package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.CharacterExpression;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public class DScript_1A_Antal_EscapingAlive extends OLD_ChoreographedCutsceneScript {


    public DScript_1A_Antal_EscapingAlive(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_ANTAL_ESCAPING_ALIVE);
    }


    @Override
    protected void declareTriggers() {
        armCampaignFlagCutsceneTrigger(CampaignFlags.STAGE_1A_ANTAL_ESCAPED, false);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;

        choreographShortPause();

        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "I made it!");
        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "Th-thank you, kind stranger!");
    }

}
