package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrCutsceneChoreographer {

    public enum Type {
        GRID,
        WORLD,
        NARRATIVE,
    }

    private final Type type;

    protected WyrCutsceneChoreographer(Type type) {
        this.type = type;
    }

    public Type getType() { return type; }


}
