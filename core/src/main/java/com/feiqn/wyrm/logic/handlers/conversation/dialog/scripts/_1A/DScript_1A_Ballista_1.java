package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class DScript_1A_Ballista_1 extends ChoreographedDialogScript {

    private final SimpleUnit ballistaUnit, target;

    public DScript_1A_Ballista_1(WYRMGame game, SimpleUnit ballistaUnit, SimpleUnit target) {
        super(game);
        this.ballistaUnit = ballistaUnit;
        this.target = target;
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ballistaUnit);

        choreographBallistaAttack(ballistaUnit, target);
    }
}
