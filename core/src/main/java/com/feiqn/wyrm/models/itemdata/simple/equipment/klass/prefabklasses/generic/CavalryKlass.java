package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.generic;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;

public class CavalryKlass extends SimpleKlass {

    public CavalryKlass() {
        super();

        klassID = KlassID.CAVALRY;
        name = "Cavalier";
        bonusDefense = 1;
        bonusStrength = 1;
        bonusSpeed = 2;
        bonusResistance = 1;
        bonusHealth = 2;
    }
}
