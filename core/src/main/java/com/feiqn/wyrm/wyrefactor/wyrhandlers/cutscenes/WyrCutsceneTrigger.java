package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.GridCutsceneTrigger;

public abstract class WyrCutsceneTrigger {

    private final WyrType type;

    protected boolean hasFired;
    protected boolean isCompound; // Requires 2 or more conditions to be met simultaneously.
    protected boolean defused; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.

    protected int defuseThreshold;
    protected int defuseCount;

    protected WyrCutsceneTrigger(WyrType type) {
        this.type = type;

        hasFired              = false;
        defused               = false;
        isCompound            = false;

        defuseCount     = 0;
        defuseThreshold = 1;
    }

    protected void incrementDefuseCount() {
        if(defused) return;
        defuseCount++;
        if(defuseCount >= defuseThreshold) defused = true;
    }

    public void setDefuseThreshold(int i) {
        defuseThreshold = i;
    }

    public boolean hasFired() {
        if(defused) return false;
        return hasFired;
    }

    public void fire() {
        if(defused) return;
        hasFired = true;
    }

    public WyrType getType() { return type; }
}
