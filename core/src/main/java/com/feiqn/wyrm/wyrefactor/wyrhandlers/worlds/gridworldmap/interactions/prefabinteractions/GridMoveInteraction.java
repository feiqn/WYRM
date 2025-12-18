package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction.InteractionType.MOVE;

public final class GridMoveInteraction extends GridInteraction {

//    private final GridMetaHandler h;
    private final GridPath path;

    public GridMoveInteraction(GridActor parent, GridPath path) {
        super(parent, MOVE, 0);
//        this.h = metaHandler;
        this.path = path;
    }

    public GridPath getPath() { return path;}

//    @Override
//    public void payload() {
//        h.actors().followPath((GridActor) parent, path);
//    }

}
