package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabKlasses;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class PlaneswalkerKlass extends SimpleKlass {

    boolean mounted;

    public PlaneswalkerKlass() {
        super();

        mounted = true;
        klassID = KlassID.PLANESWALKER;
        name = "Planeswalker";
        movementType = MovementType.FLYING;
        bonusSpeed = 3;
        bonusHealth = 5;
    }

    public void dismount() {
        mounted = false;
        movementType = MovementType.INFANTRY;
        bonusSpeed = 1;
    }

    public void mount() {
        mounted = true;
        movementType = MovementType.FLYING;
        bonusSpeed = 3;
    }
}
