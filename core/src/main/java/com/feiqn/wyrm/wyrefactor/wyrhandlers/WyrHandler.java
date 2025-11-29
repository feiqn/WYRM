package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;

public abstract class WyrHandler {

    protected final WYRMGame root;

    protected WyrHandler(WYRMGame root) {
        this.root = root;
    }
}
