package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.screens.GridScreen;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.RIGHT;

public class DScript_1A_POST_Leif_Antal_Campfire extends ChoreographedDialogScript {

    public DScript_1A_POST_Leif_Antal_Campfire(GridScreen gridScreen) {
        super(gridScreen);
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

            // lingers before beginning. Owls, frogs, and other
            // forest animal sounds can be heard.

            set(LEIF_WORRIED, "We can rest here until dawn.");

            set(ANTAL_EXHAUSTED, "You'll hear no complaints from me.", RIGHT, true);

            choreographLinger();

            set(ANTAL_WORRIED, "...I hope my family made it out alright.", RIGHT, true);

            set(LEIF_CURIOUS, "You had family in the city?");

            set(ANTAL_WORRIED, "My mother and brother.", RIGHT, true);

            set(LEIF_CURIOUS, "And you left them behind?");

            set(ANTAL_SAD, "...", RIGHT, true);

            choreographLinger();

            set(LEIF_THINKING, "...I didn't see anyone fleeing south along the coast. If your family made it out, they'd have to be following the same road as everybody else, Westward to the walled city.");

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

            choreographLinger();

            // the pair move to douse the fire quickly. MOVE + SPAWN choreography?

            // a rider SPAWN from east side of road and move to west before REMOVE

            set(LEIF_WORRIED, "A scout... or a messenger, perhaps?");

            set(ANTAL_DEVASTATED, "This is awful, why is this happening?", RIGHT, true);

            set(LEIF_CURIOUS, "\"Why\"? Aren't you at war with them?");

            set(ANTAL_CURIOUS, "War? Of course not. We've had trade agreements with the North since I was a boy -- albeit always a bit tense, neither side likes letting anyone cross the border. What do you mean, \"you\" and \"them\"? Where are you from, again?", RIGHT, true);

            set(LEIF_THINKING, "A small farm on the plains. I've never followed politics much.");

            // continues... "this is unprecedented, nothing like this has ever happened in living memory"
        }
    }
}
