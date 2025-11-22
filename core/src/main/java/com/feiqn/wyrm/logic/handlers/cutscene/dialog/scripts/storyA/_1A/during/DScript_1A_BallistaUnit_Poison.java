package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_BallistaUnit_Poison extends ChoreographedCutsceneScript {

    public DScript_1A_BallistaUnit_Poison(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA1);
    }

    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnLocation(35, 27);

        set(CharacterExpression.GENERIC_SOLDIER, "No, not yet, I can still...", SpeakerPosition.RIGHT, true);
    }

}
