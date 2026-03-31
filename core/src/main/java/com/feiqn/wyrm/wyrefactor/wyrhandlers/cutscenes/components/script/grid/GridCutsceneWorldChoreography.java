package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.AbilityID;

public class GridCutsceneWorldChoreography extends GridCutsceneChoreography {

    // refactor of CutsceneFrameChoreography

    // "Choreography is stuff that happens on the map / over-world,
    // as opposed to DialogActions which happen inside the Conversation window."

    private final WorldChoreoType choreoType;
    private final Vector2 associatedCoordinate = new Vector2();
    private AbilityID ability;
    private ScreenAdapter screenForTransition;

    protected GridCutsceneWorldChoreography(WorldChoreoType type) {
        super(ChoreoStage.WORLD);
        this.choreoType = type;
    }

    public WorldChoreoType getChoreoType() {
        return choreoType;
    }
}
