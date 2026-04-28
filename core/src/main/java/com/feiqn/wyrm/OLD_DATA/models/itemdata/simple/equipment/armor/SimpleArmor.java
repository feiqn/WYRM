package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.armor;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.armor.ArmorCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.armor.ArmorCatalogue;

public class SimpleArmor extends SimpleEquipment {

    protected ArmorCategory type;
    protected ArmorCatalogue catalog;

    public SimpleArmor() {
        super();
        type = ArmorCategory.CLOTH;
        catalog = ArmorCatalogue.CLOTH_SHIRT;
        name = "Plain Clothes";
    }

}
