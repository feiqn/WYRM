package com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.StatType;

public class SoldierClass extends IronKlass {

    public SoldierClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SOLDIER;

        name = "Soldier";

        growthRateBonuses.put(StatType.SPEED, 0f);
        growthRateBonuses.put(StatType.STRENGTH, 0.2f);
        growthRateBonuses.put(StatType.DEFENSE, 0.2f);
        growthRateBonuses.put(StatType.DEXTERITY, 0.02f);
//        growthRateBonuses.put(RPGStatType.HEALTH, 0.2f);

    }

}
