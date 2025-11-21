package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Leif_GettingInTheBallista extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_GettingInTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_GETTINGINTHEBALLISTA);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;

        choreographShortPause();

        // Leif "Okay, I can do this, just point and shoot..."

    }
}
