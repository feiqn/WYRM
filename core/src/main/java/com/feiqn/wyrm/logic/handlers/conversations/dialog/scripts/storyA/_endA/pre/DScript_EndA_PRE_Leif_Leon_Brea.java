package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.storyA._endA.pre;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;

import static com.feiqn.wyrm.logic.handlers.conversations.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversations.SpeakerPosition.*;

public class DScript_EndA_PRE_Leif_Leon_Brea extends ChoreographedDialogScript {

    public DScript_EndA_PRE_Leif_Leon_Brea(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        // enter: Leif is in danger, being choked / restrained in some way by either Leon or some powerful revenants

        set(NONE, "UNHAND MY SON!");

        // choreograph: an iconic shining arrow previously seen associated with Eric flies in from offscreen, freeing Leif from his captor

        set(LEIF_BADLY_WOUNDED, "Father?");

        // choreograph: rapid camera pan to see Queen Brea and several escorts on a nearby hill overlooking the battle

        set(LEIF_SURPRISED, "Mom!?");

        // choreograph: clear portraits etc

        set(BREA_SHOUTING, "By my right as Queen, you will not harm my child!", RIGHT, true);

        // choreograph: brea uses her special ability (very fancy and impressive)

        // choreograph: clear portraits etc

        set(LEIF_SURPRISED, "Mom!", LEFT_OF_CENTER, true);

        // choreograph: clear portraits and focus on Brea

        set(LEIF_SURPRISED, "How did you get here!?", FAR_LEFT);

        set(BREA_STOIC, "Leif, listen to me now.", FAR_RIGHT);

        set(LEIF_SURPRISED, "!", FAR_LEFT);

        set(BREA_STOIC, "Leif, your father is a Great man - albeit not a good one.", FAR_RIGHT);

        set(BREA_STOIC, "The same strength that rules his kingdom flows through your veins.", FAR_RIGHT);

        set(BREA_STOIC, "Rise now, and overcome this evil! Claim your destiny!"); // corny af, will make it better later


    }

}
