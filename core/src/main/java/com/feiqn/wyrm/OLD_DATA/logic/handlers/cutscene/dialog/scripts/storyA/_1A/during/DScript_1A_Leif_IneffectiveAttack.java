package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

public class DScript_1A_Leif_IneffectiveAttack extends OLD_ChoreographedCutsceneScript {

    public DScript_1A_Leif_IneffectiveAttack(WYRMGame game) {
        super(game, WYRM.CutsceneID.CSID_1A_LEIF_INEFFECTIVE_ATTACK);
    }

    @Override
    protected void declareTriggers() {
        armSingleUnitCombatCutsceneTrigger(WYRM.Character.Leif, false, true, false);

//        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_LEIF_LEAVEMEALONE, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_WINCING, "Ow ow ow!");

        set(OLD_CharacterExpression.LEIF_WINCING, "I think I hurt my fist more than I hurt him.");

        set(OLD_CharacterExpression.LEIF_WINCING, "I've got to get out of here before these guys kill me!");

//        choreographShortPause();

    }
}
