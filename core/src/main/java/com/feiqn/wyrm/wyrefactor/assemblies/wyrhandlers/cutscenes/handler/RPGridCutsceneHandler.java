package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player.GridCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class RPGridCutsceneHandler extends WyrCutsceneHandler {

    public RPGridCutsceneHandler(RPGridMetaHandler metaHandler, Skin skin) {
        super(metaHandler, new GridCutscenePlayer(metaHandler, skin));
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
