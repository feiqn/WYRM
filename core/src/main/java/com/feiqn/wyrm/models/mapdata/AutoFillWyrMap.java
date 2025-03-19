package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.RoadTile;

public class AutoFillWyrMap extends WyrMap {

    /* I understand how silly this is. Cart and horse and all that.
     * I just didn't understand how robust Tiled is before writing
     * all the code surrounding this. So, here we go.
     * A Class that basically rebuilds the Tiled backend
     * functionality, on top of Tiled.
     */

    private final TiledMap tiledMap;

    public AutoFillWyrMap(WYRMGame game, TiledMap tiledMap) {
        super(game, (TiledMapTileLayer)tiledMap.getProperties().get("base ground plains"));
        this.tiledMap = tiledMap;
    }

    @Override
    protected void setUpTiles() {
        final MapProperties properties = tiledMap.getProperties();

//        TiledMapTileLayer groundLayer     = (TiledMapTileLayer)properties.get("base ground plains");
        TiledMapTileLayer roadLayer       = (TiledMapTileLayer)properties.get("road tiles");
        TiledMapTileLayer impassibleLayer = (TiledMapTileLayer)properties.get("impassible walls");
        TiledMapTileLayer forestLayer     = (TiledMapTileLayer)properties.get("forest tiles");
        TiledMapTileLayer lowWalls        = (TiledMapTileLayer)properties.get("low walls");
        // water, etc

        // TODO: This is bad but I'm drunk and just need to write some code even if its bad.

        for(int row = 0; row < internalLogicalMap.length; row++) {
            for(int column = 0; column < internalLogicalMap[row].length; column++) {
                TiledMapTileLayer.Cell cell = roadLayer.getCell(column, row);
                if(cell.getTile().getId() != 0) {
                    // tile is flagged for this layer
                    setLogicalTileToType(row, column, LogicalTileType.ROAD);
                    break;
                }
                cell = impassibleLayer.getCell(column,row);
                if(cell.getTile().getId() != 0) {
                    // tile is flagged for this layer
                    setLogicalTileToType(row, column, LogicalTileType.IMPASSIBLE_WALL);
                    break;
                }
                cell = forestLayer.getCell(column,row);
                if(cell.getTile().getId() != 0) {
                    // tile is flagged for this layer
                    setLogicalTileToType(row, column, LogicalTileType.FOREST);
                    break;
                }
                cell = lowWalls.getCell(column,row);
                if(cell.getTile().getId() != 0) {
                    // tile is flagged for this layer
                    setLogicalTileToType(row, column, LogicalTileType.LOW_WALL);
                    break;
                }
            }
        }

    }

}
