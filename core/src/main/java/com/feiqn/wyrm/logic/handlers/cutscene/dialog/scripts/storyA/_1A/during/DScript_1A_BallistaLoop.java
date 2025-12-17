package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnitOLD;

public class DScript_1A_BallistaLoop extends ChoreographedCutsceneScript {

    // Begin looping on turn 4 and stop after unit dies.

    public DScript_1A_BallistaLoop(WYRMGame game) {
        super(game,CutsceneID.CSID_1A_BALLISTALOOP);
    }

    @Override
    protected void declareTriggers() {
        loop(LoopCondition.BROKEN_THRESHOLD);

        armTurnCutsceneTrigger(4, false, false);

        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_BALLISTADEATH, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;

        final SoldierUnitOLD soldier = new SoldierUnitOLD(game);
        soldier.setTeamAlignment(TeamAlignment.ENEMY);
        soldier.setAIType(AIPersonality.AGGRESSIVE);
        soldier.giveUniqueID("ballistaTarget");

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(3));

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(3));

        choreographSpawn(soldier, 16, 21);
        choreographMoveTo(soldier, 17,21);

    }

}
