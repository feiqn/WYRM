package com.feiqn.wyrm.models.itemdata.simple.equipment.armor;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;

public class SimpleArmor extends SimpleEquipment {

    protected ArmorType type;
    protected ArmorCatalog catalog;

    public SimpleArmor(WYRMGame game) {
        super(game);
        type = ArmorType.CLOTH;
        catalog = ArmorCatalog.NONE;
        name = "Plain Clothes";
    }

}
