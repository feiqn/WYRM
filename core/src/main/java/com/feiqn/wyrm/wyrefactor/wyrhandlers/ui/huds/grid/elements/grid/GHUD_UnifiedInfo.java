package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.elements.grid;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GHUD_UnifiedInfo extends VerticalGroup {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GHUD_UnifiedInfo(GridMetaHandler metaHandler) {
        super();
        this.h = metaHandler;
        updateAll();
    }

    public void updateAll() {
        this.clearChildren();

        // TODO:
        //  - victory / failure conditions
        //  - tile info
        //  - unit info
        //  - prop info
        //  - etc...

    }


}
