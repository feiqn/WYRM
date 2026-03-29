package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;

public class DScript_TEMPLATE extends OLD_ChoreographedCutsceneScript {

    public DScript_TEMPLATE(WYRMGame game, CutsceneID id) {
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
