package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;

public final class GridHUD extends WyrHUD {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridHUD(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    @Override
    protected void build() {

    }
}
