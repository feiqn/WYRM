package com.feiqn.wyrm.models.itemdata.simple.equipment.armor;

import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;

public class SimpleArmor extends SimpleEquipment {

    protected ArmorCategory type;
    protected ArmorCatalog catalog;

    public SimpleArmor() {
        super();
        type = ArmorCategory.CLOTH;
        catalog = ArmorCatalog.NONE;
        name = "Plain Clothes";
    }

}
