package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class NecriteSword extends SimpleWeapon {

    public NecriteSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.B;
        catalogueID = WyRPG.WeaponCatalogue.NECRITE_SWORD;
        name = "Necrite Sword";
        bonusStrength = 9;
    }

}
