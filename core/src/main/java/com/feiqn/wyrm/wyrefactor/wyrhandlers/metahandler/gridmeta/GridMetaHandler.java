package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid.GridConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.GridHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.GridMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public final class GridMetaHandler extends MetaHandler<GridActorHandler, GridInputHandler, GridHUD, GridMap, GridCombatHandler, GridComputerPlayerHandler, GridCutsceneHandler, GridConditionsHandler, GridScreen> {

    // The cameraman seems fairly agnostic to
    // old vs wyr format. Watching him closely, though.

    public GridMetaHandler(TiledMap tiledMap) {
        map                   = new GridMap(this, tiledMap);
        cameraMan             = new CameraMan();
        actorHandler          = new GridActorHandler(this);
        inputHandler          = new GridInputHandler(this);
        combatHandler         = new GridCombatHandler(this);
        comHandler            = new GridComputerPlayerHandler(this);
        cutsceneHandler       = new GridCutsceneHandler(this);
        hud                   = new GridHUD(this);
        conditionsHandler     = new GridConditionsHandler(this);
    }

    @Override
    public GridScreen screen() {
        if(WYRMGame.activeScreen() instanceof GridScreen) {
            return (GridScreen) WYRMGame.activeScreen();
        } else {
            Gdx.app.log("GridMetaHandler", "Root active screen is not Grid.");
            throw new IllegalStateException("root screen not parseable.");
        }
    }

    public boolean isBusy() {
        return combat().isBusy()
            || cutscenes().isBusy()
            || actors().isBusy()
            || ai().isBusy()
            || conditions().isBusy()
            || input().isBusy()
            || map().isBusy()
            || hud().isBusy();
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }
}
