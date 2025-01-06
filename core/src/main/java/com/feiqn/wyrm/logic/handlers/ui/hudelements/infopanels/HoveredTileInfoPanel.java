package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class HoveredTileInfoPanel extends HUDElement {

    protected Label tileTypeLabel;

    public HoveredTileInfoPanel(WYRMGame game) {
        super(game);

        final float width = Gdx.graphics.getWidth() * .3f;

        tileTypeLabel = new Label("", game.assetHandler.menuLabelStyle);

        background = new Image(game.assetHandler.solidBlueTexture);

//        background.setSize(width, width * .4f);

//        setPosition(Gdx.graphics.getWidth() - background.getWidth(), Gdx.graphics.getHeight() - background.getHeight() * 1.2f);

//        background.setDrawable(new TextureRegionDrawable(game.assetHandler.solidBlueTexture));
        background.setColor(Color.RED);
        addActor(background);
    }

    public void setTile(LogicalTileType type) {

    }

}
