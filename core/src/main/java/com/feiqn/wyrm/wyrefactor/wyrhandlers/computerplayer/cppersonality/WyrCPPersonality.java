package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrCPPersonality implements Wyr {

    private final AIPersonality personality;

    public WyrCPPersonality(AIPersonality personality) {
        this.personality = personality;
    }

    public AIPersonality personalityType() { return personality; }
}
