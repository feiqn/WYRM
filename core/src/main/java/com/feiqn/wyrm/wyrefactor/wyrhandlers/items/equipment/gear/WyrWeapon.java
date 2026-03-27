package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.WyrEquipment;

public class WyrWeapon extends WyrEquipment {

    protected final WeaponCategory weaponCategory;
    protected WeaponRank weaponRank;

    public WyrWeapon() {
        super(EquipmentType.WEAPON);
        weaponCategory = WeaponCategory.HANDS;
        weaponRank = WeaponRank.F;
    }

    protected WyrWeapon(WeaponCategory category, WeaponRank rank) {
        super(EquipmentType.WEAPON);
        weaponCategory = category;
        weaponRank = rank;
    }

    @Override
    public String getName() {
        return "Blunt force";
    }

    @Override
    public String getDescription() {
        return "Through whatever means available, they're going to use their mass against you.";
    }

    public WeaponCategory getWeaponCategory() { return weaponCategory; }
    public WeaponRank getWeaponRank() { return  weaponRank; }

}
