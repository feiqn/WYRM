package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class GridCutscenePlayer extends WyrCutscenePlayer {

    public GridCutscenePlayer(RPGridMetaHandler metaHandler, Skin skin) {
        super(metaHandler, skin);
    }

    @Override
    protected void endScene() {
        Gdx.app.log("CS Player", "end scene");
        layout.addAction(Actions.fadeOut(.3f));
        isBusy = false;
        activeCutscene = null;
        // todo: stagger w/ timer
        h().standardizeParse();
    }
    @Override
    protected void parseWorldChoreo(WyrInteraction interaction) {
      if(interaction instanceof RPGridInteraction) {

      } else {
          super.parseWorldChoreo(interaction);
      }
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
