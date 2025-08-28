package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.during;

import com.badlogic.gdx.graphics.Color;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;

public class DScript_1A_Antal_HelpMe extends ChoreographedDialogScript {

    private AntalUnit antal;


    public DScript_1A_Antal_HelpMe(WYRMGame game) {
        super(game);

        antal = new AntalUnit(game);
        antal.setTeamAlignment(TeamAlignment.ALLY);
        antal.setAIType(AIType.ESCAPE);
        antal.setColor(Color.GREEN);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        set(CharacterExpression.LEIF_WORRIED, "I think we got away...");

        choreographSpawn(antal, 29, 29);

        choreographFocusOnUnit(antal);

        set(CharacterExpression.ANTAL_EXHAUSTED, "Please...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "...help me.");
        set(CharacterExpression.LEIF_PANICKED, "Help you?! Aren't you supposed to be protecting the city?!", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.ANTAL_EXHAUSTED, "Please, this armor, it's so heavy...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "I'll die if I don't get out of here!");

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getPlayerTeam().get(0));

        set(CharacterExpression.LEIF_WORRIED, "I could flee and save myself, but that knight...");
        set(CharacterExpression.LEIF_WORRIED, "What do I do..?");

        choreographRevealVictCon(CampaignFlags.STAGE_1A_ANTAL_ESCAPED);
    }

}
