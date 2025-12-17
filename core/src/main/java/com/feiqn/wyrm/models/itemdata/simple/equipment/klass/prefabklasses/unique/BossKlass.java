package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;

public class BossKlass extends SimpleKlass {

    // Starting class for Tohni, the Wall Mob Leader.

    public BossKlass() {
        super();

        klassID = KlassID.BOSS;
        name = "The Boss";
        movementType = MovementType.INFANTRY;
        bonusSpeed = 2;
        bonusHealth = 4;
        bonusDefense = 1;

    }
}
