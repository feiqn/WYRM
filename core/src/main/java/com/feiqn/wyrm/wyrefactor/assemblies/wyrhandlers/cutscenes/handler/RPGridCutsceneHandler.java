package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player.GridCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class RPGridCutsceneHandler extends WyrCutsceneHandler {

    public RPGridCutsceneHandler(RPGridMetaHandler metaHandler) {
        super(metaHandler, new GridCutscenePlayer(metaHandler));
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
