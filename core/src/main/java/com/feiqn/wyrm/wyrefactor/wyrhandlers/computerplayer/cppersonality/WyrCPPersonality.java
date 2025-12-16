package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrCPPersonality extends Wyr {

    private final AIPersonality personality;

    public WyrCPPersonality(WyrType wyrType, AIPersonality personality) {
        super(wyrType);
        this.personality = personality;
    }


    // TODO: extrapolate this into a data class
    //  like choreo / triggers, apply things like
    //  goal tiles, priority targets, and patrol
    //  points there instead.


    public AIPersonality getPersonality() { return personality; }
}
