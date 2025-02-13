package com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;

public class IronSword extends SimpleWeapon {

    public IronSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.F;
        catalogue = WeaponCatalogue.IRON_SWORD;
        name = "Iron Sword";
        bonusStrength = 2;
    }

}
