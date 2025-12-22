package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;

public class AurichalcumSword extends SimpleWeapon {

    public AurichalcumSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.C;
        catalogue = WeaponCatalogue.AURICHALCUM_SWORD;
        name = "Aurichalcum Sword";
        bonusStrength = 8;
    }

}
