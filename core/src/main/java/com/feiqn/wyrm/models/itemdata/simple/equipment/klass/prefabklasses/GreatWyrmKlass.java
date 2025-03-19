package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class GreatWyrmKlass extends SimpleKlass {

    // Unique for The Great Wyrm.

    public GreatWyrmKlass() {
        super();

        klassID = KlassID.GREAT_WYRM;
        name = "The Great Wyrm";
        movementType = MovementType.FLYING;

        bonusDefense    = 10;
        bonusHealth     = 10;
        bonusSpeed      = 10;
        bonusStrength   = 10;
        bonusMagic      = 10;
        bonusResistance = 10;
    }
}
