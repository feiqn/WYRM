package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD_;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class WyrGridScreen extends WyrScreen {

    // Full refactor of GridScreen.
    // Aw, shit.

    protected GridInputHandler inputHandler;
    protected GridPathfinder pathfinder;
    protected WyrGrid gridMap;
    protected WyrHUD_ HUD;
    protected CameraMan cameraMan;
//    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected Stage gameStage;
    protected Stage hudStage;

    public WyrGridScreen(WYRMGame game, WyrGrid gridMap) {
        super(game, Type.GRID);
        this.gridMap = gridMap;

        pathfinder = new GridPathfinder(gridMap);
    }

    public GridInputHandler getInputHandler() { return inputHandler; }
    public CameraMan getCameraMan() { return cameraMan; }
    public GridPathfinder getPathfinder() { return pathfinder; }
    public Stage getGameStage() { return gameStage; }
    public Stage getHudStage() { return hudStage; }
//    public TiledMap getTiledMap() { return tiledMap; }
    public WyrGrid getGridMap() { return gridMap; }
}
