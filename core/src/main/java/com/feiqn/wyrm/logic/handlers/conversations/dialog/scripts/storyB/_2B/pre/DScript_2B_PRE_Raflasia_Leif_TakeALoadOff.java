package com.feiqn.wyrm.logic.handlers.conversations.dialog.scripts.storyB._2B.pre;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversations.dialog.ChoreographedDialogScript;

public class DScript_2B_PRE_Raflasia_Leif_TakeALoadOff extends ChoreographedDialogScript {

    public DScript_2B_PRE_Raflasia_Leif_TakeALoadOff(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        // Raflasia, a spunky child with spiky blue hair, sits cross-legged
        // on the side of the road, and calls Leif over when he sees him
        // traveling south to the capital. Raflasia has no sense of danger
        // and has only ever lived deep within the peaceful south. Raflasia
        // is starting up a business offering games to play, and asks
        // Leif to try them out.
        // For now only one minigame opens up, but more will come later.
        // If Leif spends a significant amount of time investing in playing
        // Raflasia's, Raf will eventually offer to let Leif travel together
        // with him and work together to expand the games offering.
        // This represents the first "secret" branching path in WYRM.

    }

}
