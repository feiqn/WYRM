package com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.prefab.martial.swords;

import com.feiqn.wyrm.models.itemdata.simple.equipment.EquipEffect;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;

public class BurnSword extends SimpleWeapon {

    public BurnSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.D;
        catalogue = WeaponCatalogue.BURN_SWORD;
        name = "FlameTongue";
        bonusStrength = 3;
        effect = EquipEffect.BURN;
    }

}
