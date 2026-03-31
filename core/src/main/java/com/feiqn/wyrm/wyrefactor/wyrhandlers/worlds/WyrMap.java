package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrMap implements Wyr {

    protected boolean isBusy = false;

    protected WyrMap() {}

    public boolean isBusy() { return isBusy; }

}
