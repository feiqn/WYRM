package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player.GridCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCutsceneHandler extends WyrCutsceneHandler<GridUnit, GridCutscene, GridCutscenePlayer> {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridCutsceneHandler(GridMetaHandler metaHandler) {
        this.h = metaHandler;
//        cutscenePlayer = new GridCutscenePlayer(metaHandler);
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
