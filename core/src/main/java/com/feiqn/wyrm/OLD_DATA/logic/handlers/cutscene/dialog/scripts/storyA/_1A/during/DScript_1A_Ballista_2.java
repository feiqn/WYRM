package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.graphics.Color;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.RPGridPersonalityType;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.enemy.generic.SoldierUnitOLD;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.HorizontalPosition;

public class DScript_1A_Ballista_2 extends OLD_ChoreographedCutsceneScript {

    public DScript_1A_Ballista_2(WYRMGame game) {
        super(game, WYRM.CutsceneID.CSID_1A_BALLISTA_2);
    }

    @Override
    protected void declareTriggers() {
        armTurnCutsceneTrigger(3, false, false);
        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_BALLISTA_DEATH, true);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        final SoldierUnitOLD soldier = new SoldierUnitOLD(game);
        soldier.setTeamAlignment(Wyr.TeamAlignment.ENEMY);
        soldier.setAIType(RPGridPersonalityType.AGGRESSIVE);
        soldier.setColor(Color.RED);


        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "In the name of the Queen, I shall defend our great nation!", HorizontalPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(3));


        set(OLD_CharacterExpression.GENERIC_SOLDIER, "Fire!", HorizontalPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(3));

        choreographFocusOnLocation(16,21);
        choreographSpawn(soldier, 16, 21);

//        choreographShortPause();

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "Damn it! They just keep coming...", HorizontalPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographMoveTo(soldier, 18,21); // TODO: don't spawn where people already are

    }

}
