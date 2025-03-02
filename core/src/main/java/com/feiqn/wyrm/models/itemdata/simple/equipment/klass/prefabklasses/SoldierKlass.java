package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;

public class SoldierKlass extends SimpleKlass {

    public SoldierKlass() {
        super();

        klassID = KlassID.SOLDIER;
        name = "Soldier";
        bonusDefense = 1;
        bonusStrength = 1;
        bonusSpeed = 1;
        bonusHealth = 1;
    }
}
