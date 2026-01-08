package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements.GHUD_ContextualActions;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements.GHUD_TurnOrder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements.GHUD_UnifiedInfo;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public final class GridHUD extends WyrHUD {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final Table subTable = new Table();

    private final GHUD_UnifiedInfo       unifiedInfo;
    private final GHUD_TurnOrder         turnOrder;
    private final GHUD_ContextualActions contextActions;

    // TODO:
    //  - other popups and fullscreen menus / displays (combat, inventory, etc.)

    public GridHUD(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;

        // TODO: testing,
        //  code should eventually be moved to asset manager
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("test/uiSkinTest/uiskin.atlas"));
        skin.addRegions(atlas);
        //

        turnOrder      = new GHUD_TurnOrder(h);
        contextActions = new GHUD_ContextualActions(skin, h);
        unifiedInfo    = new GHUD_UnifiedInfo(skin, h);

        // Since subTable is added to parent table
        // and not a stack, it needs to set its own size.
        subTable.setFillParent(true);

        buildLayout();
    }

    @Override
    protected void buildLayout() {
        layout.clearChildren();
        subTable.clearChildren();

        layout.add(subTable).colspan(2).top();
        layout.add(unifiedInfo).top();
        subTable.add(turnOrder).top();
        subTable.row();
        subTable.add(contextActions).top();


        // TODO: etc... (see design notes)
    }

    public void clearContextInteractions() { contextActions.clear(); }
    public void setTileContext(GridTile tile) { contextActions.setContext(tile); }
    public void addTileInteraction(GridInteraction interaction) { contextActions.addInteraction(interaction); }
    public void updateUnitContext(GridUnit unit) { unifiedInfo.updateUnitContext(unit); }
    public void updateTileContext(GridTile tile) { unifiedInfo.updateTileContext(tile); }
    public void updatePropContext(GridProp prop) { unifiedInfo.updatePropContext(prop); }
    // TODO:
    //  - add win cons
    //  - add fail cons
    public void updateTurnOrder() { turnOrder.update(); }


}
