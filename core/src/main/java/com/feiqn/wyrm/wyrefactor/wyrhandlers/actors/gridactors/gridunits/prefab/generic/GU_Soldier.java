package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.generic;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.SimpleStats.RPGClass.RPGClassID.PLANESWALKER;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.SimpleStats.RPGClass.RPGClassID.SOLDIER;

public class GU_Soldier extends GridUnit {

    public GU_Soldier(GridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.SOLDIER, metaHandler.assets().soldierTexture);
        setName("Soldier");
        setDescription("There must be a person behind that helmet, but it sure doesn't seem like it...");

        this.stats.setBaseSpeed(2);
        this.stats.setBaseDefense(2);
        this.stats.setBaseHealth(4);
        this.stats.setBaseMagic(1);
        this.stats.setBaseResistance(1);
        this.stats.setBaseStrength(2);

        this.stats.getRPGClass().setTo(SOLDIER);
    }
}
