package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid.GridCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

public final class GridComputerPlayerHandler extends WyrComputerPlayerHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayerHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        computerPlayer = new GridComputerPlayer(h);
    }

    public void run(GridUnit unit) {

        // TODO:
        //  handle many units on same tick

        final GridComputerPlayer GCP = (GridComputerPlayer) computerPlayer;
        final GridInteraction action = GCP.preferredAction(unit);

        h.actors().parseInteractable(action);

    }

}
