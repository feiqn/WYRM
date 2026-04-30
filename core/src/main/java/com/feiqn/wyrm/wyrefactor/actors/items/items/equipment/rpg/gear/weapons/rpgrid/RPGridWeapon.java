package com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.rpgrid;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.damage.DamageType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipmentType.WEAPON;

public class RPGridWeapon extends RPGEquipment<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    protected final WeaponCategory weaponCategory;
    protected final WeaponRank weaponRank;

    public RPGridWeapon() {
        super(WEAPON);
        weaponCategory = WeaponCategory.PHYS_HANDS_BLUNT;
        weaponRank = WeaponRank.F;
    }

    public RPGridWeapon(WeaponCategory category, WeaponRank rank) {
        super(WEAPON);
        this.weaponCategory = category;
        this.weaponRank = rank;
    }

    @Override
    protected void setup() {

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
