package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutscene;

public abstract class GridCutscene extends WyrCutscene<GridUnit> {

    public GridCutscene(CutsceneID id) {
        super(id);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }
}
