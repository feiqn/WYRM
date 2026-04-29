package com.feiqn.wyrm.wyrefactor.helpers;

/** This is meant to represent a set of
 * "primitive" entity types for anything in
 * the game world. <br>
 * If you think of something else that you
 * feel should expand this list, feel free to
 * grow it.<br>
 * Implement default switch cases for scalability.
 */
public enum ActorType {
    UNIT,   // Living, animate things.
    PROP,   // Objects in the world like chests, doors...
    ITEM,   // Something that lives in your inventory or in a menu.
    BULLET, // Any vfx, spells, projectiles, etc.
    UI,     // Menu construction objects like labels, etc.
}
