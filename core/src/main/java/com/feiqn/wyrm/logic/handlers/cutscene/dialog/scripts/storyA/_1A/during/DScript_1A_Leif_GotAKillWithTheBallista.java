package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Leif_GotAKillWithTheBallista extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_GotAKillWithTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_GOTAKILLWITHTHEBALLISTA);
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
