package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;

public class WraithKlass extends SimpleKlass {

    // Unique class for Leon

    public WraithKlass() {

        klassID = KlassID.WRAITH;
        name = "Wraith";
        movementType = MovementType.INFANTRY;
        bonusStrength   = 7;
        bonusDefense    = 9;
        bonusHealth     = 9;
        bonusSpeed      = 9;
        bonusMagic      = 10;
        bonusResistance = 10;

    }

}
