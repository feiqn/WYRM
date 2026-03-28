package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.EquipmentEffect;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponRank;

public class BurnSword extends SimpleWeapon {

    public BurnSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.D;
        catalogueID = WeaponCatalogue.BURN_SWORD;
        name = "FlameTongue";
        bonusStrength = 3;
        effect = EquipmentEffect.BURN;
    }

}
