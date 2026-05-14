package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class MithrilSword extends SimpleWeapon {

    public MithrilSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_SLASH;
        rank = WyRPG.WeaponRank.D;
        catalogueID = WyRPG.WeaponCatalogue.MITHRIL_SWORD;
        name = "Mithril Sword";
        bonusStrength = 5;
    }

}
