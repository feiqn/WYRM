package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public final class GridHUD extends WyrHUD {

    private final RPGridMetaHandler h; // It's fun to just type "h".

    private final Table subTable = new Table();

//    private final GHUD_UnifiedInfo       unifiedInfo;
    private final GHUD_ActorInfo         actorInfo;
    private final GHUD_TileInfo          tileInfo;
    private final GHUD_WinCons           winCons;
    private final GHUD_TurnOrder         turnOrder;
    private final GHUD_ContextualActions contextDisplay;
    private final GHUD_ActionsMenu       actionsMenu;

    // TODO:
    //  - other popups and fullscreen menus / displays (combat, inventory, etc.)

    public GridHUD(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;

        // TODO: testing,
        //  code should eventually be moved to asset manager
        Skin skin = new Skin(Gdx.files.internal("ui/test/flat-skin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/test/flat-skin.atlas"));

        skin.addRegions(atlas);
        //

        actorInfo       = new GHUD_ActorInfo(skin);
        tileInfo        = new GHUD_TileInfo(skin);
        winCons         = new GHUD_WinCons(h, skin);
        turnOrder       = new GHUD_TurnOrder(skin, h);
//        unifiedInfo     = new GHUD_UnifiedInfo(skin, h);
        contextDisplay  = new GHUD_ContextualActions(skin, h);
        actionsMenu     = new GHUD_ActionsMenu(skin, h);

        subTable.top();

        buildLayout();
    }

    @Override
    protected void buildLayout() {
        this.clearChildren();
        subTable.clearChildren();

        this.add(subTable).expand().fill();
//        this.add(unifiedInfo).right().top();

        subTable.add(turnOrder).expandX().left().pad(Gdx.graphics.getWidth() * .005f);
        subTable.row();
        subTable.add(contextDisplay).top().left().pad(Gdx.graphics.getWidth() * .005f);
    }

    public void standardize() {
        contextDisplay.clear();
        h.input().clearFocus(false);
        buildLayout();
    }

    public void displayModalActionMenu() {
        this.clearChildren();
        subTable.clear();

        this.add(subTable).expand().fill();
//        this.add(unifiedInfo).right().top();

        subTable.add(turnOrder).expandX().left().pad(Gdx.graphics.getWidth() * .005f);
        subTable.row();
        subTable.add(actionsMenu).left().expandY().pad(Gdx.graphics.getWidth() * .0025f);

        h.input().focusMenu(actionsMenu);
    }

    public void clearContextDisplay() { contextDisplay.clear(); }
    public void setContextDisplayTile(GridTile tile) { contextDisplay.setContext(tile); }
    public void setActionMenuContext(GridTile tile, RPGridUnit unit) { actionsMenu.inferContext(tile, unit); }
    public void displayActionMenuForTile(GridTile tile) {
        actionsMenu.clear();
        for (RPGridInteraction interaction : tile.getAllInteractions()) {
            actionsMenu.addInteraction(interaction);
        }
        displayModalActionMenu();
    }
    public void addActionMenuInteraction(RPGridInteraction interaction) { actionsMenu.addInteraction(interaction); }
    public void setTileContext(GridTile tile) { tileInfo.setContext(tile); }
    public void setActorContext(RPGridActor actor) { actorInfo.setContext(actor); }
    public void updateTurnOrder() { turnOrder.update(); }
    public void updateWinCons() {  }
    public boolean isBusy() { return isBusy; }
}
