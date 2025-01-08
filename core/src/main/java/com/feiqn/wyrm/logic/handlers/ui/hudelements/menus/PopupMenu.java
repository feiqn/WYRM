package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;

public class PopupMenu extends HUDElement {

    final protected PopupMenu self = this;

    public PopupMenu(WYRMGame game) {
        super(game);
        background.setDrawable(new TextureRegionDrawable(game.assetHandler.blueButtonTexture));
        background.setColor(1,1,1,.9f);
        ags.focusedHUDElement = self;
    }

}
