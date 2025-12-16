package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality;

import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrCPPersonality extends Wyr {

    private final AIPersonality personality;

    public WyrCPPersonality(WyrType wyrType, AIPersonality personality) {
        super(wyrType);
        this.personality = personality;
    }

    public AIPersonality personalityType() { return personality; }
}
