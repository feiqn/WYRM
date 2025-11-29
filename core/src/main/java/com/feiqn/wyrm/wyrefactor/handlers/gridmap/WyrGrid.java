package com.feiqn.wyrm.wyrefactor.handlers.gridmap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.handlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles.LogicalTileType;

public abstract class WyrGrid {

    // refactor of WyrMap

//    private final WYRMGame root;

    private final TiledMap tiledMap;

    private final GridActorHandler actorHandler;

    private GridTile[][] logicalMap;

//    private boolean busy = false;

    private final int tilesWide;
    private final int tilesHigh;

    /*
    - grid combat handler
    - computer player handler
     */

    public WyrGrid(WYRMGame game, TiledMap tiledMap) {
        this.tiledMap = tiledMap;

        this.actorHandler = new GridActorHandler(game);

        final TiledMapTileLayer ground = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tilesWide = ground.getWidth();
        tilesHigh = ground.getHeight();

//        tilesWide = tiledMap.getProperties().get("tilewidth", Integer.class);
//        tilesHigh = tiledMap.getProperties().get("tileheight", Integer.class);

        logicalMap = new GridTile[tilesWide][];

        for(int y = 0; y < tilesHigh; y++) {
            logicalMap[y] = new GridTile[tilesWide];
            for(int x = 0; x < tilesWide; x++) {
                logicalMap[x][y] = new GridTile(game, GridTile.Type.PLAINS, x, y);
            }
        }

        setUpTiles();
//        setUpUnits(); TODO: this might go in actor handle
    }

//    protected abstract void setUpUnits();

    protected void setUpTiles() {
        if(tiledMap == null) return;

        TiledMapTileLayer roadLayer = null;
        TiledMapTileLayer impassibleLayer = null;
        TiledMapTileLayer forestLayer = null;
        TiledMapTileLayer lowWallLayer = null;
        TiledMapTileLayer mountainLayer = null;
        TiledMapTileLayer roughHillsLayer = null;
        TiledMapTileLayer fortressLayer = null;
        TiledMapTileLayer shallowWaterLayer = null;

        try {
            roadLayer = (TiledMapTileLayer)tiledMap.getLayers().get("road tiles");
        } catch(Exception ignored) {}

        try {
            impassibleLayer = (TiledMapTileLayer)tiledMap.getLayers().get("impassible walls");
        } catch(Exception ignored) {}

        try {
            forestLayer = (TiledMapTileLayer)tiledMap.getLayers().get("forest tiles");
        } catch(Exception ignored) {}

        try {
            lowWallLayer = (TiledMapTileLayer)tiledMap.getLayers().get("low walls");
        } catch(Exception ignored) {}

        try {
            mountainLayer = (TiledMapTileLayer)tiledMap.getLayers().get("mountain tiles");
        } catch(Exception ignored) {}

        try {
            roughHillsLayer = (TiledMapTileLayer)tiledMap.getLayers().get("rough hill tiles");
        } catch(Exception ignored) {}

        try {
            fortressLayer = (TiledMapTileLayer)tiledMap.getLayers().get("fortress tiles");
        } catch(Exception ignored) {}

        try {
            shallowWaterLayer = (TiledMapTileLayer)tiledMap.getLayers().get("shallow water tiles");
        } catch(Exception ignored) {}


        for(int y = 0; y < tilesHigh; y++) {
            for(int x = 0; x < tilesWide; x++) {
                TiledMapTileLayer.Cell cell;

                if(roadLayer != null) {
                    cell = roadLayer.getCell(x, y);
                    if(cell != null && cell.getTile().getId() != 0) {
                        // tile is flagged for this layer
                        setTileToType(x, y, GridTile.Type.ROAD);
                        continue;
                    }
                }

                if(impassibleLayer != null) {
                    cell = impassibleLayer.getCell(x,y);
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(x, y, GridTile.Type.IMPASSIBLE_WALL);
                        continue;
                    }
                }

                if(forestLayer != null) {
                    cell = forestLayer.getCell(x,y);
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(x, y, GridTile.Type.FOREST);
                        continue;
                    }
                }

                if(lowWallLayer != null) {
                    cell = lowWallLayer.getCell(x,y);
                    if(cell != null && cell.getTile().getId() != 0) {
                        setTileToType(x, y, GridTile.Type.LOW_WALL);
                    }
                }

                if(mountainLayer != null) {

                }

                if(roughHillsLayer != null) {

                }

                if(shallowWaterLayer != null) {

                }

                if(fortressLayer != null) {

                }

            }
        }


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


    private void setTileToType(int x, int y, GridTile.Type type) {

    }

    public GridActorHandler getActorHandler() { return actorHandler; }
}
