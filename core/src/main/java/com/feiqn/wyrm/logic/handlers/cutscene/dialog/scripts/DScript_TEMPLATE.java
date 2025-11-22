package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_TEMPLATE extends ChoreographedCutsceneScript {

    public DScript_TEMPLATE(WYRMGame game, CutsceneID id) {
        super(game,id);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();


    }

}
