package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.armor;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.WyrEquipment;

public class WyrArmor extends WyrEquipment {

    public WyrArmor() {
        super(EquipmentType.ARMOR);
    }

    @Override
    public String getName() {
        return "Cloth Shirt";
    }

    @Override
    public String getDescription() {
        return "Worn, faded, darned, and patched. Well-loved, without any doubt.";
    }

}
