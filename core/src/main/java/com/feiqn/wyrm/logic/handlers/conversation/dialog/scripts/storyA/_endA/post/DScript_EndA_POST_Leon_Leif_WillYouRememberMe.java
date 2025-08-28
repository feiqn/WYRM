package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._endA.post;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_EndA_POST_Leon_Leif_WillYouRememberMe extends ChoreographedDialogScript {

    public DScript_EndA_POST_Leon_Leif_WillYouRememberMe(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        // This plays as Leif lands the final killing blow on Leon in the 'A' timeline.

        // Leon reflects and laments on the truth that he has been the carrier
        // of the living memories of all of his family and friends who have
        // died in his father's mad war. He reaches the horrifying conclusion
        // that all the good memories of them and the people they were will die with him,
        // leaving only the legacy of war and abuse that Richard carved into the world
        // with his final actions.
        // Leon asks humbly if Leif will remember him, in a desperate attempt to
        // not let the final glimpse of the life and love Leon knew in these people
        // fade away.

    }

}
