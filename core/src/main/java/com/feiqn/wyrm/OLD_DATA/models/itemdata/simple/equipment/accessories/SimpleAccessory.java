package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.accessories;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.accessories.AccessoryType;

public class SimpleAccessory extends SimpleEquipment {

    protected AccessoryType type;
    protected AccessoryCatalogue catalog;

    public SimpleAccessory() {
        super();
    }

}
