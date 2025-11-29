package com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.GridTile;

public abstract class WyrGrid {

    // refactor of WyrMap

    private final WYRMGame root;

    private final TiledMap tiledMap;

    private final GridActorHandler actorHandler;

    private GridTile[][] logicalMap;

    private boolean busy = false;

    private final int tilesWide;
    private final int tilesHigh;

    /*
    - grid combat handler
    - computer player handler
     */

    public WyrGrid(WYRMGame game, TiledMap tiledMap) {
        this.root = game;
        this.tiledMap = tiledMap;

        this.actorHandler = new GridActorHandler(root);

        final TiledMapTileLayer ground = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tilesWide = ground.getWidth();
        tilesHigh = ground.getHeight();

//        tilesWide = tiledMap.getProperties().get("tilewidth", Integer.class);
//        tilesHigh = tiledMap.getProperties().get("tileheight", Integer.class);

        logicalMap = new GridTile[tilesWide][];

        for(int y = 0; y < tilesHigh; y++) {
            logicalMap[y] = new GridTile[tilesWide];
            for(int x = 0; x < tilesWide; x++) {
                logicalMap[x][y] = new GridTile(root, GridTile.Type.PLAINS, x, y);
            }
        }

        setUpTiles();
//        setUpUnits(); TODO: this might go in actor handle
    }

//    protected abstract void setUpUnits();

    protected void setUpTiles() {
        if(tiledMap == null) return;

        TiledMapTileLayer roadLayer       = (TiledMapTileLayer)tiledMap.getLayers().get("road tiles");
        TiledMapTileLayer impassibleLayer = (TiledMapTileLayer)tiledMap.getLayers().get("impassible walls");
        TiledMapTileLayer forestLayer     = (TiledMapTileLayer)tiledMap.getLayers().get("forest tiles");
        TiledMapTileLayer lowWalls        = (TiledMapTileLayer)tiledMap.getLayers().get("low walls");

    }

    // TODO:
    //  let GridActorHandler do
    //  things like placing GridActors
    //  on and moving them around the map,

    // TODO:
    //  set tile to type()
    //  get tile at position()
    //  distance between tiles()
    //  next up down left right from()
    //  tiles within distance of tile()
    //  nearest free neighbor tile()
    //  direction from tile to tile()
    //



    public GridActorHandler getActorHandler() { return actorHandler; }
}
