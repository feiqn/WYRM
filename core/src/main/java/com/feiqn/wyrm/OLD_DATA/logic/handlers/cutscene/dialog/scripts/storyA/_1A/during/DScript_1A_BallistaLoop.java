package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.PersonalityType;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.enemy.generic.SoldierUnitOLD;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.CutsceneID;

public class DScript_1A_BallistaLoop extends OLD_ChoreographedCutsceneScript {

    // Begin looping on turn 4 and stop after unit dies.

    public DScript_1A_BallistaLoop(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA_LOOP);
    }

    @Override
    protected void declareTriggers() {
        loop(LoopCondition.BROKEN_THRESHOLD);

        armTurnCutsceneTrigger(4, false, false);

        armOtherIDCutsceneTrigger(thisCutsceneID.CSID_1A_BALLISTA_DEATH, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;

        final SoldierUnitOLD soldier = new SoldierUnitOLD(game);
        soldier.setTeamAlignment(Wyr.TeamAlignment.ENEMY);
        soldier.setAIType(PersonalityType.AGGRESSIVE);
        soldier.giveUniqueID("ballistaTarget");

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(3));

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(3));

        choreographSpawn(soldier, 16, 21);
        choreographMoveTo(soldier, 17,21);

    }

}
