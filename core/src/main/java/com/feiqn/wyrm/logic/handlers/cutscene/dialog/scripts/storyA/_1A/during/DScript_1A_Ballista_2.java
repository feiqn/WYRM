package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Ballista_2 extends ChoreographedCutsceneScript {

    public DScript_1A_Ballista_2(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA3);
    }

    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        set(CharacterExpression.GENERIC_SOLDIER, "In the name of the Queen, I shall defend our great nation!", SpeakerPosition.RIGHT, true);

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(1));
    }

}
