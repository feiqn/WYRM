package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrHandler extends Wyr {

    // Space to grow later.

    protected WyrHandler(WyrType type) {
        super(type);

    }

    protected WyrHandler(WYRMGame root, WyrType wyrType) {
        super(wyrType);
    }



}
