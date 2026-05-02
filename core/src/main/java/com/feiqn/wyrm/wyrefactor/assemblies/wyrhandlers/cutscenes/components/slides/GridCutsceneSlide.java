package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutsceneChoreography;

public final class GridCutsceneSlide extends WyrCutsceneSlide<GridCutsceneChoreography> {

    public GridCutsceneSlide() {}

    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }

}
