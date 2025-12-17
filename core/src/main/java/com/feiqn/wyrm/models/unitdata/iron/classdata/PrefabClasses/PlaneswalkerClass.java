package com.feiqn.wyrm.models.unitdata.iron.classdata.PrefabClasses;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.models.unitdata.iron.classdata.IronKlass;
import com.feiqn.wyrm.models.unitdata.iron.classdata.UnitClassList;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.stats.StatType;

public class PlaneswalkerClass extends IronKlass {
    public PlaneswalkerClass(WYRMGame game) {
        super(game);

        classType = UnitClassList.PLANESWALKER;

        movementType = MovementType.FLYING;

        name = "Planeswalker";

        growthRateBonuses.put(StatType.SPEED, 0.15f);
        growthRateBonuses.put(StatType.STRENGTH, 0.01f);
        growthRateBonuses.put(StatType.DEFENSE, 0.02f);
        growthRateBonuses.put(StatType.DEXTERITY, 0.05f);
        growthRateBonuses.put(StatType.HEALTH, 0.05f);

    }
}
