package com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories;

import com.feiqn.wyrm.wyrefactor.actors.items.equipment.WyrEquipment;

public class WyrRing extends WyrEquipment {

    public WyrRing() {
        super(EquipmentType.RING);
    }

    @Override
    public String getName() {
        return "Dull ring";
    }

    @Override
    public String getExamine() {
        return "A plain piece of common metal, skillfully banded into a smooth and comfortable fit... for some great-ancestor's hand, no doubt.";
    }

}
