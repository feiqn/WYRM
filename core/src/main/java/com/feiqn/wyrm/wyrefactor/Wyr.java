package com.feiqn.wyrm.wyrefactor;

import com.feiqn.wyrm.WYRMGame;

public class Wyr {

    // Can be extended or just instantiated
    // as a standalone child object.

    // Now that root is a static singleton,
    // this might be redundant overhead.
    // Maybe not, though.

    protected final WyrType wyrType;

    public Wyr(WYRMGame root, WyrType wyrType) {
        // Obsolete constructor, but lots of inheritors already
        // set up calling this via super, so leaving and wrapping
        // out of convenience for now.
        this(wyrType);
    }

    public Wyr(WyrType type) {
        this.wyrType = type;
//        h = WYRMGame.activeScreen().handlers();
    }

    // These each look better depending on
    // whether Wyr is instantiated or extended.
    public WyrType wyrType() { return wyrType; }
    public WyrType type()    { return wyrType; }

}
