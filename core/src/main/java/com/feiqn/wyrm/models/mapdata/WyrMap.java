package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.FieldActionsPopup;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.*;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
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

    public WyrMap(WYRMGame game, int columnsXWide, int rowsYTall) {
        this.game = game;
        this.tilesHigh = rowsYTall;
        this.tilesWide = columnsXWide;

        internalLogicalMap = new LogicalTile[rowsYTall][];
        busy = false;

        for(int h = 0; h < rowsYTall; h++) {
            internalLogicalMap[h] = new LogicalTile[columnsXWide];
            for(int w = 0; w < columnsXWide; w++) {
                internalLogicalMap[h][w] = new PlainsTile(game, w, h);
            }
        }
//        setUpTiles();
//        setUpUnits();
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
//        Gdx.app.log("map", "move along path start");
        busy = true;

        final int originRow = unit.getRowY();
        final int originColumn = unit.getColumnX();

        final SequenceAction movementSequence = new SequenceAction();

        for(LogicalTile tile : path.retrievePath()) {
            final MoveToAction move = new MoveToAction();
            move.setPosition(tile.getCoordinatesXY().x, tile.getCoordinatesXY().y);
            move.setDuration(.1f);
            movementSequence.addAction(move);
        }

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                placeUnitAtPositionROWCOLUMN(unit, path.lastTile().getRowY(), path.lastTile().getColumnX());

                if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {
                    final FieldActionsPopup fap = new FieldActionsPopup(game, unit, originRow, originColumn);
                    game.activeGridScreen.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
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
    public void placeUnitAtPositionXY(SimpleUnit unit, int columnX, int rowY) {
        this.placeUnitAtPositionROWCOLUMN(unit,rowY,columnX);
    }
    private void placeUnitAtPositionROWCOLUMN(SimpleUnit unit, int rowY, int columnX) {
        internalLogicalMap[unit.getRowY()][unit.getColumnX()].setUnoccupied(); // clear the old tile

        internalLogicalMap[rowY][columnX].occupy(unit);

//        unit.setPosition(internalLogicalMap[rowY][columnX].getCoordinatesXY().x, internalLogicalMap[rowY][columnX].getCoordinatesXY().y);
        unit.setPosition(columnX, rowY);
        unit.occupyTile(internalLogicalMap[rowY][columnX]);
//        unit.setRow(rowY);
//        unit.setColumn(columnX);
    }
    public void placeMapObjectAtPosition(MapObject object, int row, int column) {
        object.occupyingTile = internalLogicalMap[row][column];
        object.row = row;
        object.column = column;
        object.setPosition(internalLogicalMap[row][column].getCoordinatesXY().x, internalLogicalMap[row][column].getCoordinatesXY().y);
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

    // --SETTERS--
    protected void setLogicalTilesToTypeXY(Array<LogicalTile> tiles, LogicalTileType type) {
        for(LogicalTile tile : tiles) {
            setLogicalTileToTypeXY(tile.getColumnX(), tile.getRowY(), type);
        }
    }
    protected void setLogicalTileToTypeXY(int columnXRight, int rowYUp, LogicalTileType newType) {
        setLogicalTileToTypeYX(rowYUp, columnXRight, newType);
    }
    private void setLogicalTileToTypeYX(int up, int right, LogicalTileType newType) {

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
                internalLogicalMap[up][right] = new ObjectiveEscapeTile(game, right, up, UnitRoster.LEIF);
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
        if(originTile.getRowY() > destinationTile.getRowY()) {
            yDistance = originTile.getRowY() - destinationTile.getRowY();
        } else {
            yDistance = destinationTile.getRowY() - originTile.getRowY();
        }

        int xDistance;
        if(originTile.getColumnX() > destinationTile.getColumnX()) {
            xDistance = originTile.getColumnX() - destinationTile.getColumnX();
        } else {
            xDistance = destinationTile.getColumnX() - originTile.getColumnX();
        }
        return yDistance + xDistance;
    }

    public LogicalTile nextTileUpFrom(LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX(), tile.getRowY() + 1);
    }
    public LogicalTile nextTileDownFrom(LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX(), tile.getRowY() - 1);
    }
    public LogicalTile nextTileLeftFrom(LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX() - 1, tile.getRowY());
    }
    public LogicalTile nextTileRightFrom(LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX() + 1, tile.getRowY());
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

    public LogicalTile getTileAtPositionXY(int columnXRight, int rowYUp) {
        return getTileAtPositionROWCOLUMN(rowYUp, columnXRight);
    }

    private LogicalTile getTileAtPositionROWCOLUMN(int rowYUp, int columnXRight) {
        return internalLogicalMap[rowYUp][columnXRight];
    }

    public Array<LogicalTile> tilesWithinDistanceOfOrigin(LogicalTile origin, int distance) {
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
