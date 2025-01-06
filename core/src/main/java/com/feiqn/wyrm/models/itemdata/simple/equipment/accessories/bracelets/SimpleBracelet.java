package com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.bracelets;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryCatalog;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleBracelet extends SimpleAccessory {

    public SimpleBracelet(WYRMGame game) {
        super(game);
        type = AccessoryType.BRACELET;
        catalog = AccessoryCatalog.DULL_BRACELET;
        name = "Dull Bracelet";
    }

}
