package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;

public class BronzeSword extends SimpleWeapon {

    public BronzeSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.F;
        catalogueID = WeaponCatalogue.BRONZE_SWORD;
        name = "Bronze Sword";
        bonusStrength = 1;
    }

}
