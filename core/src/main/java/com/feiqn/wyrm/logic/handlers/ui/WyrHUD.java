package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredTileInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredUnitInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.TurnOrderPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.SimpleUnit;

public class WyrHUD extends Table {

    private final WYRMGame game;

    private final HoveredUnitInfoPanel hoveredUnitInfoPanel;
    private final HoveredTileInfoPanel hoveredTileInfoPanel;
    private final VictConInfoPanel     victConInfoPanel;
    private final TurnOrderPanel       turnOrderPanel;


    public WyrHUD(WYRMGame game) {
        this.game = game;

//        this.setDebug(true);

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);
        hoveredTileInfoPanel = new HoveredTileInfoPanel(game);
        victConInfoPanel     = new VictConInfoPanel(game);
        turnOrderPanel       = new TurnOrderPanel(game);


        build();
    }

    private void build() {
//        float tlPanelWidth = Math.min(Gdx.graphics.getWidth() * 0.35f, 3000); // Ensure panels are proportional but capped
//        float tlPanelHeight = Math.min(Gdx.graphics.getHeight() * 0.015f, 2000);

        this.clear();
        this.setFillParent(true);
//        this.pad(Gdx.graphics.getHeight() * .001f);
        this.top();
        this.add(victConInfoPanel).top().left(); // vict cons
        this.add(turnOrderPanel).top().center().expandX(); // turn order
        this.add(hoveredUnitInfoPanel).top().right();
//        reset();
    }

    public void updateTurnOrderPanel() { turnOrderPanel.layoutPanels(); }

    public void updateHoveredUnitInfoPanel(SimpleUnit unit) {
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
//        this.invalidate();
    }


    public void reset() {
        victConInfoPanel.update();
    }

}
