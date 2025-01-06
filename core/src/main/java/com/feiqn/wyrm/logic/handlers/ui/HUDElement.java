package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class HUDElement extends Group {

    protected final WYRMGame game;

    protected Image background;

    protected final GridScreen ags;

    public HUDElement(WYRMGame game) {
        this.game = game;
        ags = game.activeGridScreen;
        background = new Image();
    }

}
