package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class PlaneswalkerKlass extends SimpleKlass {

    // Unique for Leif

    private boolean mounted;

    public PlaneswalkerKlass() {
        super();

        mounted = true;
        klassID = KlassID.PLANESWALKER;
        name = "Planeswalker";
        RPGridMovementType = WyRPG.MovementType.FLYING;
        bonusSpeed = 5;
        bonusHealth = 5; // PROTAGONIST
    }

    public void dismount() {
        //drawable change
        mounted = false;
        RPGridMovementType = WyRPG.MovementType.INFANTRY;
        bonusSpeed = 1;
    }

    public void mount() {
        //drawable change
        mounted = true;
        RPGridMovementType = WyRPG.MovementType.FLYING;
        bonusSpeed = 5;
    }

    public boolean isMounted() {
        return mounted;
    }

    @Override
    public WyRPG.MovementType movementType() {
        return mounted ? WyRPG.MovementType.FLYING : WyRPG.MovementType.INFANTRY;
    }
}
