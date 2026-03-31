package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public abstract class WyrCutsceneChoreography<
        T extends WyrActor
            > extends Subjectivity<T> implements Wyr {

    public enum ChoreoStage {
        WORLD,
        DIALOG,
    }

    public enum DialogChoreoType {
        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
        CHOREOGRAPHY,
        ARBITRARY_CODE,
    }

    public enum WorldChoreoType {
        SPAWN,
        DESPAWN,
        MOVE,
        FOCUS_UNIT,
        FOCUS_TILE,
        UNIT_DEATH,
        SHORT_PAUSE,
        LINGER,
        ATTACK,
        USE_ABILITY,
        REVEAL_VICTORY_CONDITION,
        SCREEN_TRANSITION,
        FADE_OUT_TO_BLACK,
    }

    private final ChoreoStage choreoStage;

    protected CampaignFlags associatedCampaignFlag;

    protected WyrCutsceneChoreography(ChoreoStage choreoStage) { this.choreoStage = choreoStage; }

    public ChoreoStage getChoreoStage() { return choreoStage; }

}
