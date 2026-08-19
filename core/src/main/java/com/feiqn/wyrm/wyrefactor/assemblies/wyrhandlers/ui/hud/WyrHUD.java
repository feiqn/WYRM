package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.gridhud.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public class WyrHUD extends Table implements WyrFrame {

    private final WyrHUD self = this;

    private final float PAD = Gdx.graphics.getWidth() * .005f; // TODO: globalize via wyrframe

    private final Table leftSubTable = new Table();
    private final Table rightSubTable = new Table();

    private final Table floatingTable = new Table();

    protected boolean isBusy = false;
    protected boolean uiHidden = true;

    private final GHUD_ActorInfo actorInfo;
    private final GHUD_TileInfo tileInfo;
    private final GHUD_WinCons winCons;
    private final GHUD_TurnOrder turnOrder;
    private final GHUD_InteractionMenu actionMenu;

    final Image curtain = new Image(WYRMGame.assets().solidBlueTexture);

    public WyrHUD() {
        this.setFillParent(true);
        this.top();

        curtain.setColor(Color.BLACK);
        this.add(curtain).fill().expand();

        // TODO: testing,
        //  code should eventually be moved to asset manager
        Skin skin = new Skin(Gdx.files.internal("ui/test/flat-skin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/test/flat-skin.atlas"));

        skin.addRegions(atlas);
        //

        actorInfo       = new GHUD_ActorInfo(skin);
        tileInfo        = new GHUD_TileInfo(skin);
        winCons         = new GHUD_WinCons(skin);
        turnOrder       = new GHUD_TurnOrder(skin); // TODO: why is this faded on screen? also it needs to update.
        actionMenu = new GHUD_InteractionMenu(skin);

        this.top();
        leftSubTable.top();
        rightSubTable.top();

        leftSubTable.setColor(1,1,1,0);
        rightSubTable.setColor(1,1,1,0);

        floatingTable.top();
        floatingTable.setColor(1,1,1,0);
        floatingTable.setSize(Gdx.graphics.getWidth() * .5f , Gdx.graphics.getHeight()* .5f); // TODO: math.min...

        floatingTable.add(actionMenu).expand().top().left().pad(PAD * 2);

        fadeCurtainOut();
    }

    protected void buildStandard() {
        this.clearChildren();
        leftSubTable.clearChildren();
        rightSubTable.clearChildren();

        addSubTables();

        leftSubTable.add(winCons).left().top().expandX().pad(PAD);
        leftSubTable.row();
        leftSubTable.add(turnOrder).left().top().expandX().pad(PAD);
        leftSubTable.row();

        rightSubTable.add(tileInfo).right().pad(PAD);
        rightSubTable.row();
        rightSubTable.add(actorInfo).right().top().expandX().pad(PAD);

        if(uiHidden) {
            leftSubTable.addAction(Actions.fadeIn(1));
            rightSubTable.addAction(Actions.fadeIn(1));
            floatingTable.addAction(Actions.fadeIn(.15f));
        }

        showActionsMenu();

    }

    private void addSubTables() {
        this.add(leftSubTable).colspan(2).expand().top().left().fill();
        this.add(rightSubTable).expand().right().top().fill();
    }

    public void standardize() {
        if(isBusy) return;
        actionMenu.standardize();
        handlers.input().clearFocus(false);
        uiHidden = true;
        buildStandard();
        hideActionsMenu();
    }

    public void buildForCutscene(Table playerTable) {
        handlers.input().lock();

        floatingTable.addAction(Actions.fadeOut(.3f));
        leftSubTable.addAction(Actions.fadeOut(.3f));
        rightSubTable.addAction(Actions.sequence(
            Actions.fadeOut(.3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    self.clearChildren();
                    self.center();
                    self.add(playerTable)
                        .fill()
                        .height(Math.min(600, Gdx.graphics.getHeight() * .8f))
                        .width(Math.min(800, Gdx.graphics.getWidth() * .85f))
                    ;
                }
            })
        ));

    }

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
            Actions.fadeOut(2),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    uiHidden = false;
                    handlers.standardizeParse();
                }
            })
        ));
    }

    public void fadeCurtainIn() {
        isBusy = true;
        this.clearChildren();
        curtain.setColor(0,0,0, 0);
        curtain.addAction(Actions.sequence(
            Actions.fadeIn(4),
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

    public void updateWinCon() {

        winCons.refresh();

        // fade out left,
        // refresh winCon panel,
        // fade in left
    }

    public void setTileContext(WyrTile tile, float mouseX, float mouseY) {
        switch (handlers.input().getInputMode()) {
            case LOCKED:
            case CUTSCENE:
                return;
        }
        setTileContext(tile);
        floatingTable.setPosition(mouseX, mouseY - floatingTable.getHeight());
    }

    public void setTileContext(WyrTile tile) {
//        if(handlers.register().getFocusedTile() == tile) return;
        handlers.register().setFocusedTile(tile);
        actionMenu.readTile(tile);
        tileInfo.setContext(tile);
        // TODO: stacking actors on one tile, non corporeal objectives like escapes
        if(tile.groundIsOccupied()) setActorContext(tile.getCorporealActor());
    }

    public void showActionsMenu() {
        if(floatingTable.getParent() == null) {
            handlers.screen().getHudStage().addActor(floatingTable);
        }
        floatingTable.setColor(1,1,1,1);
    }

    public void hideActionsMenu() {
        floatingTable.setColor(1,1,1,0);
        actionMenu.hide();
    }

    public void fireFirstActionMenu() { actionMenu.fireFirst(); }
    public void anchorActionsMenu() { actionMenu.anchor(); }
    public void standardizeActionsMenu() { actionMenu.standardize(); }
    public void setActorContext(WyrActor actor) { actorInfo.setContext(actor); }
    public void updateTurnOrder() { turnOrder.update(); }
    public boolean isBusy() { return isBusy; }

}
