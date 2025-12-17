package com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique;

import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.SimpleKlass;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;

public class PlaneswalkerKlass extends SimpleKlass {

    // Unique for Leif

    private boolean mounted;

    public PlaneswalkerKlass() {
        super();

        mounted = true;
        klassID = KlassID.PLANESWALKER;
        name = "Planeswalker";
        movementType = MovementType.FLYING;
        bonusSpeed = 5;
        bonusHealth = 5; // PROTAGONIST
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
        movementType = MovementType.FLYING;
        bonusSpeed = 5;
    }

    public boolean isMounted() {
        return mounted;
    }

    @Override
    public MovementType movementType() {
        return mounted ? MovementType.FLYING : MovementType.INFANTRY;
    }
}
