package com.feiqn.wyrm.logic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

public class PopupMenu extends Group {

    final public WYRMGame game;

    protected Image background;

    public PopupMenu(WYRMGame game) {
        super();
        this.game = game;
        game.activeBattleScreen.activePopupMenu = this;
        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,96,96);
        background = new Image(blueSquareRegion);

    }

    protected void AddLargeRight(){
        // child classes should override these functions, call super(), then fill with content contextually

        background.setHeight(Gdx.graphics.getHeight() * .85f);
        background.setWidth(Gdx.graphics.getWidth() * .4f);

        background.setPosition(Gdx.graphics.getWidth() * .55f, Gdx.graphics.getHeight() * .1f);

        background.setColor(1,1,1,.95f);

        addActor(background);

    }

    protected void AddSmallTargeted(Unit unit) {

    }

}
