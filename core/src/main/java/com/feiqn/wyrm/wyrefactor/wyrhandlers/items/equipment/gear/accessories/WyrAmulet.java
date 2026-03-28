package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.WyrEquipment;

public class WyrAmulet extends WyrEquipment {

    public WyrAmulet() {
        super(EquipmentType.AMULET);
    }

    @Override
    public String getName() {
        return "Simple pendant";
    }

    @Override
    public String getDescription() {
        return  "A dull and simple pendant on an old, smooth chain. Perhaps it is worn for sentiment more than function.";
    }
}
