package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;

public class BlackSword extends SimpleWeapon {

    public BlackSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.E;
        catalogue = WeaponCatalogue.BLACK_SWORD;
        name = "Black Sword";
        bonusStrength = 4;
    }

}
