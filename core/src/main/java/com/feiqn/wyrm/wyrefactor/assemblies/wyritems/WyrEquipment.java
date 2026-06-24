package com.feiqn.wyrm.wyrefactor.assemblies.wyritems;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.DamageType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.Superiority;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.Slot.*;

public abstract class WyrEquipment extends Actor implements Examinable {

    protected final Array<EquipmentEffect> effects = new Array<>();
    protected final Slot equipmentType;
    protected EquipmentRank equipmentRank;
    private HashMap<StatType, Integer> statBonuses = new HashMap<>();

    public WyrEquipment(Slot equipmentType) {
        this(equipmentType, EquipmentRank.F);
    }

    public WyrEquipment(Slot equipmentType, EquipmentRank equipmentRank){
        this.equipmentType = equipmentType;
        this.equipmentRank = equipmentRank;
        setup();
    }

    abstract void setup();

    protected void addEffect(EquipmentEffect effect) { effects.add(effect); }
    protected void setBonus(StatType stat, int bonus) { statBonuses.put(stat, bonus); }
    public Array<EquipmentEffect> getEffects() { return effects;}
    public Slot getEquipmentType() { return equipmentType; }
    public int getStatBonus(StatType forStat) {
        return statBonuses.getOrDefault(forStat, 0);
    }

    public static class EquipmentEffect {
        protected final BonusEffect bonusEffect;
        protected int areaOfEffectRange = 0;
        protected Superiority superiority = Superiority.STANDARD;
        protected boolean isPerpetual = true;
        protected int effectDuration = 0;

        public EquipmentEffect(BonusEffect effectType) {
            this.bonusEffect = effectType;
        }

        public EquipmentEffect aoe(int range) {
            this.areaOfEffectRange = range;
            return this;
        }

        public EquipmentEffect duration(int effectDuration) {
            this.effectDuration = effectDuration;
            isPerpetual = false;
            return this;
        }

        public BonusEffect getEffectType() { return bonusEffect; }
        public int getAreaOfEffectRange() { return areaOfEffectRange; }
    }

    public static class WyrWeapon extends WyrEquipment {

        protected WeaponCategory weaponCategory;
        protected int reach = 1;

        public WyrWeapon() {
            super(WEAPON);
        }

        public WyrWeapon(WeaponCategory category, EquipmentRank rank) {
            super(WEAPON);
            this.weaponCategory = category;
            this.equipmentRank = rank;
            setName(category.toString());
        }

        @Override
        protected void setup() {
            this.weaponCategory = WeaponCategory.PHYS_HANDS_BLUNT;
            equipmentRank = EquipmentRank.F;
            setName("Blunt force");
        }

        @Override
        public String getExamine() {
            return "Through whatever means available, they're going to use their mass against you.";
        }

        public WeaponCategory getWeaponCategory() { return weaponCategory; }
        public EquipmentRank getWeaponRank() { return equipmentRank; }

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
            super(RING);
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
            super(BRACELET);
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
