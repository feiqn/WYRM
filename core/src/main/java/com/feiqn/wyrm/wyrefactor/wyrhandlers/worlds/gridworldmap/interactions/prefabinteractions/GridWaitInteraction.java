package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public final class GridWaitInteraction extends GridInteraction {

    public GridWaitInteraction(GridActor parent) {
        super(parent, null, InteractionType.WAIT, 0);
    }
}
