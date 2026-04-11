package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.damage.DamageType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.WyrEquipment;

public class WyrWeapon extends WyrEquipment {

    protected final WeaponCategory weaponCategory;
    protected WeaponRank weaponRank;

    public WyrWeapon() {
        super(EquipmentType.WEAPON);
        weaponCategory = WeaponCategory.PHYS_HANDS_BLUNT;
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
    public String getExamine() {
        return "Through whatever means available, they're going to use their mass against you.";
    }

    public WeaponCategory getWeaponCategory() { return weaponCategory; }
    public WeaponRank getWeaponRank() { return  weaponRank; }

    public DamageType getDamageType(boolean discrete) {
        switch (weaponCategory) {
            case MAGE_DARK:
            case MAGE_ANIMA:
            case MAGE_LIGHT:
                return DamageType.MAGIC;

            case HERBAL_FLORAL:
            case HERBAL_POTION:
                return DamageType.HERBAL;

            case PHYS_AXE_SLASH:
            case PHYS_SWORD_SLASH:
                if(discrete) return DamageType.SLASHING;

            case PHYS_BOW_STAB:
            case PHYS_LANCE_STAB:
                if(discrete) return DamageType.PIERCING;

            case PHYS_HANDS_BLUNT:
            case PHYS_SHIELD_BLUNT:
                if(discrete) return DamageType.BLUDGEONING;

            default:
                return DamageType.PHYSICAL;
        }
    }

}
