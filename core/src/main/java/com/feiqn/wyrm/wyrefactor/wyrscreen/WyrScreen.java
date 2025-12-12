package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrScreen extends ScreenAdapter {

    protected final Wyr wyr;

    protected final MetaHandler handlers;


    // Not sure if these three should be here.
//    protected final WyrComputerPlayer        cpPlayer;
//    protected final WyrComputerPlayerHandler cpHandler;
//    protected final WyrConditionsHandler     conditions;

    public WyrScreen(WYRMGame root, WyrType wyrType, MetaHandler handlers) {
        wyr = new Wyr(root, wyrType);
        this.handlers = handlers;

//        this.cpHandler = cpHandler;
//        this.cpPlayer = cpPlayer;
//        this.conditions = conditionsHandler;
    }

    public WyrType wyrType() { return wyr.Type(); }
//    public WyrComputerPlayer computerPlayer() { return  cpPlayer; }
//    public WyrComputerPlayerHandler cpHandler() { return cpHandler; }

}
