package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;

public class GridComputerPlayer extends WyrComputerPlayer {

    public GridComputerPlayer(WYRMGame root) {
        super(root, WyrType.GRIDWORLD);
    }

}
