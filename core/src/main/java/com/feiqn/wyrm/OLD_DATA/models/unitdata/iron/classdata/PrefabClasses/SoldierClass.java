package com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.RPGStatType;

public class SoldierClass extends IronKlass {

    public SoldierClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SOLDIER;

        name = "Soldier";

        growthRateBonuses.put(RPGStatType.SPEED, 0f);
        growthRateBonuses.put(RPGStatType.STRENGTH, 0.2f);
        growthRateBonuses.put(RPGStatType.DEFENSE, 0.2f);
        growthRateBonuses.put(RPGStatType.DEXTERITY, 0.02f);
//        growthRateBonuses.put(RPGStatType.HEALTH, 0.2f);

    }

}
