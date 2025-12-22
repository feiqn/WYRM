package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.prefab.martial.swords.basic;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCatalogue;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;

public class MithrilSword extends SimpleWeapon {

    public MithrilSword() {
        super();
        type = WeaponCategory.SWORD;
        rank = WeaponRank.D;
        catalogue = WeaponCatalogue.MITHRIL_SWORD;
        name = "Mithril Sword";
        bonusStrength = 5;
    }

}
