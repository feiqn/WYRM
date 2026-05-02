package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponRank;

public class IronSword extends SimpleWeapon {

    public IronSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.F;
        catalogueID = WeaponCatalogue.IRON_SWORD;
        name = "Iron Sword";
        bonusStrength = 2;
    }

}
