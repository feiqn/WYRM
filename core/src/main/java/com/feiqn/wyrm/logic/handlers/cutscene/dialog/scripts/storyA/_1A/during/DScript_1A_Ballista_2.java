package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.graphics.Color;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnit;

public class DScript_1A_Ballista_2 extends ChoreographedCutsceneScript {

    public DScript_1A_Ballista_2(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA2);
    }

    @Override
    protected void declareTriggers() {
        armTurnCutsceneTrigger(3, false, false);
        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_BALLISTADEATH, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        final SoldierUnit soldier = new SoldierUnit(game);
        soldier.setTeamAlignment(TeamAlignment.ENEMY);
        soldier.setAIType(AIType.AGGRESSIVE);
        soldier.setColor(Color.RED);


        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        set(CharacterExpression.GENERIC_SOLDIER, "In the name of the Queen, I shall defend our great nation!", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(3));


        set(CharacterExpression.GENERIC_SOLDIER, "Fire!", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(3));

        choreographFocusOnLocation(16,21);
        choreographSpawn(soldier, 16, 21);

//        choreographShortPause();

        set(CharacterExpression.GENERIC_SOLDIER, "Damn it! They just keep coming...", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographMoveTo(soldier, 18,21); // TODO: don't spawn where people already are

    }

}
