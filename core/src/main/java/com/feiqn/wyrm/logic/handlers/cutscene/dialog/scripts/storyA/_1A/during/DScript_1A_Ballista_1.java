package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

public class DScript_1A_Ballista_1 extends ChoreographedCutsceneScript {

    // Plays on turn 2 of 1A. Due to turn order and no AI behavior, it
    // looks like this plays on the allied soldiers' turn, though it doesn't.

    public DScript_1A_Ballista_1(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_BALLISTA1);
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

        set(CharacterExpression.GENERIC_SOLDIER, "Damned Northerners!", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        set(CharacterExpression.GENERIC_SOLDIER, "My wounds are deep, but even if it costs me my life, I will protect my home!", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        set(CharacterExpression.GENERIC_SOLDIER, "Take this!", SpeakerPosition.RIGHT, true);
        lastFrame().setFocusedName("Danial");

        choreographFocusOnUnit(ags.conditions().teams().getEnemyTeam().get(1));

        choreographShortPause();

        choreographBallistaAttack(ags.conditions().teams().getAllyTeam().get(0), ags.conditions().teams().getEnemyTeam().get(1));

        // clear the dialog stage

        set(CharacterExpression.LEIF_SURPRISED, "Holy shit!");

        set(CharacterExpression.LEIF_SURPRISED, "That guy just got obliterated!");

        set(CharacterExpression.LEIF_DETERMINED, "I've got to get out of here!");
    }
}
