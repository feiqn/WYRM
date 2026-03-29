package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.world;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides.choreography.GridCutsceneChoreography;

public class GridCutsceneWorldChoreography extends GridCutsceneChoreography {

    // refactor of CutsceneFrameChoreography

    // "Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation window."

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
    private AbilityID ability;
    private ScreenAdapter screenForTransition;

    protected GridCutsceneWorldChoreography(ChoreoType type) {
        super(Set.WORLD);
        this.choreoType = type;
    }

    public ChoreoType getChoreoType() {
        return choreoType;
    }
}
