package com.feiqn.wyrm.models.itemdata.weapondata.weapons.martial.swords;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.Weapon;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;

public class IronSword extends Weapon {

    public IronSword(WYRMGame game) {
        super(game);

        weaponType = WeaponType.SWORD;

        name = "Iron Sword";

        strengthBonus = 2;
        defenseBonus = 1;
        skillBonus = 0;
        healthBonus = 0;
        weight = 0.5f;
    }

}
