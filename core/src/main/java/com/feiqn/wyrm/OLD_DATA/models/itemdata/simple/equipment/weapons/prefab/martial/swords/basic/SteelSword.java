package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class SteelSword extends SimpleWeapon {

    public SteelSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.E;
        catalogueID = WyRPG.WeaponCatalogue.STEEL_SWORD;
        name = "Steel Sword";
        bonusStrength = 3;
    }

}
