package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player.GridCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class RPGridCutsceneHandler extends WyrCutsceneHandler {

    public RPGridCutsceneHandler(RPGridMetaHandler metaHandler, Skin skin) {
        super(metaHandler, new GridCutscenePlayer(metaHandler, skin));
    }

    @Override
    protected void startCutscene(WyrCutscene script) {
        super.startCutscene(script);
//        Gdx.app.log("csHandle", "grid.start called");
        h().clearEphemeral();
        h().map().hideAllHighlights();
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

    @Override
    protected RPGridMetaHandler h() {
        assert super.h() instanceof RPGridMetaHandler;
        return (RPGridMetaHandler) super.h();
    }

}
