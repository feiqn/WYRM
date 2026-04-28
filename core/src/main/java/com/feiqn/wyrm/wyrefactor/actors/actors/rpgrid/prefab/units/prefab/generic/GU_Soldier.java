package com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.generic;

import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridStats.RPGClass.RPGClassID.SOLDIER;

public class GU_Soldier extends RPGridUnit {

    public GU_Soldier(RPGridMetaHandler metaHandler) {
        super(metaHandler, UnitIDRoster.GENERIC_SOLDIER, metaHandler.assets().soldierTexture);

        this.stats.setBaseStrength(3);
        this.stats.setBaseDefense(2);
        this.stats.setBaseMagic(1);
        this.stats.setBaseResistance(1);
        this.stats.setBaseSpeed(3);
        this.stats.setBaseHealth(4);

        this.stats.getRPGClass().setTo(SOLDIER);


        /* STARTING STATS FOR CLASS: SOLDIER
         * STAT | BASE | CLASS (Std/Mnt) | TOTAL
         * _____________________________________
         * STR  | 3    | 1               | 4
         * DEF  | 2    | 1               | 3
         * MAG  | 1    | 0               | 1
         * RES  | 1    | 0               | 1
         * SPD  | 3    | 0               | 3
         * HP   | 4    | 1               | 5
         * AP_G | 1    | 0               | 1
         */

    }

    @Override
    public String getName() {
        return "Soldier";
    }

    @Override
    public String getExamine() {
        return "There must be a person behind that helmet, but it sure doesn't seem like it...";
    }


}
