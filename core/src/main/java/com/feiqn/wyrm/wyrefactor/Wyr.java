package com.feiqn.wyrm.wyrefactor;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public class Wyr {

    // Can be extended or just instantiated
    // as a floating object.

    protected final WYRMGame root;

    protected final WyrType wyrType;

    protected static MetaHandler h;

    public Wyr(WYRMGame root, WyrType wyrType) {
        this.root = root;
        this.wyrType = wyrType;
        h = root.handlers();
    }

    public WYRMGame root() { return root; }
    public WyrType Type() { return wyrType; }
    public MetaHandler handlers() { return h; }

}
