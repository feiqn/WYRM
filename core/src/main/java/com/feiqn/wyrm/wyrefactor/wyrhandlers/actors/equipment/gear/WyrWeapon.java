package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.gear;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment.WyrEquipment;

public abstract class WyrWeapon extends WyrEquipment {

    protected final WeaponCategory weaponCategory;
    protected WeaponRank weaponRank;

    public WyrWeapon(WeaponCategory category) {
        type = EquipmentType.WEAPON;
        this.weaponCategory = category;
    }

    public WeaponCategory getWeaponCategory() { return weaponCategory; }

}
