package com.feiqn.wyrm.wyrefactor.helpers.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors.WyrEmblem.Units;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.WeaponCategory;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.WeaponCategory.*;

/** Commence operation: Bird On a Wyr!
 */
public interface WyrFrame {

    interface GameKit {

        enum Implementation {
            TABLETOP,
            TCG,
            RPG,
            FPS,
            SIDESCROLL,
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
                    CLOTH,

                    PROP_ARMOR,
                }

                enum WeaponCategory {
                    HANDS,
                    SWORD,
                    AXE,
                    SPEAR,
                    SHIELD,
                    BOW,
                    CROSSBOW,

                    MAGIC,

                    FLORA,
                    POTION,

                    EXPLOSIVE,

                    PROP_WEAPON,
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

            interface RPGClass {

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

                    WILD_ANIMAL,
                    DOMESTICATED_ANIMAL,

                    OBJECT,            // Boxes and doors and cannons, oh my!.
                }

                static int statBonus(StatType statID, RPGClassID classID) {
                    switch(classID) {

                        case PLANESWALKER:
                            // Protagonist stats for Leif,
                            // aka: plot armor.
                            switch(statID) {
                                case SPEED:
                                case HEALTH: return 5;
                                case DEFENSE:
                                case RESISTANCE: return 2;
                                case MAGIC:
                                case STRENGTH: return 1;
                                default:
                                    return 0;
                            }
                            // TODO:
                            //  in combat, if the difference in mounted hp
                            //  would cause the unit to drop to 1 or lower,
                            //  automatically force dismount and set health to 1(?)

                        case SHIELD_KNIGHT:
                            // Antal, et. all
                            switch(statID) {
                                case HEALTH: return 6;
                                case DEFENSE: return 4;
                                case MAGIC:
                                case STRENGTH: return 1;
                                case RESISTANCE:
                                case SPEED: return 2;
                                default:
                                    return 0;
                            }

                        case SOLDIER:
                            switch(statID) {
                                case HEALTH: return 4;
                                case SPEED:
                                case STRENGTH: return 3;
                                case DEFENSE: return 2;
                                case MAGIC:
                                case RESISTANCE: return 1;
                                default: return 0;
                            }

                        case GREAT_WYRM:
                            return 10;

                        case WRAITH:
                        case KING:
                        case QUEEN:
                        case CAPTAIN:
                        case HERBALIST:
                        case BOSS:
                        case BLADE_KNIGHT:
                        case CAVALRY:
                        case BOATMAN:
                        case OBJECT:
                        case PEASANT:
                        case DRAFTEE:
                        default:
                            return 0;
                    }
                }

                static boolean canWield(WeaponCategory weaponCategory, RPGClassID classID) {
                    return proficientWeapons(classID).contains(weaponCategory, true);
                }
                static Array<WeaponCategory> proficientWeapons(RPGClassID classID) {
                    final Array<WeaponCategory> usableWeapons = new Array<>();

                    switch(classID) {

                        case PLANESWALKER:
                            usableWeapons.add(BOW);
                            break;

                        case SHIELD_KNIGHT:
                            usableWeapons.add(SHIELD);
                            break;

                        case CAVALRY:
                        case SOLDIER:
                            usableWeapons.add(SPEAR);
                            break;

                        default:
                            break;
                    }

                    return usableWeapons;
                }

                static boolean canMount(RPGClassID classID, MountType mountType) {
                    return proficientMounts(classID).contains(mountType, true);
                }
                static Array<MountType> proficientMounts(RPGClassID classID) {
                    final Array<MountType> rideableMounts = new Array<>();
                    switch(classID) {

                        case PLANESWALKER:
                            rideableMounts.add(MountType.PEGASUS);
                            rideableMounts.add(MountType.HORSE);
                            rideableMounts.add(MountType.BOAT);
                            break;

                        case BOATMAN:
                            rideableMounts.add(MountType.BOAT);
                            break;

                        case CAVALRY:
                            rideableMounts.add(MountType.HORSE);
                            break;

                        default:
                            break;
                    }
                    return rideableMounts;
                }

                static MobilityType standardMobilityType(RPGClassID classID) {
                    switch(classID) {

                        case WRAITH:
                            return MobilityType.FLYING;

                        case OBJECT:
                            return MobilityType.INANIMATE;

                        default:
                            return MobilityType.INFANTRY;
                    }
                }

                static String examineText(RPGClassID classID) {
                    switch(classID) {

                        case PLANESWALKER:
                            return "A pedestrian from the flatland.";

                        case SHIELD_KNIGHT:
                            return "It's like talking to a wall.";

                        case SOLDIER:
                            return "There must be a person behind that helmet, but it sure doesn't seem like it.";

                        case OBJECT:
                            return "It's... something!";

                        default:
                            return "Just as easily somebody from somewhere as nobody from nowhere.";
                    }
                }

            }

            enum MountType {
                HORSE,
                PEGASUS,
                ELEPHANT,
                WYVERN,
                SNAKE,
                WOLF,
                VEHICLE,
                BOAT,
            }

            enum InteractionType {
                EXAMINE,

                CALL_MOUNT,
                MOUNT,
                DISMOUNT,

                SPAWN,

                DESPAWN,

                CAMERA_TO_ACTOR,
                CAMERA_TO_TILE,

                UNIT_DEATH,

                TALK,
                ATTACK,
                MOVE_BY, // in tiles
                MOVE_TO, // grid coordinate directly
                FOLLOW_PATH,
                WAIT, // parse priority

                MOVE_TALK, // TODO: these move_ methods to be obsoleted
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
                HEALTH,

                MANA, // *
                STAMINA, // *

                SPEED,

                STRENGTH,
                DEFENSE,

                MAGIC,
                RESISTANCE,

                // *
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
                PHYS_CUT,
                PHYS_STAB,
                PHYS_BLUNT,

//                MAGE_SLASHING, // air-bending slice!
//                MAGE_PIERCING,
//                MAGE_CRUSHING, // impact, force, throw a rock at them earth-bending style.
//
//                MAGE_BURNING,
//                MAGE_FREEZING,
//                MAGE_SHOCKING,
//                MAGE_SPIRITUAL,
//                MAGE_VOID,

                HERBAL_TOXIC, // gets in you
                HERBAL_CORROSIVE, // gets on you
                HERBAL_SENSORY, // smells bad, burns the eyes, makes your mouth taste like pennies

                EXPLOSIVE_COMBUSTIVE, // incendiary
                EXPLOSIVE_PROPULSIVE, // force
                EXPLOSIVE_BLINDING, // flare
            }

            enum StatusConditionID {

                /**
                 * Buffs
                 */
                HASTED,
                SHIELDED,
                SPIRIT_SHIELDED,
                WATER_WALKING, // shallow and deep
                FIREPROOF, // fire immune. shh.
                DEFT_CLIMBING, // traverse low walls
                SPIRIT_SHIELD, // quite a gambit
                FLIGHT, // override mobility type

                CRITICAL_IMMUNE,

                BOUND_MOUNT, // a mount is summonable, separate from Owned Mount

                CRITICAL_DAMAGE_UP,
                CRITICAL_CHANCE_UP,

                RESOLVED, // feeling strong determination

                /**
                 * Debuffs
                 */
                EXERTED,

                BURNED,
                AFLAME, // actually on fire.

                ENVENOMED,
                STUNNED,
                COLD,
                WET,
                SLOWED,
                AFRAID,
                IMMOBILE,
                COMPELLED,
                SOUL_BRANDED,
                PETRIFIED,

                GRIEVING,

                /**
                 * I mean really it depends on how you look at it to be honest.
                 */
                MOUNTED,
                MOUNTING,
                CONTROLLED,
                CONTROLLING,

                /**
                 * Applied on hit:
                 */
                LIFE_STEAL_HALF_ON_HIT,
                LIFE_STEAL_FULL_ON_HIT,
                SLOW_ON_HIT, // half movement and speed in combat
                STUN_ON_HIT,

                PIERCE_DEFENSE_HALF_ON_HIT,
                PIERCE_DEFENSE_FULL_ON_HIT,
                PIERCE_RESISTANCE_HALF_ON_HIT,
                PIERCE_RESISTANCE_FULL_ON_HIT,
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
                LOW_CEILING,
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

        }

    }

    /**
     * FlagID, CutsceneID, CharacterID, and StageID are pre-populated with values specific to the main WYRM campaign. </br>
     * Whether you are modding WYRM or using WyrFrame for an unrelated project, feel free to add to the existing list or rename existing values; however, deleting values is not recommended as existing switch calls will throw errors. If you really want to clear the errors, go ahead and empty the lists.</br>
     * Always implement switch checks with Default case breaks, and unused values will not affect implementation.
     */
    interface Campaign {

        enum WinConPolarity {
            VICTORY,
            OPTIONAL,
            FAILURE,
        }

        enum WinConType {
            ESCAPE, // get to a specific tile and leave the map
            DEATH, // of a unit
            DESTRUCTION, // of a prop
            SEIZE, // claim a tile or prop
            DEFEND, // hold a tile or protect a prop for x turns
            SURVIVAL, // until turn x
            COLLECTION, // acquire a specific item (shop, steal, loot, etc.)
            KILL_COUNT, // defeat x enemies
            REAL_TIME, // real time passing
            CONVERSATION, // talk to a unit with any character
            CONFRONTATION, // talk to a unit with a specific character

        }

        enum FlagID {
            /*
             * Campaign event flags tracked here, EXCEPT:
             * - Unit recruitment / death
             * - Stage unlocked / cleared
             *
             * Verbiage should always reflect "true" being the non-default state.
             */

            UNDO_CUTSCENE_PLAYED,

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

            /**
             * STORY "A"
             */

            STAGE_1A, // The eastern border city is attacked, Leif flees and chooses to rescue Antal.
            STAGE_CUTSCENE_1A_POST_LEIF_FOUND_ANTAL,

            STAGE_2A, // Leif and Antal try to sneak into the eastern walled city without being noticed.

            STAGE_3A, // After sneaking past the guards, Leif and Antal are approached by Tohni, a local
                    //   mob boss who recruits the pair to perform grunt work in exchange for food and shelter.
                    //   The group must help Tohni escape from an illegal gambling operation in which the guards
                    //   have set up a sting.

            STAGE_4A,
            STAGE_5A,
            STAGE_6A,

            /**
             * STORY "B"
             */

            STAGE_2B, // Leif did not save Antal in 1A, and instead fled alone south along the coast.
                      //   TODO: are they waylaid or do they cut straight to the capital? I think they rush
                      //   to the capital and then travel back north with the soldiers for some reason but idk why yet

            STAGE_3B,
            STAGE_4B,
            STAGE_5B,
            STAGE_6B,

            /**
             * STORY "C"
             */

            STAGE_3C, // Leif and Antal got caught trying to sneak into the walled city in 2A, and get recruited
                      //   by Anvil, the captain of the local guard, to perform in a sting operation against Tohni

        }

        Preferences saveData = Gdx.app.getPreferences("internalState");

        static String getBFN() {
            return saveData.contains("bfn") ? saveData.getString("bfn") : "Ashe";
        }

        static void setBFN(String bestFriendsName) {
            saveData.putString("bfn", bestFriendsName);
            saveData.flush();
        }

        static void hitFlag(FlagID flag) {
            saveData.putBoolean(flag.toString(), true);
            saveData.flush();
        }

        static boolean checkFlag(FlagID flagID) {
            return saveData.contains(flagID.toString()) && saveData.getBoolean(flagID.toString());
        }

        static void learnName(Character.Name name) {
            saveData.putBoolean("KNOWS_NAME" + name, true);
            saveData.flush();
        }

        static boolean nameKnown(Character.Name name) {
            return saveData.contains("KNOWS_NAME" + name) && saveData.getBoolean("KNOWS_NAME" + name);
        }

        static void recruitCharacter(Character.Name charID) {
            saveData.putBoolean(charID + "_RECRUITED", true);
            saveData.flush();
        }

        static void killCharacter(Character.Name charID) {
            saveData.putBoolean(charID + "_DIED", true);
            saveData.flush();
        }

        static boolean characterIsAvailable(Character.Name charID) {
            return characterWasRecruited(charID) && characterIsAlive(charID);
        }

        static boolean characterWasRecruited(Character.Name charID) {
            return saveData.contains(charID + "_RECRUITED") && saveData.getBoolean(charID + "_RECRUITED");
        }

        static boolean characterIsAlive(Character.Name charID) {
            return !saveData.contains(charID + "_DIED") || !saveData.getBoolean(charID + "_DIED");
        }

        static void unlockStage(StageID stageID) {
            saveData.putBoolean(stageID + "_UNLOCKED", true);
            saveData.flush();
        }

        static Array<StageID> unlockedStages() {
            final Array<StageID> unlockedStages = new Array<>();
            for(StageID stage : StageID.values()) {
                if(saveData.contains(stage + "_UNLOCKED")) {
                    unlockedStages.add(stage);
                }
            }
            return unlockedStages;
        }

        static void winStage(StageID stageID) {
            saveData.putBoolean(stageID + "_CLEARED", true);
            saveData.flush();
        }

        static Array<StageID> wonStages() {
            final Array<StageID> wonStages = new Array<>();
            for(StageID stage : StageID.values()) {
                if(saveData.contains(stage + "_CLEARED")) {
                    wonStages.add(stage);
                }
            }
            return wonStages;
        }

        static void failStage(StageID stageID) {
            saveData.putBoolean(stageID + "_CLEARED", false);
            saveData.putBoolean(stageID + "_FAILED", true);
            saveData.flush();
        }

        static boolean stageWon(StageID stageID) {
            return saveData.contains(stageID + "_CLEARED");
        }

        static boolean stageFailed(StageID stageID) {
            return saveData.contains(stageID + "_FAILED");
        }

        static Array<StageID> failedStages() {
            final Array<StageID> failedStages = new Array<>();
            for(StageID stage : StageID.values()) {
                if(saveData.contains(stage + "_FAILED")) {
                    failedStages.add(stage);
                }
            }
            return failedStages;
        }

        static WyrActor.Unit nextFodder() {
            // todo:
            //  iterate through all names,
            //  cast each to string,
            //  check if string length exactly == 3 ( && != "One")
            //  then check if alive && in play
            //  switch method in WYRMActors.Units to return object from string

            if(characterIsAlive(Amy) && !handlers.register().characterIsInPlay(Amy)) return Units.amy();

            return null;
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
            // -ally
            Danial,

            // -enemy
            Collin,
            Liam,
            Gordon,
            Fran,
            Kaylie,
            Noah,
            Kyle,
            Ryan,
            Henry,
            Cody,


            // -"infinite spawn" fodder
            Amy,
            Zoe,
            Ben,
            Bob,
            Jim,
            Jon,
            Rav,
            Zim,
            Bil,
            Oby,
            Kal,
            Mac,
            Lan,
            Joe,
            Obe,
            Lee,
            Lie,
            Lou,
            Jal,
            Jan,
            Pan,
            Raj,
            Ori,
            Wei,
            Ane,
            Niv,
            Bae,
            Bri,
            Bec,
            Kev,
            Jil,
            Jaz,
            Gaz,
            Tam,
            Ron,
            Don,
            Lin,
            Leu,
            Bre,
            Bon,
            Jax,
            Jet,
            Wes,
            Tav,
            Kat,
            Cal,
            Ghi,
            Gib,
            Ret,
            Uri,
            Uth,
            Eth,
            Ost,
            Bif,
            Ort,
            Orz,
            Tib,
            Ral,
            Opi,
            Sal,
            Yan,
            Mag,
            Maj,
            Zen,
            Sav,
            Pim,
            Pip,
            Zel,
            Zig,
            Kar,
            Sid,
            Syd,
            Pat,
            Ren,
            Bir,
            Lax,
            Vin,
            Wyn,
            Nej,
            Cem,
            Art,
            Rex,
            Hon,
            Rox,
            Baz,
            Poe,
            Edd,


            // Other
            The_Great_Wyrm, // to preserve this world, he forgot his own name
            Rakel,
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

                LEARN_NAME,

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
            // TODO: gather supplies, poke Antal, enter, leave


            // 1A
            CSID_1A_ANTAL_ESCAPING_ALIVE,
            CSID_1A_ANTAL_HELP_ME,
            CSID_1A_BALLISTA_1,
            CSID_1A_BALLISTA_2,
            CSID_1A_BALLISTA_3,
            CSID_1A_BALLISTA_4_DEATH_OF_DANIAL,
            CSID_1A_DEATH_OF_KAYLIE,
            CSID_1A_CITY_FALLEN,
            CSID_1A_LEIF_FLEEING_ALONE,
            CSID_1A_LEIF_FLEEING_SAVED_ANTAL,
            CSID_1A_LEIF_GETTING_IN_THE_BALLISTA,
            CSID_1A_LEIF_FIRED_BALLISTA,
            CSID_1A_LEIF_INEFFECTIVE_ATTACK,
            CSID_1A_LEIF_LEAVE_ME_ALONE,
            CSID_1A_LEIF_NEED_TO_ESCAPE,

            CSID_1A_POST_LEIF_ANTAL_CAMPFIRE,
            CSID_1A_POST_LEIF_FOUND_ANTAL,
            CSID_1A_POST_LEIF_SHOULD_FIND_ANTAL,
            CSID_1A_POST_LEIF_ESCAPED_ALONE,


            // 2A
            CSID_2A_PRE_LEIF_ANTAL_GATES_ARE_CLOSED,

            CSID_2A_LEIF_ANTAL_STEALTH_INTO_CITY,

            CSID_2A_POST_ANVIL_CAUGHT_YOU_SNEAKING_IN,
            CSID_2A_POST_TOHNI_SAW_YOU_SNEAK_PAST_GUARDS,


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
            ZERO_HP,
            DEATH_OF,
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

        enum CompassDirection {
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
            ENE,
        }

        enum Speed {
            INSTANT,
            SUPER_FAST,
            FAST,
            NORMAL,
            SLOW,
            SUPER_SLOW,
            STOPPED,
        }

        enum Size {
            SMALL,
            AVERAGE,
            LARGE,
        }

        enum NaturalElement {
            FIRE,
            WATER,
            AIR,
            EARTH,
            // magnets?
            LIGHT,
            DARK,
        }

        enum Superiority {
            SUPERIOR,
            STANDARD,
            INFERIOR,
        }

        enum AttackEfficacy {
            STANDARD,
            ADVANTAGE,
            DISADVANTAGE,
            NO_EFFECT,
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
        ENTITY, // things that act within the world.
        PROP,   // Objects in the world like chests, doors...
        ITEM,   // Something that lives in your inventory or in a menu.
        BULLET, // Any vfx, spells, projectiles, etc.
        GUI,     // Menu construction objects like labels, etc.
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
    float WORLD_SCALE = 1/16f;

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


