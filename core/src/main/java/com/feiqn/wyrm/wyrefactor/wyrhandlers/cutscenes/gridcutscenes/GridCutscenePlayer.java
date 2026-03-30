package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutscenePlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.GridCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCutscenePlayer extends WyrCutscenePlayer<GridUnit, GridCutsceneScript> {

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
