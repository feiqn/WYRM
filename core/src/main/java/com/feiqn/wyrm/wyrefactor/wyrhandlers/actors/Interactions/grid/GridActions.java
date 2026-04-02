package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction.InteractionType.*;

public final class GridActions {

    private GridActions() {}

    public static GridInteraction MoveThenWait(GridActor subject, GridPath path) {
        final GridInteraction i = new GridInteraction(subject, null, MOVE_WAIT, 0);

    }

}
