package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

public class PopupMenu extends HUDElement {

    final protected PopupMenu self = this;

    protected float width,
                    height;

    protected Image background;

    public PopupMenu(WYRMGame game) {
        super(game);
        game.activeBattleScreen.activePopupMenu = self;
        background = new Image(game.assetHandler.blueButtonTexture);
        background.setColor(1,1,1,.95f);

        width  = game.activeBattleScreen.hudStage.getWidth() * .5f;
        height = game.activeBattleScreen.hudStage.getHeight() * .5f;

        background.setSize(width, height);
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
        background.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
        background.setHeight(height);
    }

}
