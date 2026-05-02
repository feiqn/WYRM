package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrMapHandler<handler extends MetaHandler<?,?,?,?,?,?,?,?,?,?>> extends WyrHandler<handler> {

    protected boolean isBusy = false;

    protected WyrMapHandler() {}

    public boolean isBusy() { return isBusy; }

}
