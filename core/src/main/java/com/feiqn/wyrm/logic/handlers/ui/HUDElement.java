package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class HUDElement extends Stack {

    protected final WYRMGame game;

    protected Image backgroundImage;

    protected final Table layout;

    protected final GridScreen ags;

    public HUDElement(WYRMGame game) {
        this.game = game;
        ags = game.activeGridScreen;
        backgroundImage = new Image(game.assetHandler.solidBlueTexture);
        layout = new Table();
        layout.left().top();
        addActor(backgroundImage);
        addActor(layout);
        backgroundImage.setFillParent(true);
        layout.setFillParent(true);
    }

    /**
     * An example of bad advice from ChatGPT that seems good, and may be good in some other way
     * which I don't quite understand yet.
     */
//    public void updateFontScale() {
//        float scale = Math.min(width / 600f, height / 400f); // Adjust base scale as needed
//        for (Actor actor : layout.getChildren()) {
//            if (actor instanceof Label) {
//                Label label = (Label) actor;
//                label.getStyle().font.getData().setScale(scale);
//            }
//        }
//    }

    public void resized(int width, int height) {
//        updateFontScale();
    }

}
