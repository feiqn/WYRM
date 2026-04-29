package com.feiqn.wyrm.wyrefactor.wyrinterface;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;

public interface WyriHandler {

//    MetaHandler h = null; // It's fun to just type "h".

    boolean isBusy = false;

    default boolean isBusy() {
        if(isBusy) Gdx.app.log("busy", "" + this);
        return isBusy;
    }

}
