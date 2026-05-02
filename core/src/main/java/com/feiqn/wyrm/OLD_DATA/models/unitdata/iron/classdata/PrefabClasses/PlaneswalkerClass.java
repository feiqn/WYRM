package com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.RPGStatType;

public class PlaneswalkerClass extends IronKlass {
    public PlaneswalkerClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.PLANESWALKER;

        RPGridMovementType = RPGridMovementType.FLYING;

        name = "Planeswalker";

        growthRateBonuses.put(RPGStatType.SPEED, 0.15f);
        growthRateBonuses.put(RPGStatType.STRENGTH, 0.01f);
        growthRateBonuses.put(RPGStatType.DEFENSE, 0.02f);
        growthRateBonuses.put(RPGStatType.DEXTERITY, 0.05f);
//        growthRateBonuses.put(RPGStatType.HEALTH, 0.05f);

    }
}
