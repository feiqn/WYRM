package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public class WyrComputerPlayerHandler extends WyrHandler {

    protected WyrComputerPlayer computerPlayer;

    protected WyrComputerPlayerHandler(WYRMGame root, WyrType wyrType) {
        super(root, wyrType);
    }

}
