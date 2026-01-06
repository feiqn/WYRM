package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction.InteractionType.MOVE;

public final class GridMoveInteraction extends GridInteraction {

    private final GridPath path;

    public GridMoveInteraction(GridActor parent, GridPath path) {
        super(parent, null, MOVE, 0);
        this.path = path;
    }

    public GridPath getPath() { return path;}

}
