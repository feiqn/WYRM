package com.feiqn.wyrm.models.itemdata.simple.equipment.weapons;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.SimpleEquipment;

public class SimpleWeapon extends SimpleEquipment {

    protected WeaponType type;
    protected WeaponRank rank;
    protected WeaponCatalogue catalogue;
    protected int range;

    public SimpleWeapon() {
        super();
        type = WeaponType.HANDS;
        rank = WeaponRank.F;
        catalogue = WeaponCatalogue.HANDS;
        name = "Fists";
        range = 1;
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
    public WeaponType getType() {
        return type;
    }
}
