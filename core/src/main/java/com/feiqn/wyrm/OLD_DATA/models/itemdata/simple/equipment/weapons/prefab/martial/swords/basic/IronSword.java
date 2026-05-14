package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class IronSword extends SimpleWeapon {

    public IronSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.F;
        catalogueID = WyRPG.WeaponCatalogue.IRON_SWORD;
        name = "Iron Sword";
        bonusStrength = 2;
    }

}
