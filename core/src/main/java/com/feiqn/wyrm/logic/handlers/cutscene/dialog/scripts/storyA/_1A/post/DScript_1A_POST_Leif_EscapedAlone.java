package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.post;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;

import static com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression.*;

public class DScript_1A_POST_Leif_EscapedAlone extends ChoreographedCutsceneScript {

    public DScript_1A_POST_Leif_EscapedAlone(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_POST_LEIF_ESCAPEDALONE);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        choreographFocusOnUnit(ags.conditions().teams().getPlayerTeam().get(0));

        set(LEIF_EXHAUSTED, "I think we managed to escape.");

        set(LEIF_WINCING, "We barely made it out with our lives.");

        set(LEIF_WORRIED, "But that knight, and all those people in that city...");

        set(LEIF_WORRIED, "...they're dead.");

        set(LEIF_WINCING, "Damn it! I fled here to escape, and now I've gotten caught in a civil war?");

        set(LEIF_WORRIED, "...And I've gotten you caught in a war, too, haven't I.");

        choreographLinger();

        set(LEIF_WORRIED, "We need to find shelter for the night, and eat something.");

        set(LEIF_EXHAUSTED, "I'm sure dad would just hunt a boar to cook, and build a shelter from stones and branches.");

        set(LEIF_THINKING, "Do they even have wild boar here?");

        set(LEIF_DETERMINED, "We'll just have to make due however we can.");

        set(LEIF_THINKING, "And if there's war to the North, I suppose our best bet is to continue flying south.");

        set(LEIF_CURIOUS, "This road has to go somewhere, presumably there's another city.");

        set(LEIF_ANNOYED, "This would be easier if I had a map, at least then I'd know how far we have to go.");

        set(LEIF_WORRIED, "For your sake at least, you've been carrying me ever since we left home. I don't think you've ever had a workout like this in your life.");

        set(LEIF_CURIOUS, "How far have we even come? The world seems so small, for this to be the edge of it.");

        set(LEIF_CURIOUS, "Is there truly nothing beyond the sea?");

        choreographShortPause();

        set(LEIF_DETERMINED, "Let's get moving.");

        choreographFadeOut();

        choreographTransitionScreen(new MainMenuScreen(game));
    }


}
