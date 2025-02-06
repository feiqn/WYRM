package com.feiqn.wyrm.models.itemdata.iron.weapondata.weapons.martial.swords;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.iron.iron_Item;
import com.feiqn.wyrm.models.itemdata.iron.iron_ItemType;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;

public class IronSword extends iron_Item {

    public IronSword(WYRMGame game) {
        super(iron_ItemType.Weapon);

        blankItem = false;

        weaponCategory = WeaponCategory.SWORD;
        catalogueID = WeaponCatalogue.IRON_SWORD;

        name = "Iron Sword";

        strengthBonus = 2;
        defenseBonus = 1;
        dexterityBonus = 0;
        healthBonus = 0;
        weight = 5;
    }

}
