package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.post;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.DialogAction;
import com.feiqn.wyrm.OLD_DATA.logic.screens.storyA.stage1.OLDGridScreen_CUTSCENE_Leif_Antal_Campfire;

public class DScript_1A_POST_Leif_FoundAntal extends ChoreographedCutsceneScript {

    public DScript_1A_POST_Leif_FoundAntal(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_POST_LEIF_FOUNDANTAL);
    }

    @Override
    protected void declareTriggers() {
        // Started by CS screen.
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;


        /* Leif, flying high, sees the destruction and fire
         * in the city, then spots Antal lagging behind other
         * fleeing survivors on the road.
         * Leif swoops down.
         */

        // TODO: have leif flying in from the east to start conversation.

        set(CharacterExpression.LEIF_DETERMINED, "Hey!", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_DETERMINED, "What the hell! Shouldn't you be protecting the city?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_DEVASTATED, "I have to get out of here! I'm no soldier, I'm a ceremonial guard! They just pay me to wear this ridiculous armor and look stoic outside the palace gates!");
        set(CharacterExpression.ANTAL_DEVASTATED, "No one even lives in the palace! It's just a symbolic gesture to honor the Queen!");

        set(CharacterExpression.LEIF_ANNOYED, "Ceremonial...? So you're fleeing your home without a fight?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_DEVASTATED, "Aren't you listening? I don't know how to fight!");
        set(CharacterExpression.ANTAL_SAD, "All I can do is run...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "This armor weighs me down so much, it seems I'm the last one to make it out of the city.");
        set(CharacterExpression.ANTAL_EXHAUSTED, "...and that everyone else has gone on far ahead of me by now.");
        set(CharacterExpression.ANTAL_WORK_FACE, "Headed for the western city, by my guess. It's closer than the capital, and it's well fortified.");

        set(CharacterExpression.LEIF_THINKING, "The western city? Is that where you'll go?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "Yes, it's either there or the capital, but the road south is much longer and rougher than the road west.");
        set(CharacterExpression.ANTAL_WORK_FACE, "Though with that winged horse of yours, you might make the distance in a day.");
        set(CharacterExpression.ANTAL_CURIOUS, "I've... never seen a horse like that up close. Only in pictures, and as far off specs among the mountaintop clouds.");
        set(CharacterExpression.ANTAL_WORK_FACE, "I wasn't aware anyone rode atop them... where did you-");
        lastFrame().setAutoplayNext(true);

        set(CharacterExpression.LEIF_WORRIED, "We should pick up the pace, we need to find shelter before nightfall. The world is volatile right now, people will be confused and desperate.", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_THINKING, "It will be safest if we stick together and avoid talking to anyone else until we reach the city.", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "Oh, okay... wait, we? You want to travel together? Surely I will only slow you down. With your winged horse, you could-");
        lastFrame().setAutoplayNext(true);

        set(CharacterExpression.LEIF_WINCING, "Let's just, keep moving. Try to keep your eyes on the road. Don't look back at the city.", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "...");
        set(CharacterExpression.ANTAL_SAD, "Right...");

        lastFrame().addDialogAction(new DialogAction(new Runnable() {
            @Override
            public void run() {
                ags.gameStage.addAction(Actions.sequence(
                        Actions.fadeOut(3),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.OLD_TransitionToScreen(new OLDGridScreen_CUTSCENE_Leif_Antal_Campfire(game));
                            }
                        })
                    )
                );
            }
        }));
    }
}
