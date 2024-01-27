package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.*;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;
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
     * 0, 0 = bottom left tile.
     */

    protected final WYRMGame game;

    // --ARRAYS--
    public LogicalTile[][] internalLogicalMap; // Be mindful of limitations of standard Array datatype in Java.

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
        internalLogicalMap = new LogicalTile[tilesHigh][];

        for(int h = 0; h < tilesHigh; h++) {
            internalLogicalMap[h] = new LogicalTile[tilesWide];
            for(int w = 0; w < tilesWide; w++) {
                internalLogicalMap[h][w] = new PlainsTile(game, w, h);
            }
        }

    }

    public void setUpUnits() {

    }

    public LogicalTile getTileAtPosition(int row, int column) {
        return internalLogicalMap[row][column];
    }
    public LogicalTile getTileAtPosition(Vector2 pos) {
        return internalLogicalMap[(int)pos.x][(int)pos.y];
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

        internalLogicalMap[unit.getRow()][unit.getColumn()].occupyingUnit = null; // clear the old tile
        internalLogicalMap[unit.getRow()][unit.getColumn()].isOccupied = false;

        internalLogicalMap[row][column].occupyingUnit = unit;
        internalLogicalMap[row][column].isOccupied = true;

        unit.setPosition(internalLogicalMap[row][column].coordinates.x, internalLogicalMap[row][column].coordinates.y);
        unit.occupyingTile = internalLogicalMap[row][column];
        unit.setRow(row);
        unit.setColumn(column);

    }

    public void placeMapObjectAtPosition(MapObject object, int row, int column) {
        object.occupyingTile = internalLogicalMap[row][column];
        object.row = row;
        object.column = column;
        object.setPosition(internalLogicalMap[row][column].coordinates.x, internalLogicalMap[row][column].coordinates.y);
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
                internalLogicalMap[xPos][yPos] = new DoorTile(game, yPos, xPos);
                break;
            case LAVA:
                internalLogicalMap[xPos][yPos] = new LavaTile(game, yPos, xPos);
                break;
            case ROAD:
                internalLogicalMap[xPos][yPos] = new RoadTile(game, yPos, xPos);
                break;
            case CHEST:
                internalLogicalMap[xPos][yPos] = new ChestTile(game, yPos, xPos);
                break;
            case FOREST:
                internalLogicalMap[xPos][yPos] = new ForestTile(game, yPos, xPos);
                break;
            case PLAINS:
                internalLogicalMap[xPos][yPos] = new PlainsTile(game, yPos, xPos);
                break;
            case FORTRESS:
                internalLogicalMap[xPos][yPos] = new FortressTile(game, yPos, xPos);
                break;
            case MOUNTAIN:
                internalLogicalMap[xPos][yPos] = new MountainTile(game, yPos, xPos);
                break;
            case CORAL_REEF:
                internalLogicalMap[xPos][yPos] = new CoralReefTile(game, yPos, xPos);
                break;
            case DEEP_WATER:
                internalLogicalMap[xPos][yPos] = new DeepWaterTile(game, yPos, xPos);
                break;
            case ROUGH_HILLS:
                internalLogicalMap[xPos][yPos] = new RoughHillsTile(game, yPos, xPos);
                break;
            case SHALLOW_WATER:
                internalLogicalMap[xPos][yPos] = new ShallowWaterTile(game, yPos, xPos);
                break;
            case BREAKABLE_WALL:
                internalLogicalMap[xPos][yPos] = new BreakableWallTile(game, yPos, xPos);
                break;
            case IMPASSIBLE_WALL:
                internalLogicalMap[xPos][yPos] = new ImpassibleWallTile(game, yPos, xPos);
                break;
            case LOW_WALL:
                internalLogicalMap[xPos][yPos] = new LowWallTile(game, yPos, xPos);
                break;
//            case OBJECTIVE_SEIZE:
//            case OBJECTIVE_ESCAPE:
//            case OBJECTIVE_DESTROY:
            default:
                break;
        }

    }

    protected void debugShowAllTilesOfType(LogicalTileType type) {
        for(LogicalTile[] a : internalLogicalMap) {
            for(LogicalTile tile : a) {
                if(tile.tileType == type) {

                    tile.setDebug(true);

//                    final Texture debugCharTexture = new Texture(Gdx.files.internal("test/test_character.png"));
//                    final TextureRegion debugCharRegion = new TextureRegion(debugCharTexture,0,0,128,160);
//
//                    final Image highlightImage = new Image(debugCharRegion);
//                    highlightImage.setSize(1,1);
//                    highlightImage.setPosition(tile.coordinates.x, tile.coordinates.y);
//                    highlightImage.setColor(.5f,.5f,.5f,.5f);
//                    game.activeBattleScreen.gameStage.addActor(highlightImage);
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
    public Array<LogicalTile> getTiles() {
        final Array<LogicalTile> tilesAsArray = new Array<>();
        for(LogicalTile[] a : internalLogicalMap) {
            for(LogicalTile tile : a) {
                tilesAsArray.add(tile);
            }
        }
        return tilesAsArray;
    }
}