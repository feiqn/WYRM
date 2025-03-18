package com.feiqn.wyrm.models.itemdata.simple.equipment.klass;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class SimpleKlass extends SimpleEquipment {

    public enum KlassID {
        PEASANT,         // alt default
        DRAFTEE,         // default class if unassigned

        PLANESWALKER,    // unique for LEIF
        SHIELD_KNIGHT,   // starting class for ANTAL
        WRAITH,          // unique class for LEON
        KING,            // unique for ERIK and [LEON's FATHER]
        QUEEN,           // unique for [SOUTHERN QUEEN]
        CAPTAIN,         // starting class for ANVIL
        HERBALIST,       // starting class for LYRA

        SOLDIER,         // generic
        BLADE_KNIGHT,    // generic
        CAVALRY,         // generic
        BOATMAN,         // generic


        GREAT_WYRM       // God.
    }

    protected MovementType movementType;

    protected KlassID klassID;

    public SimpleKlass() {
        super();

        klassID = KlassID.PEASANT;
        name = "Peasant";
        movementType = MovementType.INFANTRY;
    }

    public MovementType movementType() {
        return movementType;
    }

    public KlassID klassID() {
        return klassID;
    }
}
