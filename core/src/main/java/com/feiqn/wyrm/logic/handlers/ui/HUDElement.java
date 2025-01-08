package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class HUDElement extends Stack {

    protected final WYRMGame game;

    protected Image background;

    protected final Table layout;

    protected final GridScreen ags;

    public HUDElement(WYRMGame game) {
        this.game = game;
        ags = game.activeGridScreen;
        background = new Image(game.assetHandler.solidBlueTexture);
        layout = new Table();
        layout.left().top();
        layout.setDebug(true);
        addActor(background);
        addActor(layout);
//        setFillParent(true);
        layout.setFillParent(true);
    }

}
