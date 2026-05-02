package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.Position;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;

public class DScript_1A_Ballista_1 extends OLD_ChoreographedCutsceneScript {

    // Plays on turn 2 of 1A. Due to turn order and no AI behavior, it
    // looks like this plays on the allied soldiers' turn, though it doesn't.

    public DScript_1A_Ballista_1(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA_1);
    }

    @Override
    protected void declareTriggers() {
        armTurnCutsceneTrigger(2, false, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getAllyTeam().get(0));

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "Damned Northerners!", Position.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "My wounds are deep, but even if it costs me my life, I will protect my home!", Position.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "I'll man this ballista until my dying breath, and drag you all to hell with me!", Position.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        set(OLD_CharacterExpression.GENERIC_SOLDIER, "Take this!", Position.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(1));

        choreographShortPause();

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(1));

        // clear the dialog stage

        set(OLD_CharacterExpression.LEIF_SURPRISED, "Holy shit!", Position.RIGHT, true);

        set(OLD_CharacterExpression.LEIF_SURPRISED, "That guy just got obliterated!", Position.RIGHT, true);

        set(OLD_CharacterExpression.LEIF_DETERMINED, "I've got to get out of here!", Position.RIGHT, true);
    }
}
