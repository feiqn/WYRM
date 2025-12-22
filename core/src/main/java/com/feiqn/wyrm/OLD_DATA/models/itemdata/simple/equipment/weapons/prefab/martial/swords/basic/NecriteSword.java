package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;

public class NecriteSword extends SimpleWeapon {

    public NecriteSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.B;
        catalogue = WeaponCatalogue.NECRITE_SWORD;
        name = "Necrite Sword";
        bonusStrength = 9;
    }

}
