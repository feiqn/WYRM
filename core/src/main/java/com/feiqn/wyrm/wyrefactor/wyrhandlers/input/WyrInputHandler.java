package com.feiqn.wyrm.wyrefactor.wyrhandlers.input;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;

public abstract class WyrInputHandler extends WyrHandler {

    // Might be superfluous, leaving in as space for
    // higher level thins such as keyboard / controller / touch
    // handling, etc.

    protected WyrInputHandler(WYRMGame root) {
        super(root);
    }

}
