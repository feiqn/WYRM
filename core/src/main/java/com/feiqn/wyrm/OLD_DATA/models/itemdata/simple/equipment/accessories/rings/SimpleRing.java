package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.rings;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class SimpleRing extends SimpleAccessory {

    public SimpleRing() {
        super();

//        type = AccessoryType.RING;
        catalog = WyRPG.AccessoryCatalogue.DULL_RING;
        name = "Dull Ring";
    }

}
