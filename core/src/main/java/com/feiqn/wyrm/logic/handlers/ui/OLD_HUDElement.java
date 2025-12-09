package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;

public class OLD_HUDElement extends Stack {

    protected static WYRMGame game;

    protected Image backgroundImage;

    protected final Table layout;

    protected final OLD_GridScreen ags;

    public OLD_HUDElement(WYRMGame game) {
        OLD_HUDElement.game = game;
        ags = game.activeOLDGridScreen;
        ags.focusedOLDHUDElement = this;

        backgroundImage = new Image(game.assetHandler.solidBlueTexture);

        layout = new Table();

        final Container<Table> layoutContainer = new Container<>(layout);

        // This is a stack, which natively fills children to parent size, so calling .setFillParent() is not needed.

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
