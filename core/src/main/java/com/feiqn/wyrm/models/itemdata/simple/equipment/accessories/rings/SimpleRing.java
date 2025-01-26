package com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.rings;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryCatalog;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.AccessoryType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleRing extends SimpleAccessory {

    public SimpleRing() {
        super();

        type = AccessoryType.RING;
        catalog = AccessoryCatalog.DULL_RING;
        name = "Dull Ring";
    }

}
