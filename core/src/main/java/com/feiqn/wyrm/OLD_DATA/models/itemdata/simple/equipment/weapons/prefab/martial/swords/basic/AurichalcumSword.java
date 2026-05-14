package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class AurichalcumSword extends SimpleWeapon {

    public AurichalcumSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.C;
        catalogueID = WyRPG.WeaponCatalogue.AURICHALCUM_SWORD;
        name = "Aurichalcum Sword";
        bonusStrength = 8;
    }

}
