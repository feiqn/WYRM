package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.unique;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GU_Leif extends GridUnit {

    private final GridMetaHandler h;

    public GU_Leif(GridMetaHandler metaHandler) {
        super(WYRMGame.root(), UnitRoster.LEIF, metaHandler.assets().leifUnmountedTexture);
        this.h = metaHandler;
        setName("Leif");
        setDescription("A displaced youth with a knack for animal husbandry.");

    }

    @Override
    protected void kill() {

    }

}
