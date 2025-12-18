package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.unique;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GU_Leif extends GridUnit {

    public GU_Leif(GridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.LEIF, metaHandler.assets().leifUnmountedTexture);
        setName("Leif");
        setDescription("A displaced youth with a knack for animal husbandry.");
    }


}
