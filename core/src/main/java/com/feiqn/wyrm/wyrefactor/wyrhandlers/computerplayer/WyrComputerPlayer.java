package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrComputerPlayer extends Wyr {

    // functions as cp action builder

    protected WyrComputerPlayer(WyrType wyrType) {
        super(wyrType);
    }

}
