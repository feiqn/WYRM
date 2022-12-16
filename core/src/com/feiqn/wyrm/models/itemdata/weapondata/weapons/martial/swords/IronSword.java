package com.feiqn.wyrm.models.itemdata.weapondata.weapons.martial.swords;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.Item;
import com.feiqn.wyrm.models.itemdata.ItemType;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;

public class IronSword extends Item {

    public IronSword(WYRMGame game) {
        super(game, ItemType.Weapon);

        weaponType = WeaponType.SWORD;
        catalogueID = WeaponCatalogue.IRON_SWORD;

        name = "Iron Sword";

        strengthBonus = 2;
        defenseBonus = 1;
        skillBonus = 0;
        healthBonus = 0;
        weight = 0.5f;
    }

}
