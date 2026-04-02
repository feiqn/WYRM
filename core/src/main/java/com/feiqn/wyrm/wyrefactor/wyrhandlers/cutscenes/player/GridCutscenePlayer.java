package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCutscenePlayer extends WyrCutscenePlayer<GridUnit, GridCutscene> {

    private GridMetaHandler h;

    public GridCutscenePlayer(GridMetaHandler metaHandler) {
        super(metaHandler.screen().getGameStage());
        this.h = metaHandler;
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
