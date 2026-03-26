package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrComputerPlayerHandler extends WyrHandler {

    // Does all the "thinking" for non-player entities,
    // particularly handling CP Actions (instruction packages)
    // which built and passed in by WyrComputerPlayer and are
    // then passed to WyrActorHandler.
    // Also responsible for parsing handling of multiple units
    // sharing priority, and deliberating the best action order
    // to take for strategic play.

    protected WyrComputerPlayer computerPlayer;

    protected WyrComputerPlayerHandler(WyrType wyrType) {
        super(wyrType);
    }

}
