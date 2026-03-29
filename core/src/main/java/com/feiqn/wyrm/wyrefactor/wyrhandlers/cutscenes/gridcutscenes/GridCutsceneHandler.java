package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneScript;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCutsceneHandler extends WyrCutsceneHandler<GridActor> {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final GridCutscenePlayer player;

    public GridCutsceneHandler(GridMetaHandler metaHandler) {
        this.h = metaHandler;
        player = new GridCutscenePlayer(metaHandler);
    }

    @Override
    public void startCutscene(WyrCutsceneScript<GridActor> script) {

        // communicate w/ cs player to begin acting
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
