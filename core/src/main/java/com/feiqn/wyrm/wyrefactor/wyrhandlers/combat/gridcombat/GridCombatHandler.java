package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCombatHandler extends WyrCombatHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridCombatHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    @Override
    protected void queueCombat() {

    }
}
