package com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.OLD_HUDElement;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.TileType;

public class HoveredTileInfoPanel extends OLD_HUDElement {

    protected Label tileTypeLabel;

    public HoveredTileInfoPanel(WYRMGame game) {
        super(game);

        tileTypeLabel = new Label("", WYRMGame.assets().menuLabelStyle);

        layout.add(tileTypeLabel).top().right().fill();
    }

    public void setTile(TileType type) {
        tileTypeLabel.setText(type.name());
    }

}
