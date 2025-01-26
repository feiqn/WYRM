package com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.classdata.IronKlass;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClassList;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

public class SoldierClass extends IronKlass {

    public SoldierClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SOLDIER;

        name = "Soldier";

        growthRateBonuses.put(StatTypes.SPEED, 0f);
        growthRateBonuses.put(StatTypes.STRENGTH, 0.2f);
        growthRateBonuses.put(StatTypes.DEFENSE, 0.2f);
        growthRateBonuses.put(StatTypes.DEXTERITY, 0.02f);
        growthRateBonuses.put(StatTypes.HEALTH, 0.2f);

    }

}
