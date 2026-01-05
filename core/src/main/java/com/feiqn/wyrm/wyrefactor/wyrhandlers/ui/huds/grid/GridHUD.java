package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.elements.grid.GHUD_TurnOrder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.elements.grid.GHUD_UnifiedInfo;

public final class GridHUD extends WyrHUD {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final Table subTable = new Table();

    private final GHUD_UnifiedInfo unifiedInfo;
    private final GHUD_TurnOrder   turnOrder;

    // TODO:
    //  - unified contextual info box (top-right)
    //  - turn order (top left)
    //  - context menu for interactables (mid-upper left)
    //  - other popups and fullscreen menus / displays (combat, inventory, etc.)

    public GridHUD(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        unifiedInfo = new GHUD_UnifiedInfo(h);
        turnOrder   = new GHUD_TurnOrder(h);

        // Since subTable is added to parent table
        // and not a stack, it needs to set its own size.
        subTable.setFillParent(true);

        buildLayout();
    }

    @Override
    protected void buildLayout() {
        layout.clearChildren();
        subTable.clearChildren();

        layout.add(turnOrder).colspan(2).top();
        layout.add(subTable).right();
        subTable.add(unifiedInfo);

        // TODO: etc... (see design notes)

    }
}
