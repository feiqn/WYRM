package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class GridCutscenePlayer extends WyrCutscenePlayer {

    public GridCutscenePlayer(RPGridMetaHandler metaHandler) {
        super(metaHandler);
    }

    @Override
    public RPGridMetaHandler h() {
        assert super.h() instanceof RPGridMetaHandler;
        return (RPGridMetaHandler) super.h();
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
