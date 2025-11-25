package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class DScript_1A_Leif_IneffectiveAttack extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_IneffectiveAttack(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_INEFFECTIVEATTACK);
    }

    @Override
    protected void declareTriggers() {
        armSingleUnitCombatCutsceneTrigger(UnitRoster.LEIF, false, true, false);

        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_LEIF_LEAVEMEALONE, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WINCING, "Ow ow ow!");

        set(CharacterExpression.LEIF_WINCING, "I think I hurt my fist more than I hurt him.");

        set(CharacterExpression.LEIF_WINCING, "I've got to get out of here before these guys kill me!");

        choreographShortPause();

    }
}
