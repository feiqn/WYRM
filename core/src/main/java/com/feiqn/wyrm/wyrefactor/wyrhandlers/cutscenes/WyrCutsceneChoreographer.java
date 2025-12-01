package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrCutsceneChoreographer {

    private final WyrType type;

    protected WyrCutsceneChoreographer(WyrType type) {
        this.type = type;
    }

    public WyrType getType() { return type; }


}
