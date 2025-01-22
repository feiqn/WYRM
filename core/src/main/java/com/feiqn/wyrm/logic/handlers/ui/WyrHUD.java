package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
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

//        this.setDebug(true);

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);
        hoveredTileInfoPanel = new HoveredTileInfoPanel(game);
        victConInfoPanel     = new VictConInfoPanel(game);

//        victConInfoPanel.setFillParent(true);

        build();
    }

    private void build() {
//        float tlPanelWidth = Math.min(Gdx.graphics.getWidth() * 0.35f, 3000); // Ensure panels are proportional but capped
//        float tlPanelHeight = Math.min(Gdx.graphics.getHeight() * 0.015f, 2000);

        this.clear();
        this.setFillParent(true);
        this.pad(Gdx.graphics.getHeight() * .0001f);
        this.top();
        this.add(victConInfoPanel).top().left(); // vict cons
        this.add(new Image(game.assetHandler.solidBlueTexture)).top().center().expandX(); // turn order
        this.add(hoveredUnitInfoPanel).top().right();
        reset();
    }

    public void updateHoveredUnitInfoPanel(Unit unit) {
        hoveredUnitInfoPanel.setUnit(unit);
    }

    public void updateTilePanel(LogicalTileType t) {
        hoveredTileInfoPanel.setTile(t);
    }

    public void resized(int width, int height) {
//        hoveredTileInfoPanel.resized(width, height);
//        hoveredTileInfoPanel.resized(width, height);
//        victConInfoPanel.resized(width, height);

//        float panelWidth = Math.min(width * 0.2f, 300);
//        float panelHeight = Math.min(height * 0.3f, 2);
//

//        build();
        this.invalidate();
    }


    public void reset() {
        victConInfoPanel.update();
    }

}
