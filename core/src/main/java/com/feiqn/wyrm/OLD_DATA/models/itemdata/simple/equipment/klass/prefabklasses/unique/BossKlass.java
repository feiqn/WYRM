package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;

public class BossKlass extends SimpleKlass {

    // Starting class for Tohni, the Wall Mob Leader.

    public BossKlass() {
        super();

        klassID = KlassID.BOSS;
        name = "The Boss";
        RPGridMovementType = RPGridMovementType.INFANTRY;
        bonusSpeed = 2;
        bonusHealth = 4;
        bonusDefense = 1;

    }
}
