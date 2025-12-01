package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrCutsceneSlide {

    // refactor of DialogFrame / DialogSlide

    private final WyrType type;

    protected WyrCutsceneSlide(WyrType type) {
        this.type = type;
    }

    public WyrType getType() { return type; }

}
