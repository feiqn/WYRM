package com.feiqn.wyrm.wyrefactor.helpers.interfaces;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

import java.util.HashMap;

/** Commence operation: Bird On a Wyr!
 */
public interface WyrFrame {

    interface GameKit {

        enum Implementation {
            TABLETOP,
            TCG,
            RPG,
            SHOOTER,
            VN
        }

        interface TableTop {
            enum Genre {
                BOARD,
                CARD,
            }
        }

        interface TCG {
            interface Cards {
                enum Standard {
                    // 'playing cards'
                }
                enum WyrEmblem {

                }
            }
        }

        interface RPG {
            interface Equipment {
                enum Slot {
                    WEAPON,
                    ARMOR,
                    RING,
                    BRACELET,
                    AMULET,
                }

                enum BonusEffect {
                    // either perpetually on self
                    // or applied on hit to target

                    /**
                     * Applied on hit:
                     */
                    LIFE_STEAL_HALF, // rounded down, net over gross
                    LIFE_STEAL_FULL,
                    SLOW, // half movement and speed in combat

                    PIERCE_DEFENSE_HALF, // rounded down
                    PIERCE_DEFENSE_FULL,
                    PIERCE_RESISTANCE_HALF,
                    PIERCE_RESISTANCE_FULL,

                    CRITICAL_DAMAGE_UP,
                    CRITICAL_DAMAGE_UP_UP,
                    CRITICAL_DAMAGE_UP_UP_UP,
                    CRITICAL_DAMAGE_MULTIPLY,
                    CRITICAL_DAMAGE_EXPONENTIAL,
                    CRITICAL_CHANGE_MULTIPLY,
                    CRITICAL_CHANCE_EXPONENTIAL,
                    CRITICAL_CHANCE_UP,
                    CRITICAL_CHANCE_UP_UP,
                    CRITICAL_CHANCE_UP_UP_UP,

                    /**
                     * Perpetually on self:
                     */
                    WATER_WALKING, // shallow and deep
                    FIREPROOF, // fire immune. shh.
                    DEFT_CLIMBING, // traverse low walls
                    SPIRIT_SHIELD, // quite a gambit
                    FLIGHT, // override mobility type

                    CRITICAL_IMMUNE,

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

                enum EquipmentRank {
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
                    WYRMWOOD_SWORD,

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

                    // --SHIPS--



                    // ----HERBAL----

                    // --POTIONS--

                    // --FLORALS--



                    // ----MAGIC----

                    // --ANIMA--

                    // --LIGHT--

                    // --DARK--



                    // ----PROP WEAPONS----

                    TREBUCHET,
                    HEAVY_BALLISTA,
                    CANNON,
                }
            }

            interface Materials {

                enum Type {
                    METAL,
                    WOOD,
                    TEXTILE,
                    STONE,
                    PLOTONIUM,
                }

                enum Metals {
                    BRONZE,
                    IRON,
                    STEEL,
                    BLACK_STEEL,
                    MITHRIL,
                    ADAMANT,
                    RUNIC,
                }

                enum Woods {
                    OAK,
                    WILLOW,
                    MAPLE,
                    YEW,
                    WYRMWOOD,
                }

                enum Textiles {
                    WOOL,
                    FLAX,
                    STRAW,
                }

                enum Stones {
                    COBBLESTONE,
                    GRANITE,
                    LIMESTONE,
                    BASALT,
                }

            }

            enum InteractionType {
                EXAMINE,

                SPAWN_UNIT,
                SPAWN_PROP,
                SPAWN_BULLET,

                DESPAWN_UNIT,
                DESPAWN_PROP,
                DESPAWN_BULLET,

                CAMERA_TO_ACTOR,
                CAMERA_TO_TILE,

                UNIT_DEATH,

                TALK,
                ATTACK,
                MOVE_BY,
                MOVE_TO,
                MOVE_ALONG_PATH,
                WAIT,

                MOVE_TALK,
                MOVE_ATTACK,
                MOVE_WAIT,
                MOVE_AIM,

                ABILITY_USE,

                PROP_ESCAPE, // objectives as props rather than tile types
                PROP_SEIZE,

                PROP_USE, // generic catch-all

                PROP_PILOT, // like a vehicle, etc.
                PROP_AIM, // like a ballista
                PROP_FIRE,

                PROP_OPEN, // i.e., a door
                PROP_CLOSE,
                PROP_LOCK,
                PROP_UNLOCK,

                PROP_LOOT, // a chest, a corpse

                PROP_DESTROY, // break a wall or object

                CUTSCENE_QUEUE,
                CUTSCENE_END,
            }

            enum SubGenre {
                DIVINE, // sinfully original
                IRON, // wyr emblem
                LEGENDARY, // densetsu no ramen
                FANTASTICAL // oh, the finality!
            }

            enum MoveControlMode {
                FREE_MOVE,
                TURN_BASED,
            }

            enum PropType {
                DOOR,
                CHEST,
                TORCH,
                BREAKABLE_WALL,
                BALLISTA,
                FLAMETHROWER,
                TREE,

                OBJECTIVE_SEIZE,
                OBJECTIVE_ESCAPE,
                OBJECTIVE_PROTECT,
            }

            enum StatType {
                AP_RESTORE_RATE,

                HEALTH,
                MANA,
                STAMINA,

                SPEED,

                STRENGTH,
                DEFENSE,

                MAGIC,
                RESISTANCE,

                DEXTERITY,
                CHARISMA,
                LUCK,
                WISDOM,
                FAITH,
                LEADERSHIP,
                CONSTITUTION,
                INTELLIGENCE,
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

            enum StatusCondition {

                /**
                 * Buffs
                 */
                HASTED,
                SHIELDED,
                SPIRIT_SHIELDED,

                /**
                 * Debuffs
                 */
                BURNED,
                AFLAME, // actually on fire.

                POISONED,
                STUNNED,
                COLD,
                WET,
                SLOWED,
                AFRAID,
                IMMOBILE,
                COMPELLED,
                SOUL_BRANDED,
                PETRIFIED,
            }

            enum MobilityType {
                INFANTRY,
                FLYING,
                CAVALRY,
                WHEELS,
                SAILING, // If you're ever feeling spicy, add an underwater type and tile, like the overhead one.
                INANIMATE,
            }

            enum AbilityID {
                DIVE_BOMB,
                CHAIN_LIGHTNING,
                QUAKE, // slowed enemies become crippled
                OBSTRUCT,
                SHOVE,
                FIRELIGHTER,
                WARRANT,
                JUSTIFY,
                SPIRIT_SHIELD, // convert def to res
            }

            enum AerialTileType { // "Weather"?
                CLEAR_SKY,
                STORM_CLOUDS,
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
            }

            enum RPGClassID {
                PEASANT,         // default / basic commoner
                DRAFTEE,         // alt basic soldier

                PLANESWALKER,    // unique for LEIF
                SHIELD_KNIGHT,   // unique for ANTAL
                WRAITH,          // unique class for LEON
                KING,            // unique for ERIK
                HIGH_ARBITER,    // unique for Richard (Leon's father)
                QUEEN,           // unique for Leif's mother.
                FLAME_ETERNAL,   // unique for southern thearch Islwyn/Isfador
                CAPTAIN,         // unique for ANVIL
                HERBALIST,       // unique for LYRA
                BOSS,            // unique for TOHNI

                SOLDIER,         // generic
                BLADE_KNIGHT,    // generic
                CAVALRY,         // generic
                BOATMAN,         // generic

                GREAT_WYRM,      // God.

                PROP,            // Boxes and doors and cannons, oh my!.
                BULLET,
            }

        }

        interface VN {}
    }

    /**
     * FlagID, CutsceneID, CharacterID, and StageID are pre-populated with values specific to the main WYRM campaign. </br>
     * Whether you are modding WYRM or using WyrFrame for an unrelated project, feel free to add to the existing list or rename existing values; however, deleting values is not recommended as existing switch calls will throw errors. If you really want to clear the errors, go ahead and empty the lists.</br>
     * Always implement switch checks with Default case breaks, and unused values will not affect implementation.
     */
    interface Campaign {
        HashMap<FlagID, Boolean> flags = new HashMap<>();

        enum FlagID {
            /*
             * All campaign event flags tracked here.
             * Verbiage should always reflect "true" being the non-default state.
             */

            UNDO_CUTSCENE_PLAYED,

            /**
             * Recruitable units'
             * Death and Recruitment tracking
             */

            // TODO: programmatically assign constructed strings to
            //  hashMap derived from UnitRoster

            LEIF_DIED,

            ANTAL_DIED,
            ANTAL_RECRUITED,

            LYRA_DIED,
            LYRA_RECRUITED,

            ONE_DIED,
            ONE_RECRUITED,

            TOHNI_DIED,
            TOHNI_RECRUITED,

            D_DIED,
            D_RECRUITED,

            ANVIL_DIED,
            ANVIL_RECRUITED,

            /**
             * Avatar flags.
             */
            AVATAR_CAN_SEE_STATS,
            AVATAR_CAN_SEE_DEXTERITY,
            AVATAR_CAN_SEE_MAGIC,
            AVATAR_CAN_SEE_LUCK,
            AVATAR_CAN_SEE_WISDOM,
            AVATAR_CAN_SEE_CONSTITUTION,
            AVATAR_CAN_SEE_GUILE,
            AVATAR_CAN_SEE_AGILITY,
            AVATAR_CAN_SEE_NAMES,

            /**
             * Flags for Stage 1A
             * Escaping the Border City
             */
            STAGE_1A_CLEARED,
            STAGE_1A_ANTAL_ESCAPED,
            STAGE_1A_LEIF_ESCAPED_WEST,
            STAGE_1A_LEIF_ESCAPED_EAST,


            /**
             * Stage 2A
             * Sneaking into the Walled City
             */
            STAGE_2A_UNLOCKED,
            STAGE_2A_CLEARED,
            STAGE_2A_SNEAK_IN_SUCCESSFULLY,


            /**
             * Stage 2B
             * Leif fled alone to the Capital City
             */
            STAGE_2B_UNLOCKED,
            STAGE_2B_CLEARED,


            /**
             * Stage 3A
             * Snuck into the Walled City successfully,
             * and got recruited by Tohni.
             */
            STAGE_3A_UNLOCKED,
            STAGE_3A_CLEARED,


            /**
             * Stage 3B
             * Got caught sneaking into the Walled City,
             * and got recruited by Anvil.
             */
            STAGE_3B_UNLOCKED,
            STAGE_3B_CLEARED,
        }

        enum StageID {
            STAGE_DEBUG,

            STAGE_1A, // The eastern border city is attacked, Leif flees and chooses to rescue Antal.
            STAGE_2A, // Leif and Antal try to sneak into the eastern walled city without being noticed.
            STAGE_3A, // After sneaking past the guards, Leif and Antal are approached by Tohni, a local
            //   mob boss who recruits the pair to perform grunt work in exchange for food and shelter.
            //   The group must help Tohni escape from an illegal gambling operation in which the guards
            //   have set up a sting.
//    STAGE_4A,
//    STAGE_5A,
//    STAGE_6A,

            STAGE_2B, // Leif did not save Antal in 1A, and instead fled alone south along the coast.
            //   TODO: are they waylaid or do they cut straight to the capital? I think they rush
            //   to the capital and then travel back north with the soldiers for some reason but idk why yet
//    STAGE_3B,
//    STAGE_4B,
//    STAGE_5B,
//    STAGE_6B,

            STAGE_3C, // Leif and Antal got caught trying to sneak into the walled city in 2A, and get recruited
            //   by Anvil, the captain of the local guard, to perform in a sting operation against Tohni

            STAGE_CUTSCENE_1A_POST_LEIF_FOUND_ANTAL,

        }

        default void triggerFlag(FlagID flag) {
            flags.put(flag, true);
        }

        default Boolean checkFlag(FlagID flag) {
            return flags.getOrDefault(flag, false);
        }
    }

    interface Character {
        enum Name {
            // Player
            Leif,
            Antal,
            Lyra,
            One,
            Tohni,
            D,
            Anvil,

            // Enemy
            Leon,
            Richard,
            Maria,
            Eric,
            Brea,

            // "Generic"
            Liam,
            Danial,
            Gordon,
            Fran,
            Kaylie,
            Noah,
        }

        enum Expression {
            NEUTRAL,

//            HAPPY,
//            DETERMINED,
//            HOPEFUL,
//            EXCITED,
//            CONTEMPLATIVE,
//            CURIOUS,

//            SURPRISED,
//            EMBARRASSED,
//            PANICKED,
//            WORRIED,
//            WOUNDED,
//            BADLY_WOUNDED,
//            WINCING,
//            MANIACAL,
//            SLY,
//            DESPAIRING,
//            ANNOYED,
//            EXHAUSTED,
//            STOIC,
//            SAD,
//            FROWNING,
//            ANGRY,
//            SHOUTING,
//            STERN,
//            FURIOUS,
//            DISTANT,
//            DYING,
//            SOLEMN,
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
    }

    interface Cutscene {
        interface Choreography {
            enum ChoreoStage {
                WORLD,
                DIALOG,
            }

            enum DialogChoreoType {
                SCREEN_TRANSITION,
                SCREEN_FADE_IN,
                SCREEN_FADE_OUT,

                PORTRAIT_SLIDE_TO,
                PORTRAIT_BUMP_INTO,
                PORTRAIT_HOP,
                PORTRAIT_SHAKE,
                PORTRAIT_RUMBLE,
                PORTRAIT_STANDARDIZE,
                PORTRAIT_FLIP,

                WINCON_REVEAL,
                WINCON_SATISFY,

                PAUSE_SHORT,
                PAUSE_LONG,

                CUTSCENE_END,
            }
        }

        enum ID {

            CSID_0_DEBUG,

            CSID_0_UNDO,

            // STORY A
            // 0A
            // TODO: gather supplies, poke antal, enter, leave


            // 1A
            CSID_1A_ANTAL_ESCAPING_ALIVE,
            CSID_1A_ANTAL_HELP_ME,
            CSID_1A_BALLISTA_1,
            CSID_1A_BALLISTA_2,
            CSID_1A_BALLISTA_LOOP,
            CSID_1A_BALLISTA_DEATH,
            CSID_1A_LEIF_FLEEING_ALONE,
            CSID_1A_LEIF_GETTING_IN_THE_BALLISTA,
            CSID_1A_LEIF_FIRED_BALLISTA,
            CSID_1A_LEIF_INEFFECTIVE_ATTACK,
            CSID_1A_LEIF_LEAVE_ME_ALONE,
            CSID_1A_LEIF_NEED_TO_ESCAPE,
            CSID_1A_LEIF_SAVED_ANTAL,

            CSID_1A_POST_LEIF_ANTAL_CAMPFIRE,
            CSID_1A_POST_LEIF_FOUND_ANTAL,
            CSID_1A_POST_LEIF_SHOULD_FIND_ANTAL,
            CSID_1A_POST_LEIF_ESCAPED_ALONE,


            // 2A
            CSID_2A_PRE_LEIFANTAL_GATESARECLOSED,

            CSID_2A_LEIFANTAL_STEALTHINTOCITY,

            CSID_2A_POST_ANVIL_CAUGHTYOUSNEAKINGIN,
            CSID_2A_POST_TOHNI_SAWYOUSNEAKPASTGUARDS,


            // 3A









            // ===============================
            // STORY BA
            // 1BA


            // STORY BB
            // 1BB







            // ===============================
            // STORY CA
            // 1CA


            // STORY CB
            // 1CB

        }

        enum HorizontalPosition {
            FAR_LEFT,
            LEFT,
            LEFT_OF_CENTER,
            CENTER,
            RIGHT_OF_CENTER,
            RIGHT,
            FAR_RIGHT
        }

        enum Background {
            NONE,
            REMOVE,

            BLACK,

            EXTERIOR_FOREST_DAY,
            EXTERIOR_FOREST_NIGHT,

            EXTERIOR_BEACH_DAY,
            EXTERIOR_BEACH_NIGHT,

            EXTERIOR_STREETS_DIRT_DAY,
            EXTERIOR_STREETS_DIRT_NIGHT,

            EXTERIOR_STREETS_STONE_DAY,
            EXTERIOR_STREETS_STONE_NIGHT,

            EXTERIOR_CAMP_WOODS_DAY,
            EXTERIOR_CAMP_WOODS_NIGHT,

            INTERIOR_STONE_TORCHLIGHT,
            INTERIOR_STONE_DAY,
            INTERIOR_STONE_NIGHT,

            INTERIOR_WOOD_FIRELIGHT,
            INTERIOR_WOOD_DAY,
            INTERIOR_WOOD_NIGHT,
        }

        enum Foreground {
            NONE,
            BLACK,
        }

        enum TriggerType {
            AREA,
            TURN,
            DEATH,
            COMBAT_START,
            COMBAT_END,
            OTHER_CUTSCENE,
            CAMPAIGN_FLAG,
        }

        enum LoopCondition {
            MULTIPLICATIVE_THRESHOLD,
            BROKEN_THRESHOLD
        }

    }

    interface Utilities {
        enum Compass {
            N,
            NW,
            NNW,
            NE,
            NNE,

            S,
            SW,
            SSW,
            SE,
            SSE,

            W,
            WSW,
            WNW,

            E,
            ESE,
            ENE
        }

        enum Speed {
            INSTANT,
            SUPER_FAST,
            FAST,
            NORMAL,
            SLOW,
            SUPER_SLOW,
            STOPPED
        }

        enum NaturalElement {
            FIRE,
            WATER,
            AIR,
            EARTH,
            LIGHT,
            DARK,
        }

        enum Superiority {
            SUPERIOR,
            STANDARD,
            INFERIOR
        }

        enum AttackEfficacy {
            STANDARD,
            ADVANTAGE,
            DISADVANTAGE,
            NO_EFFECT,
        }

        interface WorldPerspective {

            interface TopDown {

                enum Directionality {
                    GRIDLOCKED,
                    OMNI,
                }

            }

            interface SideScrolling {

            }

        }
    }



    /** This is meant to represent a set of
     * "primitive" entity types for anything in
     * the game world. <br>
     * If you think of something else that you
     * feel should expand this list, feel free to
     * grow it.<br>
     * Implement default switch cases for scalability.
     */
    enum ActorType {
        ENTITY, // "Living", "animate" things that "live" within the world. Humanoids, cards, gems, music notes, etc.
        PROP,   // Objects in the world like chests, doors...
        ITEM,   // Something that lives in your inventory or in a menu.
        BULLET, // Any vfx, spells, projectiles, etc.
        UI,     // Menu construction objects like labels, etc.
    }

    enum AnimationState {
        FACING_NORTH,
        FACING_SOUTH,
        FACING_EAST,
        FACING_WEST,
//        FACING_, // use in conjunction with Compass. I.E., FACING_N
//        ATTACKING, // use in conjunction with specific world variables
        IDLE,
        FLOURISH
    }

    enum TeamAlignment {
        PLAYER,
        ALLY,
        ENEMY,
        STRANGER,
        TEAM_5,
        TEAM_6,
        TEAM_7,
        TEAM_8,
        TEAM_9,
        TEAM_X
    }

    enum InputMode {
        STANDARD,
        ACTOR_FOCUSED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
        AIMING,
    }

    /** Use in conjunction with other state enums, or simply ignore and create your own shader state list.
     */
    enum ShaderState {
        DIM,
        HIGHLIGHT,
        STANDARD
    }

    MetaHandler handlers = WYRMGame.metaHandler();

    // world
    float WORLD_SCALE = 1/16f; // TODO: not sure if this should be here

    // camera
    float X_TOLERANCE = 1;
    float Y_TOLERANCE = 2f;
    float FOLLOW_SPEED = .35F;
    float TARGET_SPEED = .65f;

    // actors
    float MOVE_SPEED   = .195f;

    // ui
    float FONT_SCALE = .8F;

//    default State<WorldPerspective, GameKit.Implementation> GameState() {
//        return null;
//    }
//
//    default boolean usesKit(GameKit kit) {
//        return false;
//    }

}


