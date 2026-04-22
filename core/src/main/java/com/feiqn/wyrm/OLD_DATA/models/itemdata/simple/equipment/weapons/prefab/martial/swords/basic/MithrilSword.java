package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCatalogue;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WeaponRank;

public class MithrilSword extends SimpleWeapon {

    public MithrilSword() {
        super();
        type = WeaponCategory.PHYS_SWORD_SLASH;
        rank = WeaponRank.D;
        catalogueID = WeaponCatalogue.MITHRIL_SWORD;
        name = "Mithril Sword";
        bonusStrength = 5;
    }

}
