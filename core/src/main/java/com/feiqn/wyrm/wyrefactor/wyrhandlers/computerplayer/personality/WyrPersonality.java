package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

/**
 * Used to give an actor preferences within the game world.
 * I.E., "I want to rob chests." or, "I like moving red gems best."
 */
public abstract class WyrPersonality<PersonalityType extends Enum<?>> implements Wyr {

    protected PersonalityType personalityType;

    public WyrPersonality(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public PersonalityType personalityType() { return personalityType; }
}
