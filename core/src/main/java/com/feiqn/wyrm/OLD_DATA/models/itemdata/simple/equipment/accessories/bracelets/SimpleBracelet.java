package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.bracelets;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class SimpleBracelet extends SimpleAccessory {

    public SimpleBracelet() {
        super();
//        type = AccessoryType.BRACELET;
        catalog = WyRPG.AccessoryCatalogue.DULL_BRACELET;
        name = "Dull Bracelet";
    }

}
