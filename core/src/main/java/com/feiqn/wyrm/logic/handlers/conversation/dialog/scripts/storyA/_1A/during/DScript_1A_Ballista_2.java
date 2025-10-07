package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Ballista_2 extends ChoreographedDialogScript {

    public DScript_1A_Ballista_2(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(1));
    }

}
