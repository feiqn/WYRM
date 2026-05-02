package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;

public abstract class GridCutscene extends WyrCutscene<RPGridUnit> {

    public GridCutscene(CutsceneID id) {
        super(id);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }
}
