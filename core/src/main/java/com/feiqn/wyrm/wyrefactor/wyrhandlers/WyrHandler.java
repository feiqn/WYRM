package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrHandler implements Wyr {

    // Space to grow later.
    // Very abstract.

    protected boolean isBusy = false;

    protected WyrHandler() {}

    public boolean isBusy() { return isBusy; }

}
