package com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories;

import com.feiqn.wyrm.wyrefactor.actors.items.equipment.WyrEquipment;

public class WyrBracelet extends WyrEquipment {

    public WyrBracelet() {
        super(EquipmentType.BRACELET);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Braided Grass";
    }

    @Override
    public String getExamine() {
        return "Stories and legends as old as the sky teach and reinforce the importance of always keeping a piece of the Earth close to one's self at all times. A thin layer of resin watermarks this age-old style of preserving sweetgrass in a rugged but comfortable braid.";
    }

}
