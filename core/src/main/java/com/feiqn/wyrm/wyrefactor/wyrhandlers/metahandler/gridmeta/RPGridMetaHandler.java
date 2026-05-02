package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.helpers.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.grid.GridInteractionHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.grid.GridComputerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid.GridConditionsRegister;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid.GridPriorityHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.GridHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.RPGridMapHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworld.RPGridScreen;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.RPGridInputHandler.InputMode.STANDARD;

public final class RPGridMetaHandler extends MetaHandler<
        GridInteractionHandler,
        RPGridInputHandler,
        GridHUD,
        RPGridMapHandler,
        GridCombatHandler,
        GridComputerHandler,
        GridCutsceneHandler,
        GridPriorityHandler,
        GridConditionsRegister,
        RPGridScreen> {

    // The cameraman seems fairly agnostic to
    // old vs wyr format. Watching him closely, though.

    public RPGridMetaHandler(TiledMap tiledMap) {
        map                   = new RPGridMapHandler(this, tiledMap);
        cameraMan             = new CameraMan();
        interactionHandler    = new GridInteractionHandler(this);
        inputHandler          = new RPGridInputHandler(this);
        combatHandler         = new GridCombatHandler(this);
        computerHandler       = new GridComputerHandler(this);
        cutsceneHandler       = new GridCutsceneHandler(this);
        hud                   = new GridHUD(this);
        priorityHandler       = new GridPriorityHandler(this);
        conditionsRegister    = new GridConditionsRegister(this);
    }

    public void standardizeParse() {
        input().setInputMode(STANDARD);
        hud.standardize();
        for(RPGridActor a : register().unifiedTurnOrder()) {
            a.standardize();
        }
        clearAndInvalidate();
    }
    public void clearEphemeral() {
        map.clearAllHighlights();
        for(RPGridActor a : register().unifiedTurnOrder()) {
            a.clearEphemeralInteractions();
        }
    }
    public void clearAndInvalidate() {
        clearEphemeral();
        priority().parsePriority();
    }


    @Override
    public RPGridScreen screen() {
        if(WYRMGame.activeScreen().getWyrType() == WyrType.RPGRID) {
            return (RPGridScreen) WYRMGame.activeScreen();
        } else {
            Gdx.app.log("GridMetaHandler", "Root active screen is not Grid.");
            return null;
        }
    }
    public boolean isBusy() {
        return combat().isBusy()
            || cutscenes().isBusy()
            || interactions().isBusy()
            || ai().isBusy()
            || priority().isBusy()
            || input().isBusy()
            || map().isBusy()
            || hud().isBusy();
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }
}
