package com.feiqn.wyrm.models.itemdata.simple.equipment.weapons;

import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;

public class SimpleWeapon extends SimpleEquipment {

    public enum DamageType {
        PHYSICAL,
        MAGIC
    }

    protected WeaponCategory type;
    protected WeaponRank rank;
    protected WeaponCatalogue catalogue;
    protected int range;
    protected DamageType damageType;

    public SimpleWeapon() {
        super();
        type = WeaponCategory.HANDS;
        rank = WeaponRank.F;
        catalogue = WeaponCatalogue.HANDS;
        name = "Fists";
        range = 1;
        damageType = DamageType.PHYSICAL;
    }

    public int getRange() {
        return range;
    }
    public WeaponCatalogue getCatalogue() {
        return catalogue;
    }
    public WeaponRank getRank() {
        return rank;
    }
    public WeaponCategory getType() {
        return type;
    }
    public DamageType getDamageType() { return damageType; }

}
