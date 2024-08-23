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

    /* You fucking idiot. You fucktard. You absolute twat.
     * Why the fuck did you make it like this?
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

        unit.setPosition(internalLogicalMap[row][column].getCoordinates().x, internalLogicalMap[row][column].getCoordinates().y);
        unit.occupyingTile = internalLogicalMap[row][column];
        unit.setRow(row);
        unit.setColumn(column);

    }

    public void placeMapObjectAtPosition(MapObject object, int row, int column) {
        object.occupyingTile = internalLogicalMap[row][column];
        object.row = row;
        object.column = column;
        object.setPosition(internalLogicalMap[row][column].getCoordinates().x, internalLogicalMap[row][column].getCoordinates().y);
    }

    protected void setLogicalTilesToType(Array<LogicalTile> tiles, LogicalTileType type) {
        for(LogicalTile tile : tiles) {
            final Vector2 pos = new Vector2(tile.getCoordinates().y, tile.getCoordinates().x); // Don't listen to IntelliJ, this is correct.

            setLogicalTileToType(pos, type);
        }
    }
    protected void setLogicalTileToType(Vector2 coordinates, LogicalTileType newType){
        this.setLogicalTileToType((int)coordinates.x, (int)coordinates.y, newType);
    }
    protected void setLogicalTileToType(int up, int right, LogicalTileType newType) {

        switch(newType) {
            case DOOR:
                internalLogicalMap[up][right] = new DoorTile(game, right, up);
                break;
            case LAVA:
                internalLogicalMap[up][right] = new LavaTile(game, right, up);
                break;
            case ROAD:
                internalLogicalMap[up][right] = new RoadTile(game, right, up);
                break;
            case CHEST:
                internalLogicalMap[up][right] = new ChestTile(game, right, up);
                break;
            case FOREST:
                internalLogicalMap[up][right] = new ForestTile(game, right, up);
                break;
            case PLAINS:
                internalLogicalMap[up][right] = new PlainsTile(game, right, up);
                break;
            case FORTRESS:
                internalLogicalMap[up][right] = new FortressTile(game, right, up);
                break;
            case MOUNTAIN:
                internalLogicalMap[up][right] = new MountainTile(game, right, up);
                break;
            case CORAL_REEF:
                internalLogicalMap[up][right] = new CoralReefTile(game, right, up);
                break;
            case DEEP_WATER:
                internalLogicalMap[up][right] = new DeepWaterTile(game, right, up);
                break;
            case ROUGH_HILLS:
                internalLogicalMap[up][right] = new RoughHillsTile(game, right, up);
                break;
            case SHALLOW_WATER:
                internalLogicalMap[up][right] = new ShallowWaterTile(game, right, up);
                break;
            case BREAKABLE_WALL:
                internalLogicalMap[up][right] = new BreakableWallTile(game, right, up);
                break;
            case IMPASSIBLE_WALL:
                internalLogicalMap[up][right] = new ImpassibleWallTile(game, right, up);
                break;
            case LOW_WALL:
                internalLogicalMap[up][right] = new LowWallTile(game, right, up);
                break;
//            case OBJECTIVE_SEIZE:
            case OBJECTIVE_ESCAPE:
                internalLogicalMap[up][right] = new ObjectiveEscapeTile(game, right, up);
                break;
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

    // todo: wrapper methods for nextTile via vector2 parameter
    public LogicalTile nextTileNorthFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newY = (int)xy.y + 1;
        final Vector2 next = new Vector2(newY, (int)xy.x);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileSouthFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newY = (int)xy.y - 1;
        final Vector2 next = new Vector2(newY, (int)xy.x);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileWestFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newX = (int)xy.x - 1;
        final Vector2 next = new Vector2((int)xy.y, newX);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileEastFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newX = (int)xy.x + 1;
        final Vector2 next = new Vector2((int)xy.y, newX);
        return getTileAtPosition(next);
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
