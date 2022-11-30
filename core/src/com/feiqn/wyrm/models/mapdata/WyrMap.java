package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.*;
import com.feiqn.wyrm.models.unitdata.Unit;

public class WyrMap extends Actor {
    /*
     * This class forms a logical grid map via nested arrays.
     *
     * When selecting a tile on the map:
     * First select the Vertical Position (row) you want,
     * then select the Horizontal Position (column).
     * I.E., wyrMap.getTile(row, column)
     *
     * ROW refers to how high up (y) a tile is from bottom left,
     * COLUMN refers to right distance (x) from bottom left.
     *
     */

    private final WYRMGame game;

    // --ARRAYS--
    protected LogicalTile[][] logicalMap; // Be mindful of limitations of standard Array datatype in Java.

    // --INTS--
    private final int tilesWide,
                      tilesHigh;

    public WyrMap(WYRMGame game) {
        this.game = game;
        tilesWide = 10;
        tilesHigh = 10;
        SharedInit();
    }

    public WyrMap(WYRMGame game, int sizeRoot) {
        this.game = game;
        tilesHigh = sizeRoot;
        tilesWide = sizeRoot;
        SharedInit();
    }

    public WyrMap(WYRMGame game, int tilesWide, int tilesHigh) {
        this.game = game;
        this.tilesHigh = tilesHigh;
        this.tilesWide = tilesWide;
        SharedInit();
    }

    private void SharedInit() {
        logicalMap = new LogicalTile[tilesHigh][];

        for(int h = 0; h < tilesHigh; h++) {
            logicalMap[h] = new LogicalTile[tilesWide];
            for(int w = 0; w < tilesWide; w++) {
                logicalMap[h][w] = new PlainsTile(game, w, h);
            }
        }

    }

    public LogicalTile getTileAtPosition(int row, int column) {
        return logicalMap[row][column];
    }
    public LogicalTile getTileAtPosition(Vector2 pos) {
        return logicalMap[(int)pos.x][(int)pos.y];
    }

    public void placeUnitAdjacentToTile(Unit unit, LogicalTile tile) {
//                if (reachableTiles.contains(logicalMap.getTileAtPosition(tile.row - 1, tile.column), true)) {
//                    logicalMap.placeUnitAtPosition(unit, tile.row - 1, tile.column);
//                } else if (reachableTiles.contains(logicalMap.getTileAtPosition(tile.row + 1, tile.column), true)) {
//                    logicalMap.placeUnitAtPosition(unit, tile.row + 1, tile.column);
//                } else if (reachableTiles.contains(logicalMap.getTileAtPosition(tile.row, tile.column - 1), true)) {
//                    logicalMap.placeUnitAtPosition(unit, tile.row, tile.column - 1);
//                } else if (reachableTiles.contains(logicalMap.getTileAtPosition(tile.row, tile.column + 1), true)) {
//                    logicalMap.placeUnitAtPosition(unit, tile.row, tile.column + 1);
//                }
    }

    public void placeUnitAtPosition(Unit unit, int row, int column) {

        logicalMap[unit.getRow()][unit.getColumn()].occupyingUnit = null;
        logicalMap[unit.getRow()][unit.getColumn()].isOccupied = false;

        logicalMap[row][column].occupyingUnit = unit;
        logicalMap[row][column].isOccupied = true;

        unit.setPosition(logicalMap[row][column].coordinates.x, logicalMap[row][column].coordinates.y);

        unit.setRow(row);
        unit.setColumn(column);
//        unit.setRow((int)logicalMap[row][column].coordinates.y);
//        unit.setColumn((int)logicalMap[row][column].coordinates.x);
    }

    protected void setLogicalTilesToType(Array<LogicalTile> tiles, LogicalTileType type) {
        for(LogicalTile tile : tiles) {
            final Vector2 pos = new Vector2(tile.coordinates.y, tile.coordinates.x); // Don't listen to IntelliJ, this is correct.

            setLogicalTileToType(pos, type);
        }
    }
    protected void setLogicalTileToType(Vector2 coordinates, LogicalTileType newType){
        this.setLogicalTileToType((int)coordinates.x, (int)coordinates.y, newType);
    }
    protected void setLogicalTileToType(int xPos, int yPos, LogicalTileType newType) {

        switch(newType) {
            case DOOR:
                logicalMap[xPos][yPos] = new DoorTile(game, yPos, xPos);
                break;
            case LAVA:
                logicalMap[xPos][yPos] = new LavaTile(game, yPos, xPos);
                break;
            case ROAD:
                logicalMap[xPos][yPos] = new RoadTile(game, yPos, xPos);
                break;
            case CHEST:
                logicalMap[xPos][yPos] = new ChestTile(game, yPos, xPos);
                break;
            case FOREST:
                logicalMap[xPos][yPos] = new ForestTile(game, yPos, xPos);
                break;
            case PLAINS:
                logicalMap[xPos][yPos] = new PlainsTile(game, yPos, xPos);
                break;
            case FORTRESS:
                logicalMap[xPos][yPos] = new FortressTile(game, yPos, xPos);
                break;
            case MOUNTAIN:
                logicalMap[xPos][yPos] = new MountainTile(game, yPos, xPos);
                break;
            case CORAL_REEF:
                logicalMap[xPos][yPos] = new CoralReefTile(game, yPos, xPos);
                break;
            case DEEP_WATER:
                logicalMap[xPos][yPos] = new DeepWaterTile(game, yPos, xPos);
                break;
            case ROUGH_HILLS:
                logicalMap[xPos][yPos] = new RoughHillsTile(game, yPos, xPos);
                break;
            case SHALLOW_WATER:
                logicalMap[xPos][yPos] = new ShallowWaterTile(game, yPos, xPos);
                break;
            case BREAKABLE_WALL:
                logicalMap[xPos][yPos] = new BreakableWallTile(game, yPos, xPos);
                break;
            case IMPASSIBLE_WALL:
                logicalMap[xPos][yPos] = new ImpassibleWallTile(game, yPos, xPos);
                break;
//            case OBJECTIVE_SEIZE:
//            case OBJECTIVE_ESCAPE:
//            case OBJECTIVE_DESTROY:
            default:
                break;
        }

    }

    protected void debugShowAllTilesOfType(LogicalTileType type) {
        for(LogicalTile[] a : logicalMap) {
            for(LogicalTile tile : a) {
                if(tile.tileType == type) {
                    final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
                    final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);

                    final Image highlightImage = new Image(debugCharRegion);
                    highlightImage.setSize(1,1);
                    highlightImage.setPosition(tile.coordinates.x, tile.coordinates.y);
                    highlightImage.setColor(.5f,.5f,.5f,.5f);
                    game.activeBattleScreen.gameStage.addActor(highlightImage);
                }
            }
        }
    }

    public int getTilesHigh() {
        return tilesHigh;
    }
    public int getTilesWide() {
        return tilesWide;
    }
}