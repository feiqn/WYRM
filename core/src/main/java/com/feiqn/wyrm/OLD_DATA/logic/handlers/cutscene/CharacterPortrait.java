package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class CharacterPortrait extends Image {

    private final WYRMGame game;

    public CharacterPortrait(WYRMGame game) {
        this.game = game;
    }

    // TODO: use boolean checks in override Draw method for animations like bouncing and sliding
}
