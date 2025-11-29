package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneChoreography;

public abstract class GridCutsceneChoreography extends WyrCutsceneChoreography {

    public enum Target {
        WORLD,
        DIALOG,
    }

    private final Target target;

    protected GridCutsceneChoreography(Target target) {
        super(Type.GRID);
        this.target = target;
    }

    public Target getTarget() { return target; }
}
