package com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.amulets;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryCatalog;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleAmulet extends SimpleAccessory {

    public SimpleAmulet() {
        super();
        type = AccessoryType.AMULET;
        name = "Dull Amulet";
        catalog = AccessoryCatalog.DULL_AMULET;
    }

}
