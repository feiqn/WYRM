package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class DScript_1A_Antal_EscapingAlive extends ChoreographedCutsceneScript {


    public DScript_1A_Antal_EscapingAlive(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_ANTAL_ESCAPINGALIVE);
    }


    @Override
    protected void declareTriggers() {
        armSpecificUnitAreaCutsceneTrigger(UnitRoster.ANTAL, new Vector2(9, 2), false);
    }

    @Override
    protected void setSeries() {
        if(ags != null) return;
        if(slideshow.size != 0) return;


        choreographShortPause();

        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "I made it!");
        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "Th-thank you, kind stranger!");
    }

}
