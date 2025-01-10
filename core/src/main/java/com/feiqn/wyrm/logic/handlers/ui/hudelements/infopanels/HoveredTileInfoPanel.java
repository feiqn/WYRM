package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class HoveredTileInfoPanel extends HUDElement {

    protected Label tileTypeLabel;

    public HoveredTileInfoPanel(WYRMGame game) {
        super(game);

//        final float width = Gdx.graphics.getWidth() * .3f;

        tileTypeLabel = new Label("", game.assetHandler.menuLabelStyle);

        backgroundImage.setColor(Color.RED);
        layout.add(tileTypeLabel).top().right();
//        setColor(1,1,1,0);
    }

    public void setTile(LogicalTileType type) {

    }

}
