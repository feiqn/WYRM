package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.prefab.unique;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats.RPGClass.RPGClassID.PLANESWALKER;

public final class GU_Leif extends GridUnit {

    public GU_Leif(GridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.LEIF, metaHandler.assets().leifUnmountedTexture);

        this.stats.setBaseStrength(1);
        this.stats.setBaseDefense(2);
        this.stats.setBaseMagic(2);
        this.stats.setBaseResistance(2);
        this.stats.setBaseSpeed(4);
        this.stats.setBaseHealth(5);

        this.stats.getRPGClass().setTo(PLANESWALKER);

        /* STARTING STATS FOR CLASS: PLANESWALKER
         * STAT | BASE | CLASS (Std/Mnt) | TOTAL
         * _____________________________________
         * STR  | 1    | 0 / 0           | 1
         * DEF  | 2    | 0 / 1           | 2, 3m
         * MAG  | 2    | 0 / 0           | 2
         * RES  | 2    | 0 / 1           | 2, 3m
         * SPD  | 4    | 2 / 4           | 6, 10m
         * HP   | 5    | 3 / 5           | 8, 10m
         * AP_G | 1    | 0 / 0           | 1
         */
    }

    @Override
    public String getName() {
        return "Leif";
    }

    @Override
    public String getDescription() {
        return "A displaced youth with a knack for animal husbandry.";
    }
}
