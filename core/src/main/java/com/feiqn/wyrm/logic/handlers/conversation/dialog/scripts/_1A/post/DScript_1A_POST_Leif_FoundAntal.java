package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_1A_POST_Leif_FoundAntal extends DialogScript {

    public DScript_1A_POST_Leif_FoundAntal() { super(); }

    @Override
    protected void setSeries() {
        /* Leif, flying high, sees the destruction and fire
         * in the city, then spots Antal lagging behind other
         * fleeing survivors on the road.
         * Leif swoops down.
         */
        set(CharacterExpression.LEIF_DETERMINED, "Hey!", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_DETERMINED, "What the hell? Shouldn't you be protecting the city?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_DEVASTATED, "I have to get out of here! I'm no soldier, just a ceremonial guard! They just pay me to wear the overlarge armor and look stoic outside the palace gates!");

        set(CharacterExpression.LEIF_ANNOYED, "Ceremonial...? So you're fleeing your home without a fight?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_DEVASTATED, "Aren't you listening? I don't know how to fight!");
        set(CharacterExpression.ANTAL_SAD, "All I can do is run... and hope my family does the same.");

        set(CharacterExpression.LEIF_CURIOUS, "Family? And you haven't seen them among the fleeing survivors?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_EXHAUSTED, "This armor weighs me down so much, I'm sure they've just gone ahead of me.");
        set(CharacterExpression.ANTAL_WORK_FACE, "For the western city, by my guess. It's closer than the capital and well fortified.");

        set(CharacterExpression.LEIF_THINKING, "The western city? Is that where you're going?", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "Yes, it's either there or the capital, but the road south is much farther than the road west.");
        set(CharacterExpression.ANTAL_WORK_FACE, "Though with that winged horse of yours, you might make the distance in a day.");
        set(CharacterExpression.ANTAL_CURIOUS, "I've... never seen a horse like that up close. Only in pictures, and as far off specs among the mountaintops.");
        set(CharacterExpression.ANTAL_WORK_FACE, "I wasn't aware anyone rode atop them... where did you-");
        lastFrame().setAutoplayNext(true);

        set(CharacterExpression.LEIF_WORRIED, "We should pick up the pace, we need to find shelter before nightfall. The world is volatile right now, people will be confused and desperate.", SpeakerPosition.RIGHT, true);
        set(CharacterExpression.LEIF_THINKING, "It will be safest if we stick together and avoid talking to anyone else until we reach the city.", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "Oh, okay... wait, we? You want to travel together? Surely I will only slow you down. With your winged horse-");
        lastFrame().setAutoplayNext(true);

        set(CharacterExpression.LEIF_WINCING, "Let's just, keep moving. Try to keep your eyes on the road. Don't look back at the city.", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_SAD, "...");
        set(CharacterExpression.ANTAL_SAD, "Right...");

    }
}
