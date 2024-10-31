package com.feiqn.wyrm.logic.handlers.ui.hudelements;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;

public class PopupMenu extends HUDElement {

    final protected PopupMenu self = this;

//    protected float width,
//                    height;

    public PopupMenu(WYRMGame game) {
        super(game);
        background = new Image(game.assetHandler.blueButtonTexture);
        background.setColor(1,1,1,.95f);
        abs.focusedHUDElement = self;
    }

//    @Override
//    public void setWidth(float width) {
//        this.width = width;
//        background.setWidth(width);
//    }
//
//    @Override
//    public void setHeight(float height) {
//        this.height = height;
//        background.setHeight(height);
//    }

}
