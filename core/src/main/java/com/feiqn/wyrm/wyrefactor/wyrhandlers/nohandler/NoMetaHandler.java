package com.feiqn.wyrm.wyrefactor.wyrhandlers.nohandler;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class NoMetaHandler extends MetaHandler {
    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public WyrScreen<?> screen() {
        return null;
    }
}
