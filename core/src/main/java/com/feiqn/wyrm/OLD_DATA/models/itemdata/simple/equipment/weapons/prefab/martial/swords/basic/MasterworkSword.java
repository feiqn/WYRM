package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;

public class MasterworkSword extends SimpleWeapon {

    public MasterworkSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.A;
        catalogue = WeaponCatalogue.MASTERWORK_SWORD;
        name = "Masterwork Sword";
        bonusStrength = 10;
    }

}
