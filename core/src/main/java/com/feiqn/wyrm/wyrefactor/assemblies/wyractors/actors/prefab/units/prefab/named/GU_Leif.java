package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.prefab.units.prefab.named;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.RPGClassID.PLANESWALKER;

public final class GU_Leif extends WyrActor.Unit {

    public GU_Leif() {
        super(Character.Name.Leif, handlers.assets().leifUnmountedTexture);

        stats.setBaseStrength(1);
        stats.setBaseDefense(2);
        stats.setBaseMagic(2);
        stats.setBaseResistance(2);
        stats.setBaseSpeed(3);
        stats.setBaseHealth(5, true);

        stats.getRPGClass().setTo(PLANESWALKER);

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
