package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;

public class FullScreenMenu extends HUDElement {

    final protected FullScreenMenu self = this;

    public FullScreenMenu(WYRMGame game) {
        super(game);
        background = new Image(game.assetHandler.solidBlueTexture);
        background.setColor(1,1,1,1f);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ags.focusedHUDElement = self;
    }

}
