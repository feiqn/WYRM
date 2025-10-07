package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

public class DScript_1A_Ballista_1 extends ChoreographedDialogScript {

    public DScript_1A_Ballista_1(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(1));

        choreographShortPause();

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(1));

        set(CharacterExpression.LEIF_SURPRISED, "Holy shit!");

        set(CharacterExpression.LEIF_SURPRISED, "That guy just got obliterated!");

        set(CharacterExpression.LEIF_DETERMINED, "I've got to get out of here!");
    }
}
