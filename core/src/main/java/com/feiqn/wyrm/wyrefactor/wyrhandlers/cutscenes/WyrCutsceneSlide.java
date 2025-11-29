package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

public abstract class WyrCutsceneSlide {

    // refactor of DialogFrame / DialogSlide

    public enum Type {
        GRID,
        WORLD,
        NARRATIVE,
    }

    private final Type type;

    protected WyrCutsceneSlide(Type type) {
        this.type = type;
    }

    public Type getType() { return type; }

}
