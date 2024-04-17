package com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClass;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClassList;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

public class ShieldKnightClass extends UnitClass {
    public ShieldKnightClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.SHIELD_KNIGHT;

        movementType = MovementType.INFANTRY;

        name = "Shield Knight";

        growthRateBonuses.put(StatTypes.SPEED, 0.01f);
        growthRateBonuses.put(StatTypes.STRENGTH, 0.25f);
        growthRateBonuses.put(StatTypes.DEFENSE, 0.8f);
        growthRateBonuses.put(StatTypes.DEXTERITY, 0.05f);
        growthRateBonuses.put(StatTypes.HEALTH, 0.25f);
    }
}
