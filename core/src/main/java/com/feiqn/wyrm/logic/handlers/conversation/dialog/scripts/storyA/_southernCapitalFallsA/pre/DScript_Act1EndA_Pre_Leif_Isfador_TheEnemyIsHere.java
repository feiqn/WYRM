package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts.storyA._southernCapitalFallsA.pre;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.*;

public class DScript_Act1EndA_Pre_Leif_Isfador_TheEnemyIsHere extends ChoreographedDialogScript {

    public DScript_Act1EndA_Pre_Leif_Isfador_TheEnemyIsHere(WYRMGame game) {
        super(game);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        // inside the Southern Capital Throne Room

        set(LEIF_DETERMINED, "Queen Isfador, the northern armies are at the capital's doorstep.");

        set(LEIF_DETERMINED, "They've secured the road between here and the border, and their reinforcements march freely towards us from their homeland.");

        set(ISFADOR_STOIC, "...", RIGHT, true);

        set(ISFADOR_STOIC, "Then Artorias has failed.", RIGHT, true);

        set(ISFADOR_STOIC, "And the walled city?", RIGHT, true);

        set(LEIF_DETERMINED, "Northern forces have it fully surrounded, and they control the roads leading to and from the city; however, when I last flew over, the walls had yet to be breached.");

        set(ISFADOR_STOIC, "Then hope is not lost.", RIGHT, true);

        set(LEIF_SURPRISED, "I don't think we can expect any help from the other cities...");

        set(ISFADOR_STOIC, "Nor will we need any.", RIGHT, true);

        set(ISFADOR_STOIC, "If Richard's forces are inadequate to breach our lesser cities, they shall find no footing here.", RIGHT, true);

        set(LEIF_ANNOYED, "They were quite adequate at the border.");

        set(ANTAL_WORRIED, "Leif, she's the Queen, you may want to watch your tone.", FAR_LEFT, false);

        set(ISFADOR_STOIC, "The border city fell to cowards' tricks. Without the element of surprise, Richard's crusade shall find its end here.", RIGHT, true);

        set(ANTAL_SAD, "...", FAR_LEFT, false);

        set(LEIF_WORRIED, "Are you so sure of this that you would wager your own life, your kingdom, the lives of everyone in this city?");

        set(ISFADOR_STOIC, "Your companion speaks wisdom, young one. I grieve with you for the loss of your home, but you would do well to remember that I am your Queen.", RIGHT, true);

        set(LEIF_ANNOYED, "...");

        set(ISFADOR_STOIC, "In any case, it is as you said yourself: northern armies patrol the road to the walled city. What would you have us do but stay and fight for our homeland?", RIGHT, true);

        set(LEIF_DETERMINED, "They control the roads, but they fear the sea. We can fashion rafts, and follow the western coast north.");

        set(ISFADOR_STOIC, "They fear the sea with good reason, child. Not even the mad king in the west dares to disturb the still waters.", RIGHT, true);

        set(LEIF_DETERMINED, "So you would doom us all for superstition?! Isfador, we have to get our of here!");

        set(ISFADOR_STOIC, "Enough! Child, your sorrow will excuse your insolence no longer! A decision has been made, we will stand and fight for our home. All that remains in question is whether you will take up arms beside us, or cower in surrender behind us. In either case, your audience with me is at an end. Begone.", RIGHT, true);

        set(LEIF_DETERMINED, "...So be it.");

        // NEXT: Leif_Antal_ItsCalledABoat
    }
}
