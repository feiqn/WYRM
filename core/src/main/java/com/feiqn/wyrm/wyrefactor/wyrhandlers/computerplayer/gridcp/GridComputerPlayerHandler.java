package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid.GridCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridComputerPlayerHandler extends WyrComputerPlayerHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayerHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        computerPlayer = new GridComputerPlayer(h);
    }

    public void run(GridUnit unit) {

        final GridCPAction action = ((GridComputerPlayer)computerPlayer).bestAction(unit);

        // TODO:
        //  - deliberate and construct action
        //  - parse action into actor handler
    }
}
