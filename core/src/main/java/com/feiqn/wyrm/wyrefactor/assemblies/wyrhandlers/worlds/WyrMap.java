package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrMap extends WyrHandler {

    protected boolean isBusy = false;

    public WyrMap() {}

    public WyrMap(MetaHandler metaHandler) {
        super(metaHandler);
    }

    public boolean isBusy() { return isBusy; }

    public void standardize() {}

}
