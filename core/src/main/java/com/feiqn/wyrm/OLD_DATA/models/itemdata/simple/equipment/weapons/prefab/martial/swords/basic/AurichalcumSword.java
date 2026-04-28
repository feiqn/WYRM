package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponRank;

public class AurichalcumSword extends SimpleWeapon {

    public AurichalcumSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.C;
        catalogueID = WeaponCatalogue.AURICHALCUM_SWORD;
        name = "Aurichalcum Sword";
        bonusStrength = 8;
    }

}
