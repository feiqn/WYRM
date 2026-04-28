package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponRank;

public class MasterworkSword extends SimpleWeapon {

    public MasterworkSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.A;
        catalogueID = WeaponCatalogue.MASTERWORK_SWORD;
        name = "Masterwork Sword";
        bonusStrength = 10;
    }

}
