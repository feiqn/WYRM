package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._2A.pre;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;

import static com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition.CENTER;
import static com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition.RIGHT;

public class DScript_2A_PRE_LeifAntal_GatesAreClosed extends ChoreographedCutsceneScript {

    /* Leif and Antal arrive at the gates of the walled city.
     * They're the last of the refugees to arrive due to Antal's
     * sluggish speed, and they find the gates barred with crowds
     * of refugees outside trying to get in.
     */

    public DScript_2A_PRE_LeifAntal_GatesAreClosed(WYRMGame game) {
        super(game, CutsceneID.CSID_2A_PRE_LEIFANTAL_GATESARECLOSED);
    }

    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(ANTAL_EXHAUSTED, "We made it, the walled city.", RIGHT, true);

        set(LEIF_CURIOUS, "That's really what people call it? Your city had walls too, you know.");

        set(ANTAL_CURIOUS, "I suppose out on the plains you just refer to any settlement as \"The City\"?", RIGHT, true);

        set(LEIF_CURIOUS, "We probably would have referred to this as \"Mountain Town\"");

        set(ANTAL_WORRIED, "My city had mountains too, you know.", RIGHT, true);

        choreographLinger();

        set(ANTAL_WORRIED, "...I don't see my family anywhere.", RIGHT, true);

        set(LEIF_CURIOUS, "Maybe they're already inside? We should get in line for entry.");

        set(ANTAL_WORRIED, "Wait, what's that guard doing?", RIGHT, true);

        // focus on soldier

        choreographShortPause();

        set(GENERIC_SOLDIER, "Attention everyone!", CENTER);

        set(GENERIC_SOLDIER, "The city gates are now closed! We are at capacity and can no longer accept any further refugees into our walls.", CENTER);

        // panic from the crowd

        choreographShortPause();

        set(LEIF_ANNOYED, "Closed?! How can they just lock people out here on the road? They'll be slaughtered if there's another attack here.");

        set(ANTAL_DEVASTATED, "\"Slaughtered\"..?", RIGHT, true);

        set(ANTAL_DEVASTATED, "...my family, they have to be inside already!", RIGHT, true);

        set(LEIF_WORRIED, "...");

        choreographLinger();

        set(LEIF_DETERMINED, "Look, there's a path through the woods to the south.");

        // focus on location

        set(LEIF_DETERMINED, "Come on, maybe we can find another way in.");

        set(ANTAL_WORRIED, "W-wait for me!", RIGHT, true);

        // Leif and Antal walk south.

        // Fade out to black

        // next cutscene: _StealthIntoCity
    }

}
