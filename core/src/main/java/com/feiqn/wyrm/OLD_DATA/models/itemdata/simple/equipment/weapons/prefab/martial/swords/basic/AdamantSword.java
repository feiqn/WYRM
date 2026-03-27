package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;

public class AdamantSword extends SimpleWeapon {

    public AdamantSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.D;
        catalogueID = WeaponCatalogue.ADAMANT_SWORD;
        name = "Adamant Sword";
        bonusStrength = 6;
    }

}
