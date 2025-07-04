package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class CaptainKlass extends SimpleKlass {

    // Starting class for Anvil, Captain of the Guard.

    private boolean mounted;

    public CaptainKlass() {

        klassID = KlassID.CAPTAIN;
        name = "Captain of the Guard";
        movementType = MovementType.CAVALRY;
        mounted = true;

        bonusStrength   = 4;
        bonusDefense    = 3;
        bonusResistance = 2;
        bonusSpeed      = 4;
        bonusHealth     = 4;

    }

    public void dismount() {
        //drawable change
        mounted = false;
        movementType = MovementType.INFANTRY;
        bonusSpeed = 1;
    }

    public void mount() {
        //drawable change
        mounted = true;
        movementType = MovementType.CAVALRY;
        bonusSpeed = 3;
    }

    public boolean isMounted() {
        return mounted;
    }

    @Override
    public MovementType movementType() {
        return mounted ? MovementType.CAVALRY : MovementType.INFANTRY;
    }
}
