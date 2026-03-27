package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.WyrWeapon;

public final class Quartermaster {

    // SimpleWeapons...

    private Quartermaster() {}

    public WyrWeapon BronzeSword() {
        return new WyrWeapon(WeaponCategory.SWORD, WeaponRank.E) {

        };
    }

    public WyrWeapon IronSword() {
        return new WyrWeapon(WeaponCategory.SWORD, WeaponRank.D) {

        };
    }

    public WyrWeapon SteelSword() {
        return new WyrWeapon(WeaponCategory.SWORD, WeaponRank.C) {

        };
    }


    public WyrWeapon MithrilSword() {
        return new WyrWeapon(WeaponCategory.SWORD, WeaponRank.B) {

        };
    }

    public WyrWeapon AdamantSword() {
        return new WyrWeapon(WeaponCategory.SWORD, WeaponRank.A) {

        };
    }

    // TODO .......

}
