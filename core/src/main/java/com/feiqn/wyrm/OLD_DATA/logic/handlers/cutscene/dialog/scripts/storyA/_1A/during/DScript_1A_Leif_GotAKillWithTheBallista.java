package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.CutsceneID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.HorizontalPosition;

public class DScript_1A_Leif_GotAKillWithTheBallista extends OLD_ChoreographedCutsceneScript {

    public DScript_1A_Leif_GotAKillWithTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_FIRED_BALLISTA);
    }

    @Override
    protected void declareTriggers() {
        armDeathCutsceneTrigger(Wyr.TeamAlignment.ENEMY, false);
        armOtherIDCutsceneTrigger(thisCutsceneID.CSID_1A_BALLISTA_DEATH, false);
        triggerThreshold++;
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_PANICKED, "Holy shit!", HorizontalPosition.RIGHT, true);
        set(OLD_CharacterExpression.LEIF_PANICKED, "That guy exploded!", HorizontalPosition.RIGHT, true);
        set(OLD_CharacterExpression.LEIF_PANICKED, "I... I killed that guy.", HorizontalPosition.RIGHT, true);

        // TODO: this may be the place to have generic enemies display names after this cs.
    }

}
