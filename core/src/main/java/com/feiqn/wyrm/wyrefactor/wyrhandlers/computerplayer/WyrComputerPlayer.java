package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrComputerPlayer extends Wyr {

    // functions as cp action builder and
    // deliberator.

    protected WyrComputerPlayer(WyrType wyrType) {
        super(wyrType);
    }

}
