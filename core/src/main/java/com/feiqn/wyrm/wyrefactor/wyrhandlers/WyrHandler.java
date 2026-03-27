package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrHandler extends Wyr_DEPRECATED {

    // Space to grow later.

    protected WyrHandler(WyrType type) {
        super(type);

    }

    protected WyrHandler(WYRMGame root, WyrType wyrType) {
        super(wyrType);
    }



}
