package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.Position;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;

public class DScript_1A_BallistaUnit_Death extends OLD_ChoreographedCutsceneScript {

    // Plays after Antal enters in 1A.

    public DScript_1A_BallistaUnit_Death(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA_DEATH);
    }

    @Override
    protected void declareTriggers() {
        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_ANTAL_HELP_ME, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        choreographFocusOnLocation(35, 27);

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "No, not yet, I can still...", Position.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographDeath(ags.conditions().teams().getAllyTeam().get(0));

        set(OLD_CharacterExpression.LEIF_DESPAIRING, "Aw hell, now that guy too?!", Position.RIGHT, true);

        set(OLD_CharacterExpression.LEIF_WORRIED, "It really is just me alone out here...", Position.RIGHT, true);

    }

}
