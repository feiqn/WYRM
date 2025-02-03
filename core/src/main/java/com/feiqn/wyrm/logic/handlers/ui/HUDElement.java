package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class HUDElement extends Stack {

    protected static WYRMGame game;

    protected Image backgroundImage;

    protected final Table layout;

    protected final GridScreen ags;

    public HUDElement(WYRMGame game) {
        HUDElement.game = game;
        ags = game.activeGridScreen;
        ags.focusedHUDElement = this;

        backgroundImage = new Image(game.assetHandler.solidBlueTexture);

        layout = new Table();
        layout.setDebug(true);


        final Container<Table> layoutContainer = new Container<>(layout);

        addActor(backgroundImage);
        addActor(layoutContainer);
    }

    /* An example of bad advice from ChatGPT that seemed good, and may be good in some other way
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

}
