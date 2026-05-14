package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.named;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.rpgrid.RPGridStats.RPGClass.RPGClassID.PLANESWALKER;

public final class GU_Leif extends RPGridUnit {

    public GU_Leif(RPGridMetaHandler metaHandler) {
        super(metaHandler, WYRM.Character.Leif, metaHandler.assets().leifUnmountedTexture);

        this.stats().setBaseStrength(1);
        this.stats().setBaseDefense(2);
        this.stats().setBaseMagic(2);
        this.stats().setBaseResistance(2);
        this.stats().setBaseSpeed(3);
        this.stats().setBaseHealth(5, true);

        this.stats().getRPGClass().setTo(PLANESWALKER);

        /* STARTING STATS FOR CLASS: PLANESWALKER
         * STAT | BASE | CLASS (Std/Mnt) | TOTAL
         * _____________________________________
         * STR  | 1    | 0 / 0           | 1
         * DEF  | 2    | 0 / 1           | 2, 3m
         * MAG  | 2    | 0 / 0           | 2
         * RES  | 2    | 0 / 1           | 2, 3m
         * SPD  | 3    | 2 / 4           | 5, 9m
         * HP   | 5    | 3 / 5           | 8, 10m
         * AP_G | 1    | 0 / 0           | 1
         */
    }



    @Override
    public String getName() {
        return "Leif";
    }

    @Override
    public String getExamine() {
        return "A displaced youth with a knack for animal husbandry.";
    }
}
