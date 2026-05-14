package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class RunicSword extends SimpleWeapon {

    public RunicSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.C;
        catalogueID = WyRPG.WeaponCatalogue.RUNIC_SWORD;
        name = "Runic Sword";
        bonusStrength = 7;
    }

}
