package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld;

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
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

import static com.badlogic.gdx.Gdx.input;

public abstract class RPGridScreen extends WyrScreen {

    // TODO:
    //  Consider puling out more generic "grid" components into some sort of
    //  intermediary subclass. idk, the rebrand has me thinking.

    // Full refactor of GridScreen.
    // Aw, shit.

    protected OrthogonalTiledMapRenderer mapRenderer;

    protected Stage gameStage;
    protected Stage hudStage;

    protected RPGridMetaHandler h; // It's fun to just type "h".


    public RPGridScreen(TiledMap tiledMap) {

        h = new RPGridMetaHandler(tiledMap);

        mapRenderer = new OrthogonalTiledMapRenderer(h.map().getTiledMap(), Wyr.WORLD_SCALE);
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

        h.camera().actual().setToOrtho(false, worldWidth, worldHeight);

        gameStage = new Stage(new ExtendViewport(worldWidth, worldHeight, h.camera().actual()));

        h.camera().actual().zoom = Math.max(0.3f, Math.min(h.camera().actual().zoom, Math.max(worldWidth / h.camera().actual().viewportWidth, worldHeight / h.camera().actual().viewportHeight)));
        h.camera().actual().update();

        gameStage.addActor(h.camera());
        h.camera().setPosition(worldWidth / 2, worldHeight / 2);

        hudStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        hudStage.addActor(h.hud());


        final InputMultiplexer multiplexer = new InputMultiplexer();
        // TODO:
        //  keyboard controls
        //  drag listener (oldGrid_show)
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(gameStage);
        multiplexer.addProcessor(RPGridInputHandler.Listeners.MAP_scroll(h));
        input.setInputProcessor(multiplexer);

        gameStage.addListener(RPGridInputHandler.Listeners.MAP_drag(h, this));


        // TODO: Next,
        //  - hud init
        //  - fade in from black

        setup();

    }

    @Override
    public void render (float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.15f, 0.5f, 0.25f, 1);

        h.camera().actual().update();
        h.time().increment(delta);

        mapRenderer.setView(h.camera().actual());
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

    protected void instantiateUnit(RPGridUnit unit, int x, int y) {
        h.map().placeActor(unit, x, y);
        h.register().declareUnit(unit);
        gameStage.addActor(unit);

        switch(unit.getTeamAlignment()) {
            case PLAYER:
                unit.addListener(RPGridInputHandler.Listeners.UNIT_playerLeftClick(h, unit));
                break;

            case ENEMY:
                unit.addListener(RPGridInputHandler.Listeners.UNIT_enemyLeftClick(h, unit));
                break;

            case ALLY:
            case STRANGER:

            default:
                break;
        }

    }

    protected void instantiateProp(RPGridProp prop) {

    }

    /**
     * This should build units, props, victory conditions,
     * cutscenes, and anything else relevant to the game level.
     */
    protected abstract void setup();

    /**
     * Behavior for when the level is won.
     */
    protected abstract void win();

    /**
     * Getter methods
     */
    public RPGridMetaHandler h() { return h; }
    public Stage getGameStage() { return gameStage; }
    public Stage getHudStage() { return hudStage; }
    @Override
    public WyrType getWyrType() { return WyrType.RPGRID; }
}
