package com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;

public class ShieldKnightClass extends IronKlass {
    public ShieldKnightClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SHIELD_KNIGHT;

        movementType = MovementType.INFANTRY;

        name = "Shield Knight";

        growthRateBonuses.put(StatType.SPEED, 0.01f);
        growthRateBonuses.put(StatType.STRENGTH, 0.25f);
        growthRateBonuses.put(StatType.DEFENSE, 0.8f);
        growthRateBonuses.put(StatType.DEXTERITY, 0.05f);
        growthRateBonuses.put(StatType.HEALTH, 0.25f);
    }
}
