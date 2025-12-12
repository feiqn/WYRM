package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;

public abstract class WyrScreen extends ScreenAdapter {

    protected final WyrType wyrType;

    protected final WYRMGame root;

    // Not sure if these three should be here.
    protected final WyrComputerPlayer        cpPlayer;
    protected final WyrComputerPlayerHandler cpHandler;
    protected final WyrConditionsHandler     conditions;

    public WyrScreen(WYRMGame root, WyrType wyrType, WyrComputerPlayer cpPlayer, WyrComputerPlayerHandler cpHandler, WyrConditionsHandler conditionsHandler) {
        this.root = root;
        this.wyrType = wyrType;
        this.cpHandler = cpHandler;
        this.cpPlayer = cpPlayer;
        this.conditions = conditionsHandler;
    }

    public WyrType getType() { return wyrType; }
    public WyrComputerPlayer computerPlayer() { return  cpPlayer; }
    public WyrComputerPlayerHandler cpHandler() { return cpHandler; }

}
