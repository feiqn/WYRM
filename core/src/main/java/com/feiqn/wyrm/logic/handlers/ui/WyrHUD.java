package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredTileInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredUnitInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.Unit;

public class WyrHUD extends Table {

    private final WYRMGame game;

    private final HoveredUnitInfoPanel hoveredUnitInfoPanel;
    private final HoveredTileInfoPanel hoveredTileInfoPanel;
    private final VictConInfoPanel     victConInfoPanel;


    public WyrHUD(WYRMGame game) {
        this.game = game;
        this.setFillParent(true);

        this.setDebug(true);

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);
        hoveredTileInfoPanel = new HoveredTileInfoPanel(game);
        victConInfoPanel     = new VictConInfoPanel(game);

        this.top();
        this.add(victConInfoPanel).top().left(); // vict cons
        this.add(new Image(game.assetHandler.solidBlueTexture)).top().center().expandX(); // turn order
        this.add(hoveredUnitInfoPanel).top().right();

    }

    public void updateHoveredUnitInfoPanel(Unit unit) {
        hoveredUnitInfoPanel.setUnit(unit);
    }

    public void updateTilePanel(LogicalTileType t) {
        hoveredTileInfoPanel.setTile(t);
    }


    public void reset() {
        victConInfoPanel.update();
    }

}
