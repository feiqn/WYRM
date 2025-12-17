package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrComputerPlayerHandler extends WyrHandler {

    protected WyrComputerPlayer computerPlayer;

    protected WyrComputerPlayerHandler(WyrType wyrType) {
        super(wyrType);
    }

}
