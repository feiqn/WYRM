package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions.GridConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.GridHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

import static com.badlogic.gdx.Gdx.input;

public abstract class WyrGridScreen extends WyrScreen {

    // Full refactor of GridScreen.
    // Aw, shit.

    protected GridInputHandler inputHandler;
    protected GridPathfinder   pathfinder;

    protected WyrGrid gridMap;
    protected WyrHUD HUD;

    // The cameraman seems fairly agnostic to
    // old vs wyr format. Watching him closely, though.
    protected CameraMan cameraMan = new CameraMan();

    protected OrthogonalTiledMapRenderer mapRenderer;

    protected Stage gameStage;
    protected Stage hudStage;

    public WyrGridScreen(WYRMGame game, WyrGrid gridMap) {
        super(game, WyrType.GRIDWORLD, new GridComputerPlayer(game), new GridComputerPlayerHandler(game), new GridConditionsHandler(game));
        this.gridMap = gridMap;

        pathfinder = new GridPathfinder(gridMap);
        mapRenderer = new OrthogonalTiledMapRenderer(gridMap.getTiledMap());
//        root.handlers().
    }

    /**
     * To be clear, I still have no idea what I'm doing.
     */
    @Override
    public void show() {
        super.show();

        final MapProperties mapProperties = gridMap.getTiledMap().getProperties();
        final int mapWidth = mapProperties.get("width", Integer.class);
        final int mapHeight = mapProperties.get("height", Integer.class);
        final int tileWidth = mapProperties.get("tilewidth", Integer.class);
        final int tileHeight = mapProperties.get("tileheight", Integer.class);

        final float worldWidth = mapWidth * tileWidth / 16f;
        final float worldHeight = mapHeight * tileHeight / 16f;

        cameraMan.camera().setToOrtho(false, worldWidth, worldHeight);

        gameStage = new Stage(new ExtendViewport(worldWidth, worldHeight, cameraMan.camera()));

        cameraMan.camera().zoom = Math.max(0.5f, Math.min(cameraMan.camera().zoom, Math.max(worldWidth / cameraMan.camera().viewportWidth, worldHeight / cameraMan.camera().viewportHeight)));
        cameraMan.camera().update();

        gameStage.addActor(cameraMan);
        cameraMan.setPosition(worldWidth / 2, worldHeight / 2);

        hudStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        HUD = new GridHUD(root);
        hudStage.addActor(HUD);



        final InputMultiplexer multiplexer = new InputMultiplexer();
        // TODO:
        //  keyboard controls
        //  drag listener (oldGrid_show)
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(GridInputHandler.GridListeners.mapScrollListener(root.handlers()));
        input.setInputProcessor(multiplexer);

        // TODO: Next,
        //  - initial line order check to advance from turn 0
        //  - hud init
        //  - fade in from black

    }

    @Override
    public void render (float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.15f, 0.5f, 0.25f, 1);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    public GridInputHandler getInputHandler() { return inputHandler; }
    public CameraMan getCameraMan() { return cameraMan; }
    public GridPathfinder getPathfinder() { return pathfinder; }
    public Stage getGameStage() { return gameStage; }
    public Stage getHudStage() { return hudStage; }
//    public TiledMap getTiledMap() { return tiledMap; }
    public WyrGrid getGridMap() { return gridMap; }
}
