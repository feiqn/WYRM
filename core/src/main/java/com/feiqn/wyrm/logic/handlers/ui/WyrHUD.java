package com.feiqn.wyrm.logic.handlers.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredTileInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.HoveredUnitInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.TurnOrderPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.FullScreenMenu;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.ToolTipPopup;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class WyrHUD extends Table {

    private final WYRMGame game;

    private final HoveredUnitInfoPanel hoveredUnitInfoPanel;
    private final HoveredTileInfoPanel hoveredTileInfoPanel;
    private final VictConInfoPanel     victConInfoPanel;
    private final TurnOrderPanel       turnOrderPanel;

    private PopupMenu activePopup;
    private FullScreenMenu activeFullscreen;
    private ToolTipPopup activeToolTip;

    private final Table subTable;

    public WyrHUD(WYRMGame game) {
        this.game = game;

        this.setFillParent(true);

        hoveredUnitInfoPanel = new HoveredUnitInfoPanel(game);
        hoveredTileInfoPanel = new HoveredTileInfoPanel(game);
        victConInfoPanel     = new VictConInfoPanel(game);
        turnOrderPanel       = new TurnOrderPanel(game);
        subTable = new Table();

        this.top();
        subTable.left();
        build();
    }

    private void build() {
        this.clearChildren();
        subTable.clearChildren();

        this.add(victConInfoPanel).top().left(); // vict cons
        this.add(turnOrderPanel).top().center().expandX(); // turn order
        this.add(hoveredUnitInfoPanel).top().right(); // unit info
        this.row();
        this.add(hoveredTileInfoPanel).right().colspan(3); // tile info
        this.row();
        this.add(subTable).colspan(3).expand().fill();
    }

    public void addToolTip(ToolTipPopup toolTipPopup) {
        activeToolTip = toolTipPopup;
        subTable.add(toolTipPopup).pad(activePopup.getWidth() * .05f);
    }

    public void removeToolTip() {
            subTable.clearChildren();
            if(activePopup != null) addPopup(activePopup);
            if(activeFullscreen != null) addFullscreen(activeFullscreen);
            if(activeToolTip != null) activeToolTip = null;
    }

    public void addPopup(PopupMenu popup) {
        if(activePopup != null) subTable.clearChildren();
        game.activeGridScreen.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
        subTable.add(popup).expandY().padLeft(Gdx.graphics.getWidth() * 0.025f);
        this.activePopup = popup;

    }

    public void removePopup() {
        subTable.clearChildren();
        activePopup = null;
        if(activeFullscreen != null) {
            addFullscreen(activeFullscreen);
        } else {
            game.activeGridScreen.setInputMode(GridScreen.InputMode.STANDARD);
        }
    }

    public void addFullscreen(FullScreenMenu fullscreen) {
        game.activeGridScreen.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
        subTable.clearChildren();
        subTable.add(fullscreen).expand().fill();
        this.activeFullscreen = fullscreen;
    }

    public void removeFullscreen() {
        subTable.clearChildren();
        if(activePopup != null) {
            addPopup(activePopup);
        } else {
            game.activeGridScreen.setInputMode(GridScreen.InputMode.STANDARD);
        }
        this.activeFullscreen = null;
    }

    public void updateTurnOrderPanel() { turnOrderPanel.layoutPanels(); }

    public void updateHoveredUnitInfoPanel(SimpleUnit unit) {
        hoveredUnitInfoPanel.setUnit(unit);
    }

    public void updateTilePanel(LogicalTileType t) {
        hoveredTileInfoPanel.setTile(t);
    }

    public void reset() {
        activeToolTip = null;
        activePopup = null;
        activeFullscreen = null;

        build();
        victConInfoPanel.update();
    }

}
