package com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid.RPGridStats.RPGStatType;

public class ShieldKnightClass extends IronKlass {
    public ShieldKnightClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SHIELD_KNIGHT;

        RPGridMovementType = RPGridMovementType.INFANTRY;

        name = "Shield Knight";

        growthRateBonuses.put(RPGStatType.SPEED, 0.01f);
        growthRateBonuses.put(RPGStatType.STRENGTH, 0.25f);
        growthRateBonuses.put(RPGStatType.DEFENSE, 0.8f);
        growthRateBonuses.put(RPGStatType.DEXTERITY, 0.05f);
//        growthRateBonuses.put(RPGStatType.HEALTH, 0.25f);
    }
}
