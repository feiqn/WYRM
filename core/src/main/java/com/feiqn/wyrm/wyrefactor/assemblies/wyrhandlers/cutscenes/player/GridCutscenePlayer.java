package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class GridCutscenePlayer extends WyrCutscenePlayer<GridCutscene> {

    private RPGridMetaHandler h;

    public GridCutscenePlayer(RPGridMetaHandler metaHandler) {
        super();
        this.h = metaHandler;
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
