package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.EquipmentType.*;

public abstract class RPGEquipment extends WyrEquipment {

    public RPGEquipment(WyRPG.EquipmentType type) {
        super(type);
    }

    @Override
    public WyRPG.EquipmentType getEquipmentType() {
        assert equipmentType instanceof WyRPG.EquipmentType;
        return (WyRPG.EquipmentType) equipmentType;
    }

    public static class RPGWeapon extends RPGEquipment {

        protected final WyRPG.WeaponCategory weaponCategory;
        protected final WyRPG.WeaponRank weaponRank;

        public RPGWeapon() {
            super(WEAPON);
            weaponCategory = WyRPG.WeaponCategory.PHYS_HANDS_BLUNT;
            weaponRank = WyRPG.WeaponRank.F;
        }

        public RPGWeapon(WyRPG.WeaponCategory category, WyRPG.WeaponRank rank) {
            super(WEAPON);
            this.weaponCategory = category;
            this.weaponRank = rank;
        }

        @Override
        protected void setup() {}

        @Override
        public String getName() {
            return "Blunt force";
        }

        @Override
        public String getExamine() {
            return "Through whatever means available, they're going to use their mass against you.";
        }

        public WyRPG.WeaponCategory getWeaponCategory() { return weaponCategory; }
        public WyRPG.WeaponRank getWeaponRank() { return  weaponRank; }

        public WyRPG.DamageType getDamageType(boolean discrete) {
            switch (weaponCategory) {
                case MAGE_DARK:
                case MAGE_ANIMA:
                case MAGE_LIGHT:
                    return WyRPG.DamageType.MAGIC;

                case HERBAL_FLORAL:
                case HERBAL_POTION:
                    return WyRPG.DamageType.HERBAL;

                case PHYS_AXE_SLASH:
                case PHYS_SWORD_SLASH:
                    if(discrete) return WyRPG.DamageType.SLASHING;

                case PHYS_BOW_STAB:
                case PHYS_LANCE_STAB:
                    if(discrete) return WyRPG.DamageType.PIERCING;

                case PHYS_HANDS_BLUNT:
                case PHYS_SHIELD_BLUNT:
                    if(discrete) return WyRPG.DamageType.BLUDGEONING;

                default:
                    return WyRPG.DamageType.PHYSICAL;
            }
        }

    }

    public static class RPGArmor extends RPGEquipment {

        public RPGArmor() {
            super(ARMOR);
        }

        @Override
        protected void setup() {

        }

        @Override
        public String getName() {
            return "Cloth Shirt";
        }

        @Override
        public String getExamine() {
            return "Worn, faded, darned, and patched. Well-loved, without any doubt.";
        }

    }

    public static class RPGAmulet extends RPGEquipment {

        public RPGAmulet() {
            super(AMULET);
        }

        @Override
        protected void setup() {

        }

        @Override
        public String getName() {
            return "Dull pendant";
        }

        @Override
        public String getExamine() {
            return  "A dull and simple pendant on an old, smooth chain. \n Long ago, it was believed that carrying such an unassuming thing as this could reveal hidden truths about the world. \n Perhaps it is worn for sentiment more than function.";
        }
    }

    public static class RPGRing extends RPGEquipment {

        public RPGRing() {
            super(RING);
        }

        @Override
        protected void setup() {

        }

        @Override
        public String getName() {
            return "Dull ring";
        }

        @Override
        public String getExamine() {
            return "A plain piece of common metal, skillfully banded into a smooth and comfortable fit... for some great-ancestor's hand, no doubt.";
        }

    }

    public static class RPGBracelet extends RPGEquipment {

        public RPGBracelet() {
            super(BRACELET);
        }

        @Override
        protected void setup() {

        }

        @Override
        public String getName() {
            return "Braided Grass";
        }

        @Override
        public String getExamine() {
            return "Stories and legends as old as the sky teach and reinforce the importance of always keeping a piece of the Earth close to one's self at all times. \n A thin layer of resin watermarks this age-old style of preserving sweetgrass in a rugged but comfortable braid.";
        }

    }

}
