package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrScreen extends ScreenAdapter {

    protected final Wyr_DEPRECATED wyr;

    // Not sure if these three should be here.
//    protected final WyrComputerPlayer        cpPlayer;
//    protected final WyrComputerPlayerHandler cpHandler;
//    protected final WyrConditionsHandler     conditions;


    public WyrScreen(WyrType wyrType) {
        this.wyr = new Wyr_DEPRECATED(wyrType);

//        this.cpHandler = cpHandler;
//        this.cpPlayer = cpPlayer;
//        this.conditions = conditionsHandler;
    }

    public WyrType wyrType() { return wyr.type(); }
    public abstract MetaHandler handlers();
//    public WyrComputerPlayer computerPlayer() { return  cpPlayer; }
//    public WyrComputerPlayerHandler cpHandler() { return cpHandler; }

}
