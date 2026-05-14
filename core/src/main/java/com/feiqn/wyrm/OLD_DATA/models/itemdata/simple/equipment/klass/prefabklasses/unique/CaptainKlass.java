package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class CaptainKlass extends SimpleKlass {

    // Starting class for Anvil, Captain of the Guard.

    private boolean mounted;

    public CaptainKlass() {

        klassID = KlassID.CAPTAIN;
        name = "Captain of the Guard";
        RPGridMovementType = RPGridMovementType.CAVALRY;
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
        RPGridMovementType = RPGridMovementType.INFANTRY;
        bonusSpeed = 1;
    }

    public void mount() {
        //drawable change
        mounted = true;
        RPGridMovementType = RPGridMovementType.CAVALRY;
        bonusSpeed = 3;
    }

    public boolean isMounted() {
        return mounted;
    }

    @Override
    public WyRPG.MovementType movementType() {
        return mounted ? WyRPG.MovementType.CAVALRY : WyRPG.MovementType.INFANTRY;
    }
}
