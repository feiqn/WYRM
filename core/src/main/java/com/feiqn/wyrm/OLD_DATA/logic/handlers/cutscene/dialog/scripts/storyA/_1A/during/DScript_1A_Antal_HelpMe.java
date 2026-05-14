package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.RPGridPersonalityType;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.ally.recruitable.AntalUnitOLD;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class DScript_1A_Antal_HelpMe extends OLD_ChoreographedCutsceneScript {

    private final AntalUnitOLD antal;


    public DScript_1A_Antal_HelpMe(WYRMGame game) {
        super(game, WYRM.CutsceneID.CSID_1A_ANTAL_HELP_ME);

        antal = new AntalUnitOLD(game);
        antal.setTeamAlignment(Wyr.TeamAlignment.ALLY);
        antal.setAIType(RPGridPersonalityType.ESCAPE);
        antal.setColor(Color.GREEN);
    }


    @Override
    protected void declareTriggers() {
        final Array<Vector2> triggerArea = new Array<>();
        triggerArea.add(new Vector2(39, 28));
        for(int x = 39; x < 59; x++) {
            for(int y = 28; y > 0; y--){
                triggerArea.add(new Vector2(x, y));
            }
        }

        armSpecificUnitAreaCutsceneTrigger(WYRM.Character.Leif, triggerArea, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;

        choreographShortPause();

        set(OLD_CharacterExpression.LEIF_WORRIED, "I think we got away...");

        choreographSpawn(antal, 29, 29);

        choreographFocusOnUnit(antal);

        set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "Please...");
        set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "...help me.");
        set(OLD_CharacterExpression.LEIF_PANICKED, "Help you?! Aren't you supposed to be protecting the city?!", Wyr.HorizontalPosition.RIGHT, true);
        set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "Please, this armor, it's so heavy...");
        set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "I'll die if I don't get out of here!");

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getPlayerTeam().get(0));

        set(OLD_CharacterExpression.LEIF_WORRIED, "I could flee and save myself, but that knight...");
        set(OLD_CharacterExpression.LEIF_WORRIED, "What do I do..?");

        choreographRevealVictCon(WYRM.CampaignFlag.STAGE_1A_ANTAL_ESCAPED);
    }

}
