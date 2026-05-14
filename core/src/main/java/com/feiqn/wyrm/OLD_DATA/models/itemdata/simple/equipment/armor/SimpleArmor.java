package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.armor;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class SimpleArmor extends SimpleEquipment {

    protected WyRPG.ArmorCategory type;
    protected WyRPG.ArmorCatalogue catalog;

    public SimpleArmor() {
        super();
        type = WyRPG.ArmorCategory.CLOTH;
        catalog = WyRPG.ArmorCatalogue.CLOTH_SHIRT;
        name = "Plain Clothes";
    }

}
