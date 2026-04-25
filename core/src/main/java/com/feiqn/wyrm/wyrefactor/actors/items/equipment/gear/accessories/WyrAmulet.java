package com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories;

import com.feiqn.wyrm.wyrefactor.actors.items.equipment.WyrEquipment;

public class WyrAmulet extends WyrEquipment {

    public WyrAmulet() {
        super(EquipmentType.AMULET);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Simple pendant";
    }

    @Override
    public String getExamine() {
        return  "A dull and simple pendant on an old, smooth chain. Perhaps it is worn for sentiment more than function.";
    }
}
