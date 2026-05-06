package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.helpers.CameraMan;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.GridInteractionHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.grid.GridComputerHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid.RPGridConditionsRegister;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid.GridPriorityHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.GridHUD;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.RPGridMapHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler.InputMode.STANDARD;

public final class RPGridMetaHandler extends MetaHandler<
        GridInteractionHandler,
        RPGridInputHandler,
        GridHUD,
        RPGridMapHandler,
        GridCombatHandler,
        GridComputerHandler,
        GridCutsceneHandler,
        GridPriorityHandler,
        RPGridConditionsRegister,
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
        conditionsRegister    = new RPGridConditionsRegister(this);
    }

    public void standardizeParse() {
        Gdx.app.log("h", "standardized");
        input().setInputMode(STANDARD);
        hud.standardize();
        cameraMan.stopFollowing();
        for(RPGridActor a : register().unifiedTurnOrder()) {
            a.standardize();
        }
        map.standardizeAll();
        priorityHandler.parsePriority();
    }
    public void clearEphemeral() {
        map.standardizeAll();
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
