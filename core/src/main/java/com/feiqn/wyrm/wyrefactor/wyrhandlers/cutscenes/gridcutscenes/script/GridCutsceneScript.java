package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneScript;

public abstract class GridCutsceneScript extends WyrCutsceneScript<GridUnit> {

    public GridCutsceneScript(CutsceneID id) {
        super(id);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }
}
