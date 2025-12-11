package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;

public class GridComputerPlayerHandler extends WyrComputerPlayerHandler {

    public GridComputerPlayerHandler(WYRMGame root) {
        super(root, WyrType.GRIDWORLD);
    }

}
