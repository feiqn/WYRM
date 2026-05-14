package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.WeaponCategory;

public class SimpleWeapon extends SimpleEquipment {

    protected WeaponCategory type;
    protected WyRPG.WeaponRank rank;
    protected WyRPG.WeaponCatalogue catalogueID;
    protected int range;
    protected WyRPG.DamageType damageType;
    protected WyRPG.AbilityID ability;

    public SimpleWeapon() {
        super();
        type = WeaponCategory.PHYS_HANDS_BLUNT;
        rank = WyRPG.WeaponRank.F;
        catalogueID = WyRPG.WeaponCatalogue.HANDS;
        name = "Fists";
        range = 1;
        damageType = WyRPG.DamageType.PHYSICAL;
    }

    public int getRange() {
        return range;
    }
    public WyRPG.WeaponCatalogue getCatalogueID() {
        return catalogueID;
    }
    public WyRPG.WeaponRank getRank() {
        return rank;
    }
    public WeaponCategory getType() {
        return type;
    }
    public WyRPG.DamageType getDamageType() { return damageType; }
    public WyRPG.AbilityID getAbility() {
        if(ability != null) {
            return ability;
        }
        return null;
    }

}
