package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

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
