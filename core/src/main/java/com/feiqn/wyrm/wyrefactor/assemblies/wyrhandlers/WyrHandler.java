package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrHandler<Handler extends MetaHandler<?,?,?,?,?,?,?,?,?,?>> implements Wyr {

    protected Handler h; // It's fun to just type "h".

    protected boolean isBusy = false;

    protected WyrHandler() {}

    public boolean isBusy() {
        if(isBusy) Gdx.app.log("busy", "" + this);
        return isBusy;
    }

}
