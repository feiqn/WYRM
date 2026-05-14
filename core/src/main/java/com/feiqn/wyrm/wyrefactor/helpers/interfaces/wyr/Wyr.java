package com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr;

/** Commence operation: Bird On a Wyr!
 */
public interface Wyr {

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

    /** This is meant to represent a set of
     * "primitive" entity types for anything in
     * the game world. <br>
     * If you think of something else that you
     * feel should expand this list, feel free to
     * grow it.<br>
     * Implement default switch cases for scalability.
     */
    enum ActorType {
        UNIT,   // Living, animate things.
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

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
