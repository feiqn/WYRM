package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.rings;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryType;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories.SimpleAccessory;

public class SimpleRing extends SimpleAccessory {

    public SimpleRing() {
        super();

        type = AccessoryType.RING;
        catalog = AccessoryCatalogue.DULL_RING;
        name = "Dull Ring";
    }

}
