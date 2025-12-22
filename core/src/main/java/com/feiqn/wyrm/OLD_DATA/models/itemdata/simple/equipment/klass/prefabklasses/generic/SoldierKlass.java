package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.generic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;

public class SoldierKlass extends SimpleKlass {

    public SoldierKlass() {
        super();

        klassID = KlassID.SOLDIER;
        name = "Soldier";
        bonusDefense = 1;
        bonusStrength = 2;
        bonusSpeed = 1;
        bonusHealth = 1;
    }
}
