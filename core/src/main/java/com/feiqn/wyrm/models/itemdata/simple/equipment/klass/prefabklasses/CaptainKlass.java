package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class CaptainKlass extends SimpleKlass {

    // Starting class for Anvil, Captain of the Guard.
    // Possibly not unique -- unsure.

    public CaptainKlass() {

        klassID = KlassID.CAPTAIN;
        name = "Captain of the Guard";
        movementType = MovementType.CAVALRY;

        bonusStrength   = 4;
        bonusDefense    = 3;
        bonusResistance = 2;
        bonusSpeed      = 4;
        bonusHealth     = 4;

    }

}
