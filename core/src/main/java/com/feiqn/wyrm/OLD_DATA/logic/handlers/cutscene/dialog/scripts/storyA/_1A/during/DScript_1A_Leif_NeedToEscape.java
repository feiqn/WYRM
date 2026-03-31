package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.scripts.storyA._1A.during;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.CharacterExpression;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_ChoreographedCutsceneScript;

public class DScript_1A_Leif_NeedToEscape extends OLD_ChoreographedCutsceneScript {

    // Opening CS for 1A.

    public DScript_1A_Leif_NeedToEscape(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_LEIF_NEED_TO_ESCAPE);
    }

    @Override
    protected void declareTriggers() {
        armTurnCutsceneTrigger(1, false, false);
    }

    @Override
    protected void setSeries() {
        if(ags == null) return;
        if(slideshow.size != 0) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WINCING, "I've got to get out of here...");

        choreographFocusOnLocation(45, 20);

        set(CharacterExpression.LEIF_PANICKED, "That's where I left " + WYRMGame.assets().bestFriendName + ", but...");

        choreographFocusOnLocation(40, 23);

        set(CharacterExpression.LEIF_WORRIED, "There's no way I can push past those flames.");

        choreographFocusOnLocation(9, 23);

        set(CharacterExpression.LEIF_WORRIED, "...and the path back west is barricaded by all those soldiers.");

        choreographFocusOnLocation(30, 28);

        set(CharacterExpression.LEIF_PANICKED, "Oh man, what do I do... I've got to get out of here!");

        choreographRevealVictCon(CampaignFlags.STAGE_1A_CLEARED);

    }
}
