package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Antal_EscapingAlive extends ChoreographedCutsceneScript {


    public DScript_1A_Antal_EscapingAlive(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_ANTAL_ESCAPINGALIVE);
    }


    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags != null) return;

        choreographShortPause();

        // Antal "Th-thank you!!"
    }

}
