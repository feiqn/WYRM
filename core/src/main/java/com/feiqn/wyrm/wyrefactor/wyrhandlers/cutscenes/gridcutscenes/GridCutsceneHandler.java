package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCutsceneHandler extends WyrCutsceneHandler<GridUnit, GridCutsceneScript, GridCutscenePlayer> {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridCutsceneHandler(GridMetaHandler metaHandler) {
        this.h = metaHandler;
        cutscenePlayer = new GridCutscenePlayer(metaHandler);
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
