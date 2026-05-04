package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.weapons;

import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.SimpleEquipment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.rpgrid.RPGridAbilityID;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponRank;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.WeaponCatalogue;

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
    protected RPGridAbilityID ability;

    public SimpleWeapon() {
        super();
        type = WeaponCategory.PHYS_HANDS_BLUNT;
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
    public RPGridAbilityID getAbility() {
        if(ability != null) {
            return ability;
        }
        return null;
    }

}
