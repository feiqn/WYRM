package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridComputerPlayer extends WyrComputerPlayer {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayer(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

}
