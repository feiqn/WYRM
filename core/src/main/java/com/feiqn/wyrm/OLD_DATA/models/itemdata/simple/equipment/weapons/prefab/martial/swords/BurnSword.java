package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords;

import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.EquipmentEffect;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponRank;

public class BurnSword extends SimpleWeapon {

    public BurnSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_STAB;
        rank = WeaponRank.D;
        catalogueID = WeaponCatalogue.BURN_SWORD;
        name = "FlameTongue";
        bonusStrength = 3;
        effect = EquipmentEffect.BURN;
    }

}
