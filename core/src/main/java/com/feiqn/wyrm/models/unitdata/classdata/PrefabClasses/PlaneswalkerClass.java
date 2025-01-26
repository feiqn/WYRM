package com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.classdata.IronKlass;
import com.feiqn.wyrm.models.unitdata.classdata.UnitClassList;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

public class PlaneswalkerClass extends IronKlass {
    public PlaneswalkerClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.PLANESWALKER;

        movementType = MovementType.FLYING;

        name = "Planeswalker";

        growthRateBonuses.put(StatTypes.SPEED, 0.15f);
        growthRateBonuses.put(StatTypes.STRENGTH, 0.01f);
        growthRateBonuses.put(StatTypes.DEFENSE, 0.02f);
        growthRateBonuses.put(StatTypes.DEXTERITY, 0.05f);
        growthRateBonuses.put(StatTypes.HEALTH, 0.05f);

    }
}
