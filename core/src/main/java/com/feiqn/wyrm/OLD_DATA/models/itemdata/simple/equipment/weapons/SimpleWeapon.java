package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons.WeaponCatalogue;

public class SimpleWeapon extends SimpleEquipment {

    public enum DamageType {
        PHYSICAL,
        MAGIC,
        HERBAL,
        EXPLOSIVE,
    }

    protected WeaponCategory type;
    protected WeaponRank rank;
    protected WeaponCatalogue catalogueID;
    protected int range;
    protected DamageType damageType;
    protected Abilities ability;

    public SimpleWeapon() {
        super();
        type = WeaponCategory.HANDS;
        rank = WeaponRank.F;
        catalogueID = WeaponCatalogue.HANDS;
        name = "Fists";
        range = 1;
        damageType = DamageType.PHYSICAL;
    }

    public int getRange() {
        return range;
    }
    public WeaponCatalogue getCatalogueID() {
        return catalogueID;
    }
    public WeaponRank getRank() {
        return rank;
    }
    public WeaponCategory getType() {
        return type;
    }
    public DamageType getDamageType() { return damageType; }
    public Abilities getAbility() {
        if(ability != null) {
            return ability;
        }
        return null;
    }

}
