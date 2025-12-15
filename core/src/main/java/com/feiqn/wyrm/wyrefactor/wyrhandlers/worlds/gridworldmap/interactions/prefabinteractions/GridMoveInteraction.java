package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction.InteractionType.GRID_MOVE;

public final class GridMoveInteraction extends GridInteraction {

    private final GridMetaHandler h;
    private final GridPath path;

    public GridMoveInteraction(GridMetaHandler metaHandler, GridActor parent, GridPath path) {
        super(parent, GRID_MOVE, 0, "Move Here", "Move to this location.");
        this.h = metaHandler;
        this.path = path;
    }

    @Override
    public void payload() {
        h.actors().followPath((GridActor) parent, path);
    }
}
