package com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr;

public interface WyRPG extends Wyr {

    enum StatType {
        // WYRM
        SPEED,
        STRENGTH,
        DEFENSE,

        MAGIC,
        RESISTANCE,

        DEXTERITY, // VERY SPECIAL STAT*

        // EXTENDED RPG
        CHARISMA,
        LUCK,
        WISDOM,
        CONSTITUTION,
        GUILE,
        AGILITY,
    }

    enum DamageType {
        // Indented types are "discrete" values only returned in certain game modes.

        PHYSICAL,
            SLASHING,
            BLUDGEONING,
            PIERCING,

        MAGIC,
            BURNING,
            FREEZING,
            SHOCKING,
            SPIRITUAL,
            VOID,

        HERBAL,
            TOXIC,
            CORROSIVE,
            SENSORY,

        EXPLOSIVE,
            COMBUSTIVE,
            PROPULSIVE,
            BLINDING,
    }

    enum StatusEffect {
        BURN,
        POISON,
        STUN,
        CHILL,
        SOUL_BRAND,
        PETRIFY,
    }

    enum MovementType {
        INFANTRY,
        FLYING,
        CAVALRY,
        WHEELS,
        SAILING, // If you're ever feeling spicy, add an underwater type and tile, like the overhead one.
        INANIMATE,
    }

    enum AnimationState {
        FACING_NORTH,
        FACING_SOUTH,
        FACING_EAST,
        FACING_WEST,
        IDLE,
        FLOURISH,
    }

    enum EquipmentType {
        WEAPON,
        ARMOR,
        RING,
        BRACELET,
        AMULET,
    }

    enum AbilityID {
        DIVE_BOMB,
        SHOVE,
        FIRELIGHTER,
        WARRANT,
    }

    enum AccessoryCatalogue {
        DULL_RING,
        DULL_BRACELET,
        DULL_AMULET,
    }

    enum ArmorCatalogue {
        CLOTH_SHIRT,
    }

    enum ArmorCategory {
        HEAVY,
        MEDIUM,
        LIGHT,
        CLOTH
    }

    enum WeaponRank {
        S, // + 10    w/ effect
        A, // + 10    no effect || 9   w/ effect
        B, // + 9     no effect || 7,8 w/ effect
        C, // + 7,8   no effect || 5,6 w/ effect
        D, // + 5,6   no effect || 3,4 w/ effect
        E, // + 3,4   no effect || 1,2 w/ effect
        F  // + 1,2   no effect || 0   w/ effect
    }

    enum WeaponCategory {

        PHYS_SWORD_SLASH,
        PHYS_AXE_SLASH,

        PHYS_SWORD_STAB,
        PHYS_LANCE_STAB,
        PHYS_BOW_STAB,

        PHYS_SHIELD_BLUNT,
        PHYS_HANDS_BLUNT,

        MAGE_ANIMA,
        MAGE_LIGHT,
        MAGE_DARK,

        HERBAL_POTION,
        HERBAL_FLORAL,

        EXPLOSIVE_FORCE,
        EXPLOSIVE_FLARE,
        EXPLOSIVE_INCENDIARY
    }

    enum WeaponCatalogue {

        // ----MARTIAL----

        HANDS,

        // --SWORDS--

        BRONZE_SWORD,
        IRON_SWORD,
        STEEL_SWORD,
        BLACK_SWORD,
        MITHRIL_SWORD,
        ADAMANT_SWORD,
        RUNIC_SWORD,
        AURICHALCUM_SWORD,
        NECRITE_SWORD,
        MASTERWORK_SWORD,

        BURN_SWORD,

        // --LANCES--

        BRONZE_LANCE,
        IRON_LANCE,
        STEEL_LANCE,
        BLACK_LANCE,
        MITHRIL_LANCE,
        ADAMANT_LANCE,
        RUNIC_LANCE,
        AURICHALCUM_LANCE,
        NECRITE_LANCE,
        MASTERWORK_LANCE,

        // --AXES--

        BRONZE_AXE,
        IRON_AXE,
        STEEL_AXE,
        BLACK_AXE,
        MITHRIL_AXE,
        ADAMANT_AXE,
        RUNIC_AXE,
        AURICHALCUM_AXE,
        NECRITE_AXE,
        MASTERWORK_AXE,

        // --BOWS--

        SLING,
        OAK_BOW,
        WILLOW_BOW,
        PINE_BOW,
        MAPLE_BOW,
        YEW_BOW,
        TEAK_BOW,
        MAHOGANY_BOW,
        REDWOOD_BOW,
        MASTERWORK_BOW,

        LONG_BOW,

        // --SHIELDS--

        BRONZE_GREAT_SHIELD,
        IRON_GREAT_SHIELD,
        STEEL_GREAT_SHIELD,
        BLACK_GREAT_SHIELD,
        MITHRIL_GREAT_SHIELD,
        ADAMANT_GREAT_SHIELD,
        RUNIC_GREAT_SHIELD,
        AURICHALCUM_GREAT_SHIELD,
        NECRITE_GREAT_SHIELD,
        MASTERWORK_GREAT_SHIELD,

        CEREMONIAL_GREAT_SHIELD,

        // ----VEHICLES---

        // --WHEELS--

        TREBUCHET,
        BALLISTA,
        CANNON,

        // --SHIPS--



        // ----HERBAL----

        // --POTIONS--

        // --FLORALS--



        // ----MAGIC----

        // --ANIMA--

        // --LIGHT--

        // --DARK--
    }

    enum PersonalityType {
        DEFENSIVE,
        AGGRESSIVE,
        PROTECTIVE,
        RECKLESS,
        STILL,
        FLANKING,
        PATROLLING,
        LOS_AGGRO,
        LOS_FLEE,
        PLAYER,
        TARGET_LOCATION,
        TARGET_UNIT,
        TARGET_PROP,
        ESCAPE
    }

    enum TileType {
        PLAINS,
        ROAD,
        FOREST,
        MOUNTAIN,
        ROUGH_HILLS,
        FORTRESS,
        DOOR,
        CHEST,
        IMPASSIBLE_WALL,
        LOW_WALL,
        BREAKABLE_WALL,
        SHALLOW_WATER,
        DEEP_WATER,
        CORAL_REEF,
        LAVA,
        OBJECTIVE_SEIZE,
        OBJECTIVE_ESCAPE,
        OBJECTIVE_DESTROY,
    }

    enum RPGMode {
        DIVINE,
        IRON,
        LEGENDARY,
    }

    enum MoveControlMode {
        FREE_MOVE,
        COMBAT,
    }

    @Override
    default WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
