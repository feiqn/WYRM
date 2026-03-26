package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid.GridCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridComputerPlayer extends WyrComputerPlayer {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayer(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    private GridCPAction buildAggressiveAction(GridUnit unit) {

        // TODO:
        //  - refactor from old_ai
        return new GridCPAction(ActionType.ATTACK_ACTION);
    }

    // TODO:
    //  - best or worst combat action


}
