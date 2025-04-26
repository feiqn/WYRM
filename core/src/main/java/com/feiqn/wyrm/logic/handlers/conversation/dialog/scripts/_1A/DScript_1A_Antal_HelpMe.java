package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;

public class DScript_1A_Antal_HelpMe extends DialogScript {

    private final WYRMGame game;

    private AntalUnit antal;

    public DScript_1A_Antal_HelpMe(WYRMGame game) {
        super();
        this.game = game;

        antal = new AntalUnit(game);
    }

    @Override
    protected void setSeries() {
        if(antal == null) return;

        antal.setTeamAlignment(TeamAlignment.ALLY);
        antal.setAIType(AIType.ESCAPE);
        antal.setColor(Color.GREEN);
        game.activeGridScreen.getLogicalMap().placeUnitAtPositionROWCOLUMN(antal, 15, 23);
        game.activeGridScreen.conditions().addToTurnOrder(antal);
        game.activeGridScreen.conditions().teams().getAllyTeam().add(antal);
        game.activeGridScreen.rootGroup.addActor(antal);
        antal.setCannotMove();
        antal.setColor(0,1,0,0);

        antal.addAction(Actions.sequence(
            Actions.fadeIn(.5f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    antal.setColor(Color.GREEN);
                }
            })
        ));

        choreographLinger();

//        choreographFocusOnUnit(antalChar);

        set(CharacterExpression.ANTAL_EXHAUSTED, "Please...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "...help me.");
        set(CharacterExpression.LEIF_PANICKED, "Help you?! Aren't you supposed to be protecting the city?!", SpeakerPosition.RIGHT);
        set(CharacterExpression.ANTAL_EXHAUSTED, "Please, this armor, it's so heavy...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "I'll die if I don't get out of here!");

        set(CharacterExpression.LEIF_WORRIED, "I could flee and save myself, but that knight...");
        set(CharacterExpression.LEIF_WORRIED, "What do I do..?");
    }

}
