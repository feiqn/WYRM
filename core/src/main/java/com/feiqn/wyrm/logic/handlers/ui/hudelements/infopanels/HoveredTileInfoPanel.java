package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.LogicalTileType;

public class HoveredTileInfoPanel extends HUDElement {

    protected Label tileTypeLabel;

    public HoveredTileInfoPanel(WYRMGame game) {
        super(game);

        tileTypeLabel = new Label("", game.assetHandler.menuLabelStyle);

        layout.add(tileTypeLabel).top().right().fill();
    }

    public void setTile(LogicalTileType type) {
        tileTypeLabel.setText(type.name());
    }

}
