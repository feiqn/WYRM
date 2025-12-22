package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneScript;

public abstract class GridCutsceneScript extends WyrCutsceneScript {

    // refactor of DialogScript / ConversationScript / CutsceneScript

    protected GridCutsceneScript(CutsceneID id) {
        super(WyrType.GRIDWORLD, id);
    }
}
