package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

import static com.badlogic.gdx.Gdx.input;

public abstract class GridScreen extends WyrScreen {

    // Full refactor of GridScreen.
    // Aw, shit.

    protected OrthogonalTiledMapRenderer mapRenderer;

    protected Stage gameStage;
    protected Stage hudStage;

    protected GridMetaHandler h; // It's fun to just type "h".


    public GridScreen(TiledMap tiledMap) {
        super(WyrType.GRIDWORLD);

        h = new GridMetaHandler(tiledMap);

        mapRenderer = new OrthogonalTiledMapRenderer(h.map().getTiledMap());
    }

    /**
     * To be clear, I still have no idea what I'm doing.
     */
    @Override
    public void show() {
        super.show();

        final MapProperties mapProperties = h.map().getTiledMap().getProperties();
        final int mapWidth = mapProperties.get("width", Integer.class);
        final int mapHeight = mapProperties.get("height", Integer.class);
        final int tileWidth = mapProperties.get("tilewidth", Integer.class);
        final int tileHeight = mapProperties.get("tileheight", Integer.class);

        final float worldWidth = mapWidth * tileWidth / 16f;
        final float worldHeight = mapHeight * tileHeight / 16f;

        h.camera().camera().setToOrtho(false, worldWidth, worldHeight);

        gameStage = new Stage(new ExtendViewport(worldWidth, worldHeight, h.camera().camera()));

        h.camera().camera().zoom = Math.max(0.5f, Math.min(h.camera().camera().zoom, Math.max(worldWidth / h.camera().camera().viewportWidth, worldHeight / h.camera().camera().viewportHeight)));
        h.camera().camera().update();

        gameStage.addActor(h.camera());
        h.camera().setPosition(worldWidth / 2, worldHeight / 2);

        hudStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//        HUD = new GridHUD(root);
        hudStage.addActor(h.hud());


        final InputMultiplexer multiplexer = new InputMultiplexer();
        // TODO:
        //  keyboard controls
        //  drag listener (oldGrid_show)
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(GridInputHandler.GridListeners.mapScrollListener(h));
        input.setInputProcessor(multiplexer);

        // TODO: Next,
        //  - hud init
        //  - fade in from black

        h.conditions().parsePriority();
    }

    @Override
    public void render (float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.15f, 0.5f, 0.25f, 1);

        h.camera().camera().update();
        h.time().increment(delta);

        mapRenderer.setView(h.camera().camera());
        mapRenderer.render();

        gameStage.act();
        gameStage.draw();

        hudStage.act();
        hudStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // remove this line if unexpected behavior
        gameStage.getViewport().update(width, height, false);
        gameStage.getCamera().update();

        hudStage.getViewport().setWorldSize(width, height);
        hudStage.getViewport().update(width, height, true);
        hudStage.getCamera().update();
    }

    // Begin functionality here



    /**
     * Getter methods
     */
    @Override
    public GridMetaHandler handlers() { return h; }
    public Stage getGameStage() { return gameStage; }
    public Stage getHudStage() { return hudStage; }
}
