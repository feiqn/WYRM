package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.elements.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

public class WyrHUD extends Table implements Wyr {

    private final float PAD = Gdx.graphics.getWidth() * .005f;

    private final Table leftSubTable = new Table();
    private final Table rightSubTable = new Table();

//    private final GHUD_UnifiedInfo       unifiedInfo;
    private GHUD_ActorInfo actorInfo;
    private GHUD_TileInfo tileInfo;
    private GHUD_WinCons winCons;
    private GHUD_TurnOrder turnOrder;
    private GHUD_ContextualActions contextDisplay;
    private GHUD_ActionsMenu       actionsMenu;

    // TODO:
    //  - other popups and fullscreen menus / displays (combat, inventory, etc.)

    public WyrHUD() {
        this.setFillParent(true);
        this.top();
        curtain = new Image(WYRMGame.assets().solidBlueTexture);
        curtain.setColor(Color.BLACK);
        this.add(curtain).fill();
        // TODO: testing,
        //  code should eventually be moved to asset manager
        Skin skin = new Skin(Gdx.files.internal("ui/test/flat-skin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/test/flat-skin.atlas"));

        skin.addRegions(atlas);
        //

        actorInfo       = new GHUD_ActorInfo(skin);
        tileInfo        = new GHUD_TileInfo(skin);
        winCons         = new GHUD_WinCons(skin);
        turnOrder       = new GHUD_TurnOrder(skin);
//        unifiedInfo     = new GHUD_UnifiedInfo(skin, h);
        contextDisplay  = new GHUD_ContextualActions(skin);
        actionsMenu     = new GHUD_ActionsMenu(skin);

        leftSubTable.top();
        rightSubTable.top();

//        setDebug(true);

        buildStandard();
    }

    protected void buildStandard() {
        this.clearChildren();
        leftSubTable.clearChildren();
        rightSubTable.clearChildren();

        this.top();

        addSubTables();

        leftSubTable.add(winCons).left().top().expandX().pad(PAD);
        leftSubTable.row();
        leftSubTable.add(turnOrder).left().top().expandX().pad(PAD);
        leftSubTable.row();
        leftSubTable.add(contextDisplay).top().left().pad(PAD);

        rightSubTable.add(tileInfo).right().pad(PAD);
        rightSubTable.row();
        rightSubTable.add(actorInfo).right().top().expandX().pad(PAD);
    }

    private void addSubTables() {
        this.add(leftSubTable).colspan(2).expand().top().left().fill();
        this.add(rightSubTable).expand().right().top().fill();
    }

    public void standardize() {
        contextDisplay.clear();
        handlers.input().clearFocus(false);
        buildStandard();
    }

    public void displayModalActionMenu() {
        this.clearChildren();
        leftSubTable.clear();

        addSubTables();
//        this.add(unifiedInfo).right().top();

        leftSubTable.add(turnOrder).expandX().left().pad(PAD);
        leftSubTable.row();
        leftSubTable.add(actionsMenu).left().expandY().pad(PAD * .5f);

        handlers.input().focusMenu(actionsMenu);
    }

    public void buildForCutscene(Table playerTable) {
//        leftSubTable.addAction(Actions.fadeOut(.3f));
//        rightSubTable.addAction(Actions.fadeOut(.3f));
        this.clearChildren();
        this.bottom();
        this.add(playerTable).expandX().fill().pad(PAD * 3);
    }

    public void clearContextDisplay() { contextDisplay.clear(); }
    public void setContextDisplayTile(RPGridTile tile) { contextDisplay.setContext(tile); }
    public void setActionMenuContext(RPGridTile tile, WyrActor unit) { actionsMenu.inferContext(tile, unit); }
    public void displayActionMenuForTile(RPGridTile tile) {
        actionsMenu.clear();
        for (WyrInteraction interaction : tile.getAllInteractions()) {
            actionsMenu.addInteraction(interaction);
        }
        displayModalActionMenu();
    }
    public void addActionMenuInteraction(WyrInteraction interaction) { actionsMenu.addInteraction(interaction); }
    public void setTileContext(RPGridTile tile) { tileInfo.setContext(tile); }
    public void setActorContext(WyrActor actor) { actorInfo.setContext(actor); }
    public void updateTurnOrder() { turnOrder.update(); }
    public void updateWinCons() {  }
    public boolean isBusy() { return isBusy; }

    protected boolean isBusy = false;
    protected boolean uiHidden = true;
    protected Image curtain;


    public void buildForFullscreenCutscene(Image background, Table playerTable) {
        this.clearChildren();
        final Stack fullscreenStack = new Stack(background);
        fullscreenStack.setFillParent(true);
        fullscreenStack.add(playerTable);
        this.add(fullscreenStack);
    }

    public void fadeCurtainOut() {
        isBusy = true;
        curtain.addAction(Actions.sequence(
            Actions.fadeOut(.5f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    uiHidden = false;
                    buildStandard();
                }
            })
        ));
    }

    public void fadeCurtainIn() {
        isBusy = true;

        this.clearChildren();
        curtain.setColor(0,0,0, 0);
        curtain.addAction(Actions.sequence(
            Actions.fadeIn(.5f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    uiHidden = true;
                    // TODO: progress somehow
                }
            })
        ));

    }

}
