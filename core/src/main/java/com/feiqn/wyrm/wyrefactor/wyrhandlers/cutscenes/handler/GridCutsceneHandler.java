package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player.GridCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class GridCutsceneHandler extends WyrCutsceneHandler<RPGridUnit, GridCutscene, GridCutscenePlayer> {

    private final RPGridMetaHandler h; // It's fun to just type "h".

    public GridCutsceneHandler(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
//        cutscenePlayer = new GridCutscenePlayer(metaHandler);
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
