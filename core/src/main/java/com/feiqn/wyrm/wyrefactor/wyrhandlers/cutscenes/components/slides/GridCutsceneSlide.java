package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutsceneChoreography;

public final class GridCutsceneSlide extends WyrCutsceneSlide<GridCutsceneChoreography> {

    public GridCutsceneSlide() {}

    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }

}
