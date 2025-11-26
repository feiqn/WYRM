package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class DScript_1A_Leif_GettingInTheBallista extends ChoreographedCutsceneScript {

    public DScript_1A_Leif_GettingInTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_GETTINGINTHEBALLISTA);
    }

    @Override
    protected void declareTriggers() {
        // TODO: possibly one day have a trigger for entering a map object instead.
        armSpecificUnitAreaCutsceneTrigger(UnitRoster.LEIF, new Vector2(35,27), false);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.LEIF_WORRIED, "Okay, I can do this, just aim and shoot, same as any old longbow...", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.LEIF_PANICKED, "...oh, god, this is nothing like a a longbow.", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.LEIF_PANICKED, "How do I aim this thing?!", SpeakerPosition.RIGHT, true);

    }

}
