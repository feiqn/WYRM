package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.unsorted.eric;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;

import static com.feiqn.wyrm.logic.handlers.conversations.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversations.SpeakerPosition.*;

public class DScript_U_Eric_Leif_Brea_StillYourChild extends ChoreographedDialogScript {

    public DScript_U_Eric_Leif_Brea_StillYourChild(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        // Leif and Eric are arguing about what to do in the east -- Leif believes they have to help or
        // the problems will soon follow them home. Eric refuses to take any action outside of his own nation.
        // The argument gets heated, and Eric says something heinous. Leif gets furious but goes silent and storms out.

        set(BREA_SHOUTING, "Eric!", RIGHT, true);

        set(ERIC_FROWNING, "...");

        set(BREA_ANGRY, "That is still your child!", RIGHT, true);

        set(BREA_ANGRY, "Does that mean nothing to you?", RIGHT, true);

        set(ERIC_FROWNING, "I am the King.");

        set(ERIC_FROWNING, "It means a great deal to me.");

        set(ERIC_FROWNING, "A great deal of shame and disappointment.");

        set(BREA_ANGRY, "All the more to have you as a father.", RIGHT, true);

        set(ERIC_ANGRY, "I am the King!");

        set(ERIC_ANGRY, "I cannot be seen to be so ideologically weak!");

        set(BREA_STOIC, "So again you will turn inward, and separate yourself, just as you do our nation?", RIGHT, true);

        set(ERIC_FROWNING, "Nothing beyond the sea concerns us.");

        set(BREA_STOIC, "So you say, even as our child crosses its depths.", RIGHT, true);

        set(ERIC_FROWNING, "...");

        set(ERIC_FROWNING, "Enough of this.");

        set(BREA_STOIC, "...", RIGHT, true);

        // choreograph: clear portraits

        set(BREA_STOIC, "Coward.", RIGHT, true);
    }

}
