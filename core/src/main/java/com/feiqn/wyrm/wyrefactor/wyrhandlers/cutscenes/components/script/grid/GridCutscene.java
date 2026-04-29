package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutscene;

public abstract class GridCutscene extends WyrCutscene<RPGridUnit> {

    public GridCutscene(CutsceneID id) {
        super(id);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }
}
