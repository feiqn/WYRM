package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class BurnSword extends SimpleWeapon {

    public BurnSword() {
        super();
        type = WyRPG.WeaponCategory.PHYS_SWORD_STAB;
        rank = WyRPG.WeaponRank.D;
        catalogueID = WyRPG.WeaponCatalogue.BURN_SWORD;
        name = "FlameTongue";
        bonusStrength = 3;
        effect = Wyr.EquipmentEffect.BURN;
    }

}
