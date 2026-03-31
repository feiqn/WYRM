package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutsceneChoreography;

public final class GridCutsceneSlide extends WyrCutsceneSlide<GridUnit, GridCutsceneChoreography> {

    public GridCutsceneSlide() {}

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
