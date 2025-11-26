package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class DScript_1A_Leif_GotAKillWithTheBallista extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_GotAKillWithTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_GOTAKILLWITHTHEBALLISTA);
    }

    @Override
    protected void declareTriggers() {
        armDeathCutsceneTrigger(TeamAlignment.ENEMY, false);
        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_BALLISTADEATH, false);
        triggerThreshold++;
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.LEIF_PANICKED, "Holy shit!", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_PANICKED, "That guy exploded!", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_PANICKED, "I... I killed that guy.", SpeakerPosition.RIGHT, true);

        // TODO: this may be the place to have generic enemies display names after this cs.
    }

}
