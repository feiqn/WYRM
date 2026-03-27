package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.WyrEquipment;

public class WyrRing extends WyrEquipment {

    public WyrRing() {
        super(EquipmentType.RING);
    }

    @Override
    public String getName() {
        return "Dull ring";
    }

    @Override
    public String getDescription() {
        return "A plain piece of common metal, skillfully banded into a smooth and comfortable fit... for some great-ancestor's hand, no doubt.";
    }

}
