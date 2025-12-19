package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.unique;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.SimpleStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.SimpleStats.RPGClass.RPGClassID.PLANESWALKER;

public final class GU_Leif extends GridUnit {

    public GU_Leif(GridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.LEIF, metaHandler.assets().leifUnmountedTexture);
        setName("Leif");
        setDescription("A displaced youth with a knack for animal husbandry.");

        this.stats.setBaseSpeed(4);
        this.stats.setBaseDefense(3);
        this.stats.setBaseHealth(5);
        this.stats.setBaseMagic(2);
        this.stats.setBaseResistance(2);
        this.stats.setBaseStrength(2);

        this.stats.getRPGClass().setTo(PLANESWALKER);


    }


}
