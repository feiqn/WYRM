package com.feiqn.wyrm.wyrefactor.wyrhandlers.input;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrInputHandler extends WyrHandler {

    // Might be superfluous, leaving in as space for
    // higher level things such as keyboard / controller / touch
    // handling, etc.

    protected WyrInputHandler(WYRMGame root, WyrType wyrType) {
        super(root, wyrType);
    }

}
