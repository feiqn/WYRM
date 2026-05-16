package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.HorizontalPosition;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.*;

public class DScript_1A_Leif_GettingInTheBallista extends OLD_ChoreographedCutsceneScript {

    public DScript_1A_Leif_GettingInTheBallista(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_GETTING_IN_THE_BALLISTA);
    }

    @Override
    protected void declareTriggers() {
        // TODO: possibly one day have a trigger for entering a map object instead.
        armSpecificUnitAreaCutsceneTrigger(CharacterID.Leif, new Vector2(35,27), false);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_WORRIED, "Okay, I can do this, just aim and shoot, same as any old longbow...", HorizontalPosition.RIGHT, true);

        set(OLD_CharacterExpression.LEIF_PANICKED, "...oh, god, this is nothing like a a longbow.", HorizontalPosition.RIGHT, true);

        set(OLD_CharacterExpression.LEIF_PANICKED, "How do I aim this thing?!", HorizontalPosition.RIGHT, true);

    }

}
