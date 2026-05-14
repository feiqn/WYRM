package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.amulets;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class SimpleAmulet extends SimpleAccessory {

    public SimpleAmulet() {
        super();
//        type = AccessoryType.AMULET;
        name = "Dull Amulet";
        catalog = WyRPG.AccessoryCatalogue.DULL_AMULET;
    }

}
