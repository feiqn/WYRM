package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._2A;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.LEFT;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.RIGHT;

public class DScript_2A_Leif_Antal_StealthIntoCity extends ChoreographedDialogScript {

    public DScript_2A_Leif_Antal_StealthIntoCity(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(LEIF_HOPEFUL, "Look, there's no one here but those guards");

        set(ANTAL_ENTHUSIASTIC, "And the gates haven't been closed!", RIGHT, true);

        set(LEIF_CURIOUS, "Everyone just crowded around the east gate, they didn't even try going around here...");

        set(ANTAL_CURIOUS, "Do you think they'll let us through? Maybe they haven't gotten the news about the lockdown yet.", RIGHT, true);

        set(LEIF_DETERMINED, "Best not take the chance.");

        set(LEIF_WORRIED, "...Okay, I'll distract them, you sneak in, then I'll slip in behind you.");

        set(ANTAL_WORRIED, "W-what? Sneak in? What are you going to do", RIGHT, true);

        choreographShortPause();

        // focus on flammable tile to northeast

        set(LEIF_DETERMINED, "There. I'll start a fire to draw them away from the gate, then you run in.");

        set(ANTAL_WORRIED, "Fire?! You're going to burn the city down?!", RIGHT, true);

        set(LEIF_DETERMINED, "Just a little one -- they'll put it out before it spreads... probably.");

        choreographShortPause();

        set(ANTAL_WORRIED, "Are we the baddies?", LEFT, true);

        // begin stage 2A
    }

}
