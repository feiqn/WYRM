package com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.DamageType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.Slot.*;

public abstract class WyrEquipment extends Actor implements Examinable {

    protected final Array<Effect> effects = new Array<>();
    protected final Equipment.Slot equipmentType;

    protected HashMap<WyrFrame.GameKit.RPG.StatType, Integer> statBonuses = new HashMap<>();

    // TODO: bonus stats

    public WyrEquipment(Equipment.Slot equipmentType) {
        this.equipmentType = equipmentType;
        setup();
    }

    abstract void setup();

    public Array<Effect> getEffects() { return effects;}
    public Equipment.Slot getEquipmentType() { return equipmentType; }

    public int getStatBonus(WyrFrame.GameKit.RPG.StatType forStat) {
        return statBonuses.getOrDefault(forStat, 0);
    }

    public static class WyrWeapon extends WyrEquipment {

        protected WeaponCategory weaponCategory;
        protected WeaponRank weaponRank;

        public WyrWeapon() {
            super(WEAPON);
        }

        public WyrWeapon(WeaponCategory category, WeaponRank rank) {
            super(WEAPON);
            this.weaponCategory = category;
            this.weaponRank = rank;
            setName(category.toString());
        }

        @Override
        protected void setup() {
            this.weaponCategory = WeaponCategory.PHYS_HANDS_BLUNT;
            weaponRank = WeaponRank.F;
            setName("Blunt force");
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

    public static class WyrArmor extends WyrEquipment {

        public WyrArmor() {
            super(ARMOR);
        }

        @Override
        protected void setup() {
            setName("Cloth Shirt");
        }

        @Override
        public String getExamine() {
            return "Worn, faded, darned, and patched. Well-loved, without any doubt.";
        }

    }

    public static class WyrAmulet extends WyrEquipment {

        public WyrAmulet() {
            super(AMULET);
        }

        @Override
        protected void setup() {
            setName("Dull pendant");
        }

        @Override
        public String getExamine() {
            return  "A dull and simple pendant on an old, smooth chain. \n Long ago, it was believed that carrying such an unassuming thing as this could reveal hidden truths about the world. \n Perhaps it is worn for sentiment more than function.";
        }
    }

    public static class WyrRing extends WyrEquipment {

        public WyrRing() {
            super(Slot.RING);
        }

        @Override
        protected void setup() {
            setName("Dull ring");
        }

        @Override
        public String getExamine() {
            return "A plain piece of common metal, skillfully banded into a smooth and comfortable fit... for some great-ancestor's hand, no doubt.";
        }

    }

    public static class WyrBracelet extends WyrEquipment {

        public WyrBracelet() {
            super(Slot.BRACELET);
        }

        @Override
        protected void setup() {
            setName("Grass braid");
        }

        @Override
        public String getExamine() {
            return "Stories and legends as old as the sky teach and reinforce the importance of always keeping a piece of the Earth close to one's self at all times. \n A thin layer of resin watermarks this age-old style of preserving sweetgrass in a rugged but comfortable braid.";
        }

    }

}
