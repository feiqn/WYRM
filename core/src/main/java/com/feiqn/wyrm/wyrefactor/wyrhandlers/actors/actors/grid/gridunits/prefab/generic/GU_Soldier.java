package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.prefab.generic;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats.RPGClass.RPGClassID.SOLDIER;

public class GU_Soldier extends GridUnit {

    public GU_Soldier(GridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.SOLDIER, metaHandler.assets().soldierTexture);
        setName("Soldier");
        setDescription("There must be a person behind that helmet, but it sure doesn't seem like it...");

        this.stats.setBaseStrength(2);
        this.stats.setBaseDefense(2);
        this.stats.setBaseMagic(1);
        this.stats.setBaseResistance(1);
        this.stats.setBaseSpeed(2);
        this.stats.setBaseHealth(4);

        this.stats.getRPGClass().setTo(SOLDIER);


        /* STARTING STATS FOR CLASS: SOLDIER
         * STAT | BASE | CLASS (Std/Mnt) | TOTAL
         * _____________________________________
         * STR  | 2    | 1               | 3
         * DEF  | 2    | 1               | 3
         * MAG  | 1    | 0               | 1
         * RES  | 1    | 0               | 1
         * SPD  | 2    | 0               | 2
         * HP   | 4    | 1               | 5
         * AP_G | 1    | 0               | 1
         */

    }
}
