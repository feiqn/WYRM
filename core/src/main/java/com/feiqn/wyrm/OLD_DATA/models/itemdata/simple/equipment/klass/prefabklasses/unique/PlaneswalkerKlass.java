package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridMovementType;

public class PlaneswalkerKlass extends SimpleKlass {

    // Unique for Leif

    private boolean mounted;

    public PlaneswalkerKlass() {
        super();

        mounted = true;
        klassID = KlassID.PLANESWALKER;
        name = "Planeswalker";
        RPGridMovementType = RPGridMovementType.FLYING;
        bonusSpeed = 5;
        bonusHealth = 5; // PROTAGONIST
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
        RPGridMovementType = RPGridMovementType.FLYING;
        bonusSpeed = 5;
    }

    public boolean isMounted() {
        return mounted;
    }

    @Override
    public RPGridMovementType movementType() {
        return mounted ? RPGridMovementType.FLYING : RPGridMovementType.INFANTRY;
    }
}
