package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;

public class RunicSword extends SimpleWeapon {

    public RunicSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.C;
        catalogueID = WeaponCatalogue.RUNIC_SWORD;
        name = "Runic Sword";
        bonusStrength = 7;
    }

}
