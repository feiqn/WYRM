package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.klass;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;

public class SimpleKlass extends SimpleEquipment {

    public enum KlassID {
        PEASANT,         // alt default
        DRAFTEE,         // default class if unassigned

        PLANESWALKER,    // unique for LEIF
        SHIELD_KNIGHT,   // unique for ANTAL
        WRAITH,          // unique class for LEON
        KING,            // unique for ERIK and [LEON's FATHER]
        QUEEN,           // unique for [SOUTHERN QUEEN]
        CAPTAIN,         // unique for ANVIL
        HERBALIST,       // unique for LYRA
        BOSS,            // unique for TOHNI

        SOLDIER,         // generic
        BLADE_KNIGHT,    // generic
        CAVALRY,         // generic
        BOATMAN,         // generic


        GREAT_WYRM       // God.
    }

    protected RPGridMovementType RPGridMovementType;

    protected KlassID klassID;

    public SimpleKlass() {
        super();

        klassID = KlassID.PEASANT;
        name = "Peasant";
        RPGridMovementType = RPGridMovementType.INFANTRY;
    }

    public RPGridMovementType movementType() {
        return RPGridMovementType;
    }

    public KlassID klassID() {
        return klassID;
    }
}
