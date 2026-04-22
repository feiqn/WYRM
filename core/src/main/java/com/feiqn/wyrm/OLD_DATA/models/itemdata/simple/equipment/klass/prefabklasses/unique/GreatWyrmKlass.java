package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;

public class GreatWyrmKlass extends SimpleKlass {

    // Unique for The Great Wyrm.

    public GreatWyrmKlass() {
        super();

        klassID = KlassID.GREAT_WYRM;
        name = "The Great Wyrm";
        RPGridMovementType = RPGridMovementType.FLYING;

        bonusDefense    = 10;
        bonusHealth     = 10;
        bonusSpeed      = 10;
        bonusStrength   = 10;
        bonusMagic      = 10;
        bonusResistance = 10;
    }
}
