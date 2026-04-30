package com.feiqn.wyrm.wyrefactor.helpers;

/** Commence operation: Bird On a Wyr!
 */
public interface Wyr {

    float WORLD_SCALE = 1/16f;

    // camera
    float X_TOLERANCE = 1;
    float Y_TOLERANCE = 2f;
    float FOLLOW_SPEED = .3F;

    float MOVE_SPEED   = .165f;

    default WyrType getWyrType() {
        return WyrType.AGNOSTIC;
    }
}
