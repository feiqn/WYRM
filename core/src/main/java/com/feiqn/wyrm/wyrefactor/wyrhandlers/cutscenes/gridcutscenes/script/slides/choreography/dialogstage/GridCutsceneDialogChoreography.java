package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.dialogstage;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrameChoreography;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_DialogAction;
import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.GridCutsceneChoreography;

public class GridCutsceneDialogChoreography extends GridCutsceneChoreography {

    // refactor of Dialog Action

    public enum ChoreoType {
        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
        CHOREOGRAPHY,
        ARBITRARY_CODE,
    }

    private ChoreoType verb;

    private boolean playParallel;
    private boolean loops;
    private Speed speed;
    private Runnable code;

    protected GridCutsceneDialogChoreography() { super(Set.DIALOG); }



}
