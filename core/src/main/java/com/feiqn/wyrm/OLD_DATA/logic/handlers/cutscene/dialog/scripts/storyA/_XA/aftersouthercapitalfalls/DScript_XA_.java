package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._XA.aftersouthercapitalfalls;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_XA_ extends ChoreographedCutsceneScript {

    public DScript_XA_(WYRMGame game, CutsceneID id) {
        super(game,id);
    }

    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();


    }

}
