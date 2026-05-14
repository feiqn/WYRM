package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class MasterworkSword extends SimpleWeapon {

    public MasterworkSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.A;
        catalogueID = WyRPG.WeaponCatalogue.MASTERWORK_SWORD;
        name = "Masterwork Sword";
        bonusStrength = 10;
    }

}
