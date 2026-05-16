package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.GridCutsceneChoreography;

public final class RPGridCutsceneShot extends WyrCutsceneShot {

    public RPGridCutsceneShot() {}

    public RPGridCutsceneShot(GridCutsceneChoreography choreo) {
        super(choreo);
    }



    @Override
    public GridCutsceneChoreography getChoreo() {
        assert super.getChoreo() instanceof GridCutsceneChoreography;
        return (GridCutsceneChoreography) super.getChoreo();
    }
    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }

}
