package com.feiqn.wyrm.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;

public class SoldierClass extends IronKlass {

    public SoldierClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SOLDIER;

        name = "Soldier";

        growthRateBonuses.put(StatType.SPEED, 0f);
        growthRateBonuses.put(StatType.STRENGTH, 0.2f);
        growthRateBonuses.put(StatType.DEFENSE, 0.2f);
        growthRateBonuses.put(StatType.DEXTERITY, 0.02f);
        growthRateBonuses.put(StatType.HEALTH, 0.2f);

    }

}
