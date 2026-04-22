package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponRank;

public class BronzeSword extends SimpleWeapon {

    public BronzeSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.F;
        catalogueID = WeaponCatalogue.BRONZE_SWORD;
        name = "Bronze Sword";
        bonusStrength = 1;
    }

}
