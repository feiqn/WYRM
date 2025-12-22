package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.gear.WyrWeapon;

public final class Quartermaster {

    private Quartermaster() {}

    public WyrEquipment BronzeSword() {
        return new WyrWeapon(WeaponCategory.SWORD) {

        };
    }

    public WyrWeapon IronSword() {
        return new WyrWeapon(WeaponCategory.SWORD) {

        };
    }

    public WyrEquipment SteelSword() {
        return new WyrEquipment() {

        };
    }


    public WyrEquipment MithrilSword() {
        return new WyrEquipment() {

        };
    }

    public WyrEquipment AdamantSword() {
        return new WyrEquipment() {

        };
    }

    // TODO .......

}
