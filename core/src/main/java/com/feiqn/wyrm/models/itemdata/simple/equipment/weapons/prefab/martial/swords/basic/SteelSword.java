package com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;

public class SteelSword extends SimpleWeapon {

    public SteelSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.E;
        catalogue = WeaponCatalogue.STEEL_SWORD;
        name = "Steel Sword";
        bonusStrength = 3;
    }

}
