package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.world;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.GridCutsceneChoreography;

public class GridCutsceneWorldChoreography extends GridCutsceneChoreography {

    public enum ChoreoType {
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

    private final ChoreoType choreoType;
    private Vector2 associatedCoordinate = new Vector2();
    private Abilities ability;
    private ScreenAdapter screenForTransition;

    protected GridCutsceneWorldChoreography(ChoreoType type) {
        super(Target.WORLD);
        this.choreoType = type;
    }

    public ChoreoType getChoreoType() {
        return choreoType;
    }
}
