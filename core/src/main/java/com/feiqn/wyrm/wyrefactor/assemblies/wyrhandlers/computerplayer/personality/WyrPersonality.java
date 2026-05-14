package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality;

import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

/**
 * Used to give an actor preferences within the game world.
 * I.E., "I want to rob chests." or, "I like moving red gems best."
 */
public class WyrPersonality implements Wyr {

    protected Enum<?> personalityType;

    public WyrPersonality(Enum<?> personalityType) {
        this.personalityType = personalityType;
    }

    public Enum<?> getPersonalityType() { return personalityType; }
}
