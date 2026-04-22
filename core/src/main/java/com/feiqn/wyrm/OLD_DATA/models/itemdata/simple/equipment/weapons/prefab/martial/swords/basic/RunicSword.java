package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponRank;

public class RunicSword extends SimpleWeapon {

    public RunicSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.C;
        catalogueID = WeaponCatalogue.RUNIC_SWORD;
        name = "Runic Sword";
        bonusStrength = 7;
    }

}
