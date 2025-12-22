package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.bracelets;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.AccessoryCatalog;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.AccessoryType;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleBracelet extends SimpleAccessory {

    public SimpleBracelet() {
        super();
        type = AccessoryType.BRACELET;
        catalog = AccessoryCatalog.DULL_BRACELET;
        name = "Dull Bracelet";
    }

}
