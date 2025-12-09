package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_BallistaUnit_Death extends ChoreographedCutsceneScript {

    // Plays after Antal enters in 1A.

    public DScript_1A_BallistaUnit_Death(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTADEATH);
    }

    @Override
    protected void declareTriggers() {
        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_ANTAL_HELPME, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        choreographFocusOnLocation(35, 27);

        set(CharacterExpression.GENERIC_SOLDIER, "No, not yet, I can still...", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographDeath(ags.conditions().teams().getAllyTeam().get(0));

        set(CharacterExpression.LEIF_DESPAIRING, "Aw hell, now that guy too?!", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.LEIF_WORRIED, "It really is just me alone out here...", SpeakerPosition.RIGHT, true);

    }

}
