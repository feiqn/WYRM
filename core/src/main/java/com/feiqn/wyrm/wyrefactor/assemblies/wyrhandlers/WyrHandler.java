package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public abstract class WyrHandler implements Wyr {

    private MetaHandler h = null; // It's fun to just type "h".

    protected boolean isBusy = false;

    protected WyrHandler() {}

    protected WyrHandler(MetaHandler metaHandler) {
        this.h = metaHandler;
    }

    public boolean isBusy() {
        if(isBusy) Gdx.app.log("busy", "" + this);
        return isBusy;
    }

    protected MetaHandler h() {
        return h;
    }

}
