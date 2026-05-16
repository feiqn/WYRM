package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.*;

public class DScript_1A_Antal_EscapingAlive extends OLD_ChoreographedCutsceneScript {


    public DScript_1A_Antal_EscapingAlive(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_ANTAL_ESCAPING_ALIVE);
    }


    @Override
    protected void declareTriggers() {
        armCampaignFlagCutsceneTrigger(FlagID.STAGE_1A_ANTAL_ESCAPED, false);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;

        choreographShortPause();

        set(OLD_CharacterExpression.ANTAL_ENTHUSIASTIC, "I made it!");
        set(OLD_CharacterExpression.ANTAL_ENTHUSIASTIC, "Th-thank you, kind stranger!");
    }

}
