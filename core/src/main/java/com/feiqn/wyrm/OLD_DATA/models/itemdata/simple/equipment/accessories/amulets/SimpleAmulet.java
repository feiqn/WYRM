package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.amulets;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryType;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleAmulet extends SimpleAccessory {

    public SimpleAmulet() {
        super();
        type = AccessoryType.AMULET;
        name = "Dull Amulet";
        catalog = AccessoryCatalogue.DULL_AMULET;
    }

}
