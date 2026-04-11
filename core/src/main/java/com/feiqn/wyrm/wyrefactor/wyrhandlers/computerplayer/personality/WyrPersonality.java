package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrPersonality implements Wyr {

    private final Personality personality;

    public WyrPersonality(Personality personality) {
        this.personality = personality;
    }

    public Personality personalityType() { return personality; }
}
