package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD_;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class WyrGridScreen extends WyrScreen {

    // full refactor of GridScreen.
    // first need to refact WyrMap

    protected GridInputHandler inputHandler;
    protected WyrGrid gridMap;
    protected WyrHUD_ HUD;
    protected CameraMan cameraMan;
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected Stage gameStage;
    protected Stage hudStage;


    public WyrGridScreen(WYRMGame game) {
        super(game, Type.GRID);
    }




}
