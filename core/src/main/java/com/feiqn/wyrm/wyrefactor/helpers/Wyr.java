package com.feiqn.wyrm.wyrefactor.helpers;

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

    // world
    float WORLD_SCALE = 1/16f;

    // camera
    float X_TOLERANCE = 1;
    float Y_TOLERANCE = 2f;
    float FOLLOW_SPEED = .35F;
    float TARGET_SPEED = .65f;

    float MOVE_SPEED   = .195f;

    // ui
    float FONT_SCALE = .8F;

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
