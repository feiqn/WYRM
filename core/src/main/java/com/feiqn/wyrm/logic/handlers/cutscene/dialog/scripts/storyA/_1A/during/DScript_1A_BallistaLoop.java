package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnit;

public class DScript_1A_BallistaLoop extends ChoreographedCutsceneScript {

    // Begin looping on turn 4 and stop after unit dies.

    public DScript_1A_BallistaLoop(WYRMGame game) {
        super(game,CutsceneID.CSID_1A_BALLISTALOOP);
    }

    @Override
    protected void declareTriggers() {
        loop(LoopCondition.BROKEN_THRESHOLD);

        armTurnCutsceneTrigger(4, false);

        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_BALLISTADEATH, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        final SoldierUnit soldier = new SoldierUnit(game);
        soldier.setTeamAlignment(TeamAlignment.ENEMY);
        soldier.setAIType(AIType.AGGRESSIVE);

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(ags.conditions().teams().getEnemyTeam().size - 1));

        choreographShortPause();

        choreographSpawn(soldier, 16, 22);

    }

}
