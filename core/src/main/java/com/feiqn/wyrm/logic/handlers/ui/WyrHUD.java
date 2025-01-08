package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredTileInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredUnitInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.models.unitdata.Unit;

public class WyrHUD extends Table {

    private final WYRMGame game;

    private HoveredUnitInfoPanel hoveredUnitInfoPanel;
    private HoveredTileInfoPanel hoveredTileInfoPanel;

    public Array<VictConInfoPanel> victConUI;

    public WyrHUD(WYRMGame game) {
        this.game = game;
        this.setFillParent(true);

        this.setDebug(true);

        victConUI = new Array<>();

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);
        hoveredTileInfoPanel = new HoveredTileInfoPanel(game);

        this.top();
        this.add(new Image(game.assetHandler.solidBlueTexture)); // vict cons
        this.add(new Image(game.assetHandler.solidBlueTexture)).center().expandX(); // turn order
        this.add(hoveredUnitInfoPanel).top().right();

    }

    public void addVictConPanel(VictConInfoPanel panel) {
//        final float multiplier = panel.getIndex() + 1;
//        final float y = Gdx.graphics.getHeight() - (panel.getHeight() * multiplier);
//        victConGroup.addActor(panel); // TODO: animated fade in wrapper for dynamic adding mid fight
//        panel.setPosition(0, 0);
    }

    public void updateHoveredUnitInfoPanel(Unit unit) {
        hoveredUnitInfoPanel.setUnit(unit);
    }

    public void removeHoveredUnitInfoPanel() {
        // TODO: slide up to hide behind tile info
//        hoveredUnitInfoPanel.remove();
    }



}
