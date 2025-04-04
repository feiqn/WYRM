package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.FieldActionsPopup;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.*;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import org.jetbrains.annotations.NotNull;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class WyrMap {
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

    // --BOOLS--
    private boolean busy;

    // --ARRAYS--
    protected LogicalTile[][] internalLogicalMap; // Be mindful of limitations of standard Array datatype in Java.

    // --INTS--
    private final int tilesWide,
                      tilesHigh;

    public WyrMap(WYRMGame game) {
        this(game, 10, 10);
    }

    public WyrMap(WYRMGame game, int sizeRoot) {
        this(game, sizeRoot, sizeRoot);
    }

    public WyrMap(WYRMGame game, TiledMapTileLayer groundLayer) {
        this(game, groundLayer.getWidth(), groundLayer.getHeight());
    }

    public WyrMap(WYRMGame game, int tilesWide, int tilesHigh) {
        this.game = game;
        this.tilesHigh = tilesHigh;
        this.tilesWide = tilesWide;

        internalLogicalMap = new LogicalTile[tilesHigh][];
        busy = false;

        for(int h = 0; h < tilesHigh; h++) {
            internalLogicalMap[h] = new LogicalTile[tilesWide];
            for(int w = 0; w < tilesWide; w++) {
                internalLogicalMap[h][w] = new PlainsTile(game, w, h);
            }
        }
        setUpTiles();
        setUpUnits();
    }

    /** Empty classes for children to
     *  work with via @Override
     */
    public void setUpUnits() {
        // for Override by child
    }

    protected void setUpTiles() {
        // for Override by child
        // TODO: eventually can probably figure something automated out, if we figure out how to use Tiled properties
    }
    /** end empty classes
     */

    // --MOVERS--
    public void moveAlongPath(SimpleUnit unit, Path path) {
        final RunnableAction blank = new RunnableAction();
        blank.setRunnable(new Runnable() {
            @Override
            public void run() {
                // :)
            }
        });
        moveAlongPath(unit, path, blank);
    }

    // TODO: same thing for MapObjects
    public void moveAlongPath(SimpleUnit unit, Path path, RunnableAction extraCode) {
        busy = true;
//        Gdx.app.log("moveAlong", "path length: " + path.size() + " unit speed: " + unit.modifiedSimpleSpeed());

        final int originRow = unit.getRow();
        final int originColumn = unit.getColumn();

        final SequenceAction movementSequence = new SequenceAction();

        for(LogicalTile tile : path.retrievePath()) {
            final MoveToAction move = new MoveToAction();
            move.setPosition(tile.getCoordinates().x, tile.getCoordinates().y);
            move.setDuration(.1f);
            movementSequence.addAction(move);
        }

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                placeUnitAtPosition(unit, path.lastTile().getRow(), path.lastTile().getColumn());

                if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {
                    final FieldActionsPopup fap = new FieldActionsPopup(game, unit, originRow, originColumn);
                    game.activeGridScreen.hud().addPopup(fap);
                } else {
                    unit.setCannotMove();
                    game.activeGridScreen.checkLineOrder(); // TODO: make sure ai doesn't run during cutscenes
                }

            }
        });

        final RunnableAction unfinishedBusiness = new RunnableAction();
        unfinishedBusiness.setRunnable(new Runnable() {
            @Override
            public void run() {
                busy = false;
//                Gdx.app.log("move along path", "I'm done.");
            }
        });

        unit.addAction(sequence(movementSequence, finishMoving, extraCode, unfinishedBusiness));
    }

    // --PLACERS--
    public void placeUnitAtPosition(SimpleUnit unit, int row, int column) {
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
    public void placeUnitAdjacentToTile(SimpleUnit unit, LogicalTile tile) {
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
    public void placeUnitAtPosition(SimpleUnit unit, Vector2 vector) {
        placeUnitAtPosition(unit, (int)vector.y, (int)vector.x);
    }

    // --SETTERS--
    protected void setLogicalTilesToType(Array<LogicalTile> tiles, LogicalTileType type) {
        for(LogicalTile tile : tiles) {
            final Vector2 pos = new Vector2(tile.getCoordinates().y, tile.getCoordinates().x); // Don't listen to IntelliJ, this is correct. I am sorry.

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

    public void debugShowAllTilesOfType(LogicalTileType type) {
        for(LogicalTile[] a : internalLogicalMap) {
            for(LogicalTile tile : a) {
                if(tile.tileType == type) {

                    tile.highlight();
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

    // --GETTERS--

    // --CALCULATORS--
    public int distanceBetweenTiles(@NotNull LogicalTile originTile, @NotNull LogicalTile destinationTile) {

        int yDistance;
        if(originTile.getRow() > destinationTile.getRow()) {
            yDistance = originTile.getRow() - destinationTile.getRow();
        } else {
            yDistance = destinationTile.getRow() - originTile.getRow();
        }

        int xDistance;
        if(originTile.getColumn() > destinationTile.getColumn()) {
            xDistance = originTile.getColumn() - destinationTile.getColumn();
        } else {
            xDistance = destinationTile.getColumn() - originTile.getColumn();
        }
//        Gdx.app.log("distance between", "" + (yDistance + xDistance));
        return yDistance + xDistance;
    }
    // todo: wrapper methods for nextTile via vector2 parameter
    public LogicalTile nextTileUpFrom(LogicalTile tile) { return  nextTileNorthFrom(tile); }
    public LogicalTile nextTileNorthFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newY = (int)xy.y + 1;
        final Vector2 next = new Vector2(newY, (int)xy.x);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileDownFrom(LogicalTile tile) { return nextTileSouthFrom(tile);}
    public LogicalTile nextTileSouthFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newY = (int)xy.y - 1;
        final Vector2 next = new Vector2(newY, (int)xy.x);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileLeftFrom(LogicalTile tile) { return nextTileWestFrom(tile);}
    public LogicalTile nextTileWestFrom(LogicalTile tile) {
        final Vector2 xy = tile.getCoordinates();
        final int newX = (int)xy.x - 1;
        final Vector2 next = new Vector2((int)xy.y, newX);
        return getTileAtPosition(next);
    }
    public LogicalTile nextTileRightFrom(LogicalTile tile) { return nextTileEastFrom(tile);}
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
    public boolean isBusy() { return busy; }
    public LogicalTile getTileAtPosition(int row, int column) {
        return internalLogicalMap[row][column];
    }
    public LogicalTile getTileAtPosition(Vector2 pos) {
        return internalLogicalMap[(int)pos.x][(int)pos.y];
    }
    private Array<LogicalTile> tilesWithinDistanceOfOrigin(LogicalTile origin, int distance) {
        Array<LogicalTile> tilesInRange = new Array<>();

        for(LogicalTile[] tileArray : internalLogicalMap) {
            for(LogicalTile tile : tileArray) {
                if(distanceBetweenTiles(origin, tile) <= distance) {
                    tilesInRange.add(tile);
                }
            }
        }

        return tilesInRange;
    }
}
