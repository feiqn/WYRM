package com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr;


import java.util.HashMap;

/** Commence operation: Bird On a Wyr!
 */
public interface Wyr {

    HashMap<FlagID, Boolean> flags = new HashMap<>();

    enum WyrType {
        // This might get obsoleted, seems very likely.
        GEM_WORLD,
        CARD_WORLD,
        RPGRID,
        OVERWORLD,
        NARRATIVE_WORLD,
        MENU_WORLD,
        AGNOSTIC,
    }

    /**
     * FlagID, CutsceneID, CharacterID, and StageID are pre-populated with values specific to the main WYRM campaign. </br>
     * Whether you are modding WYRM or using WyrFrame for an unrelated project, feel free to add to the existing list or rename existing values; however, deleting values is not recommended as existing switch calls will throw errors. If you really want to clear the errors, go ahead and empty the lists.</br>
     * Always implement switch checks with Default case breaks, and unused values will not affect implementation.
     */
    enum FlagID {
        /*
         * All campaign event flags tracked here.
         * Verbiage should always reflect "true" being the non-default state.
         */

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


        CUTSCENE_1A_POST_LEIF_FOUND_ANTAL,

    }

    enum CutsceneID {
        // STORY A
        // 0A
        // TODO: gather supplies, poke antal, enter, leave

        CSID_0_DEBUG,

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

    enum CharacterID {
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
        UNIT,   // "Living", "animate" things that "live" within the world. Humanoids, cards, gems, music notes, etc.
        PROP,   // Objects in the world like chests, doors...
        ITEM,   // Something that lives in your inventory or in a menu.
        BULLET, // Any vfx, spells, projectiles, etc.
        UI,     // Menu construction objects like labels, etc.
    }

    enum AnimationState {
        FACING_, // use in conjunction with Compass. I.E., FACING_N
        ATTACKING, // use in conjunction with specific world variables
        IDLE,
        FLOURISH
    }

    // Used for laying out portraits in cutscenes.
    enum HorizontalPosition {
        FAR_LEFT,
        LEFT,
        LEFT_OF_CENTER,
        CENTER,
        RIGHT_OF_CENTER,
        RIGHT,
        FAR_RIGHT
    }

    enum TeamAlignment {
        PLAYER,
        ALLY,
        ENEMY,
        STRANGER
    }

    enum InputMode {
        STANDARD,
        ACTOR_FOCUSED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
    }

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

    /** Use in conjunction with other state enums, or simply ignore and create your own shader state list.
     */
    enum ShaderState {
        DIM,
        HIGHLIGHT,
        STANDARD
    }

    // TODO: learn more about enums
    enum Speed {
        INSTANT,
        SUPER_FAST,
        FAST,
        NORMAL,
        SLOW,
        SUPER_SLOW,
        STOPPED
    }

    enum EquipmentEffect {
        BURN,
        POISON,
        SOULBRAND,
        STUN,
        CHILL,

        LIFE_STEAL,

        WATER_WALKING,

        DEFT_CLIMBING, // traverse low walls

        CRITICAL_DAMAGE_UP,
        CRITICAL_DAMAGE_UP_UP,
        CRITICAL_DAMAGE_UP_UP_UP,

        CRITICAL_MULTIPLY,
        CRITICAL_EXPONENTIAL,

        CRITICAL_IMMUNE,

        CRITICAL_CHANCE_UP,
        CRITICAL_CHANCE_UP_UP,
        CRITICAL_CHANCE_UP_UP_UP,

    }

    enum Expression {

        // TODO:
        //  Group by similar enough to share a portrait,
        //  prune redundancies.

        SURPRISED,
        SMILING,DETERMINED,
        TALKING,
        EMBARRASSED,
        HOPEFUL,
        PANICKED,
        WORRIED,
        WOUNDED,
        BADLY_WOUNDED,
        EXCITED,
        WINCING,
        MANIACAL,
        SLY,
        THINKING,
        CURIOUS,
        DESPAIRING,
        ANNOYED,
        EXHAUSTED,
        STOIC,
        SAD,
        FROWNING,
        ANGRY,
        SHOUTING,
        STERN,
        FURIOUS,
        DISTANT,
        DYING,
        SOLEMN,
    }

    enum AttackEfficacy {
        STANDARD,
        ADVANTAGE,
        DISADVANTAGE,
        NO_EFFECT,
    }

    // world
    float WORLD_SCALE = 1/16f; // TODO: not sure if this should be in grid Wyr

    // camera
    float X_TOLERANCE = 1;
    float Y_TOLERANCE = 2f;
    float FOLLOW_SPEED = .35F;
    float TARGET_SPEED = .65f;

    // actors
    float MOVE_SPEED   = .195f;

    // ui
    float FONT_SCALE = .8F;

    default void triggerFlag(FlagID flag) {
        flags.put(flag, true);
    }

    default Boolean checkFlag(FlagID flag) {
        return flags.getOrDefault(flag, false);
    }

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
