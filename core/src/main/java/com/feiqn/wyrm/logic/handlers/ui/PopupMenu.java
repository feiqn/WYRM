package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

public class PopupMenu extends Group {

    final public WYRMGame game;

    final protected PopupMenu self = this;

    protected float width,
                    height;

    protected Image background;

    public PopupMenu(WYRMGame game) {
        super();
        this.game = game;
        game.activeBattleScreen.activePopupMenu = this;
        background = new Image(game.assetHandler.blueButtonTexture);
        background.setColor(1,1,1,.95f);

        width  = game.activeBattleScreen.hudStage.getWidth() * .5f;
        height = game.activeBattleScreen.hudStage.getHeight() * .5f;

        background.setSize(width, height);
    }


    public void setWidth(float width) {
        this.width = width;
        background.setWidth(width);
    }

    public void setHeight(float height) {
        this.height = height;
        background.setHeight(height);
    }

}
