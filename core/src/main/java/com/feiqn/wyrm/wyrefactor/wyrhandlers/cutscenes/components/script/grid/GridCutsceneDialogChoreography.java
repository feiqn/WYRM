package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.wyrefactor.helpers.Speed;

public class GridCutsceneDialogChoreography extends GridCutsceneChoreography {

    // refactor of Dialog Action

    private final DialogChoreoType verb;

    private boolean playParallel;
    private boolean loops;
    private Speed speed;
    private Runnable code;

    protected GridCutsceneDialogChoreography(DialogChoreoType choreoType) {
        super(ChoreoStage.DIALOG);
        this.verb = choreoType;
    }

}
