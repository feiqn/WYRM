package com.feiqn.wyrm.wyrefactor;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class Wyr {

    // Any class which extends an existing
    // datatype (Such as Image, ScreenAdapter)
    // will need to define these variables for
    // themselves; but classes which do not
    // extend existing datatypes may extend
    // this for easy reference management.

    // This might be a horrible idea that I have
    // to go through and undo later. Not sure
    // right now, so here we go.

    private final WYRMGame root;

    private final WyrType wyrType;

    protected static MetaHandler h;

    protected Wyr(WYRMGame root, WyrType wyrType) {
        this.root = root;
        this.wyrType = wyrType;
        h = root.handlers();
    }

    protected WYRMGame root() { return root; }
    public WyrType getWyrType() { return wyrType; }

}
