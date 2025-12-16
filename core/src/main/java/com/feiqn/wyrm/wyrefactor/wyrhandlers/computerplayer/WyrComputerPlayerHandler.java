package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrComputerPlayerHandler extends WyrHandler {

    protected WyrComputerPlayer computerPlayer;

    protected WyrComputerPlayerHandler(WyrType wyrType) {
        super(wyrType);
    }

}
