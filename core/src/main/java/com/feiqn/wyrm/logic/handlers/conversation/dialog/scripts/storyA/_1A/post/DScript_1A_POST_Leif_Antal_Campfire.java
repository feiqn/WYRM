package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._1A.post;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogAction;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;
import com.feiqn.wyrm.logic.screens.cutscenes.stage2.GridScreen_CUTSCENE_Leif_Antal_GatesAreClosed;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.CavalryUnit;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.RIGHT;

public class DScript_1A_POST_Leif_Antal_Campfire extends ChoreographedDialogScript {

    private CavalryUnit cav;

    final ScreenAdapter nextScreen;

    public DScript_1A_POST_Leif_Antal_Campfire(WYRMGame game) {
        super(game);

        nextScreen = new GridScreen_CUTSCENE_Leif_Antal_GatesAreClosed(game);

        cav = new CavalryUnit(game);
        cav.setSize(1, 1.5f);
        cav.setColor(Color.RED);
    }

    @Override
    protected void setSeries() {
        // Your cutscene here.
        if(ags != null) {
            /* fade in from black to Leif and Antal at a
             * roadside campfire, immediately after
             * fleeing together from the destroyed city
             * in 1A
             */

            // TODO: Owls, frogs, and other forest animal sound effects.

            choreographShortPause();

            set(LEIF_WORRIED, "We can rest here until dawn.");

            set(ANTAL_EXHAUSTED, "You'll hear no complaints from me.", RIGHT, true);

            choreographLinger();

            set(ANTAL_WORRIED, "...I hope my family made it out alright.", RIGHT, true);

            set(LEIF_CURIOUS, "You had family in the city?");

            set(ANTAL_WORRIED, "My mother and brother.", RIGHT, true);

            set(LEIF_CURIOUS, "And you left them behind?");

            set(ANTAL_SAD, "...", RIGHT, true);

            choreographLinger();

            set(LEIF_THINKING, "...I didn't see anyone fleeing south along the coast. If your family made it out, they'd have to be following the same road as us, westward.");

            set(ANTAL_SAD, "...", RIGHT, true);

            choreographLinger();

            set(ANTAL_WORRIED, "...Thank you for travelling with me. That winged horse of yours could surely carry you to the safety of the capital by next moon.", RIGHT, true);

            set(LEIF_DETERMINED, "You could move a lot faster too if you ditched that ridiculous armor.");

            set(ANTAL_WORRIED, "And be out here naked to the cloth? I may not be a fighter, but armor is still armor. I feel much safer with it between me and danger.", RIGHT, true);

            set(LEIF_THINKING, "...suit yourself.");

            choreographLinger();

            set(LEIF_WORRIED, "Do you hear that?");

            set(ANTAL_CURIOUS, "Hoof beats on the road.", RIGHT, true);

            set(LEIF_WORRIED, "Cover the fire and get down!");

            choreographShortPause();

            // the pair move to douse the fire quickly. MOVE + SPAWN choreography?

            choreographSpawn(cav, 29, 15);

            choreographMoveTo(cav, -1, 15);

            choreographShortPause();

            set(ANTAL_EXHAUSTED, "He's gone.", RIGHT);
            lastFrame().addDialogAction(new DialogAction(new Runnable() {
                @Override
                public void run() {
                    cav.remove();
                }
            }));

            set(LEIF_WORRIED, "A scout... or a messenger, perhaps?");

            set(ANTAL_DEVASTATED, "This is awful, why is this happening?", RIGHT, true);

            set(LEIF_CURIOUS, "For a civil war, it does seem odd that they'd burn the whole city down, civilians and all.");

            set(ANTAL_CURIOUS, "Civil war? The North is a sovereign nation-state, they have been for ages. They have their own king and army. We've had peaceful trade agreements with them for decades. Where are you from, again?", RIGHT, true);

            set(LEIF_THINKING, "A small farm on the planes. I've never followed politics much.");

            set(ANTAL_WORRIED, "This is unprecedented, nothing like this has ever happened in living memory.", RIGHT, true);

            set(LEIF_WORRIED, "...");

            choreographLinger();

            set(LEIF_WORRIED, "We need to get somewhere safe");

            set(ANTAL_SAD, "On the western coast at the base of the mountains lies the walled city.", RIGHT, true);

            set(LEIF_CURIOUS, "Your city had walls and mountains too.");

            set(ANTAL_CURIOUS, "Well, yes; but theirs' are more... majestic, I suppose.", RIGHT, true);

            set(ANTAL_CURIOUS, "It's what they're known for. We're known for being the border city.", RIGHT, true);

            set(LEIF_THINKING, "How very... eastern.");

            set(ANTAL_CURIOUS, "So you're from the western planes? Surely you've visited the walled city often then?", RIGHT, true);

            set(LEIF_THINKING, "Why are you so concerned with where I'm from?");

            set(ANTAL_EMBARRASSED, "...sorry.", RIGHT, true);

            set(LEIF_ANNOYED, "...");

            choreographLinger();

            set(LEIF_EXHAUSTED, "We need to sleep. We'll continue following the road at first light");

            set(ANTAL_EMBARRASSED, "...", RIGHT, true);

            choreographFadeOut();

            choreographTransitionScreen(nextScreen);

        }
    }
}
