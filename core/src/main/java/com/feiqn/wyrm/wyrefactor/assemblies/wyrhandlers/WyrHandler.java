package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public abstract class WyrHandler implements WyrFrame {

    protected boolean isBusy = false;

    protected WyrHandler() {}

    public boolean isBusy() {
        if(isBusy) Gdx.app.log("busy", "" + this);
        return isBusy;
    }

    public boolean standardize() {
        return true;
    }

}
