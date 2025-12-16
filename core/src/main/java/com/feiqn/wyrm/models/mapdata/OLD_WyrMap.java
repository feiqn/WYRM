package com.feiqn.wyrm.models.mapdata;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.FieldActionsPopup;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.*;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import org.jetbrains.annotations.NotNull;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public abstract class OLD_WyrMap {
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
    protected OLD_LogicalTile[][] internalLogicalMap; // Be mindful of limitations of standard Array datatype in Java.

    // --INTS--
    private final int tilesWide,
                      tilesHigh;

    public OLD_WyrMap(WYRMGame game) {
        this(game, 10, 10);
    }

    public OLD_WyrMap(WYRMGame game, int sizeRoot) {
        this(game, sizeRoot, sizeRoot);
    }

    public OLD_WyrMap(WYRMGame game, TiledMapTileLayer groundLayer) {
        this(game, groundLayer.getWidth(), groundLayer.getHeight());
    }

    public OLD_WyrMap(WYRMGame game, int columnsXWide, int rowsYTall) {
        this.game = game;
        this.tilesHigh = rowsYTall;
        this.tilesWide = columnsXWide;

        internalLogicalMap = new OLD_LogicalTile[rowsYTall][];
        busy = false;

        for(int h = 0; h < rowsYTall; h++) {
            internalLogicalMap[h] = new OLD_LogicalTile[columnsXWide];
            for(int w = 0; w < columnsXWide; w++) {
                internalLogicalMap[h][w] = new PlainsTileOLD(game, w, h);
            }
        }
//        setUpTiles();
//        setUpUnits();
    }

    protected abstract void setUpUnits();

    protected abstract void setUpTiles();

    // --MOVERS--
    public void moveAlongPath(OLD_SimpleUnit unit, OLD_Path OLDPath) {
        final RunnableAction blank = new RunnableAction();
        blank.setRunnable(new Runnable() {
            @Override
            public void run() {
                // Tell your mom I said "Hi" :)
            }
        });
        moveAlongPath(unit, OLDPath, blank, false);
    }

    // TODO: same thing for MapObjects
    public void moveAlongPath(OLD_SimpleUnit unit, OLD_Path OLDPath, RunnableAction extraCode, boolean combatAfter) {
        busy = true;

        final int originRow = unit.getRowY();
        final int originColumn = unit.getColumnX();

        final SequenceAction movementSequence = new SequenceAction();

        Direction nextDirection = null;

        for(int i = 1; i < OLDPath.size(); i++) {

            switch(directionFromTileToTile(OLDPath.retrievePath().get(i-1), OLDPath.retrievePath().get(i))) {
                case NORTH:
                    nextDirection = Direction.NORTH;
                    break;
                case SOUTH:
                    nextDirection = Direction.SOUTH;
                    break;
                case EAST:
                    nextDirection = Direction.EAST;
                    break;
                case WEST:
                    nextDirection = Direction.WEST;
                    break;
            }

            final RunnableAction changeDirection = new RunnableAction();
            final Direction decidedNextDirection = nextDirection;
            int thisI = i;
            changeDirection.setRunnable(new Runnable() {
                @Override
                public void run() {
                    switch(decidedNextDirection) {
                        case NORTH:
                            unit.faceNorth();
                            break;
                        case SOUTH:
                            unit.faceSouth();
                            break;
                        case WEST:
                            unit.faceWest();
                            break;
                        case EAST:
                            unit.faceEast();
                            break;
                    }

                    if(unit.rosterID == UnitRoster.LEIF_MOUNTED && thisI == 1) {
                        unit.setPosition(unit.getColumnX() - .5f, unit.getRowY());
                    }
                }

            });

            movementSequence.addAction(changeDirection);

            final MoveToAction move = new MoveToAction();
            if(unit.rosterID == UnitRoster.LEIF_MOUNTED) {
                move.setPosition(OLDPath.retrievePath().get(i).getCoordinatesXY().x - .5f, OLDPath.retrievePath().get(i).getCoordinatesXY().y);
            } else {
                move.setPosition(OLDPath.retrievePath().get(i).getCoordinatesXY().x, OLDPath.retrievePath().get(i).getCoordinatesXY().y);
            }
            move.setDuration(.15f);
            movementSequence.addAction(move);
        }

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                placeUnitAtPositionROWCOLUMN(unit, OLDPath.lastTile().getRowY(), OLDPath.lastTile().getColumnX());

                if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {
                    final FieldActionsPopup fap = new FieldActionsPopup(game, unit, originRow, originColumn);
                    game.activeOLDGridScreen.setInputMode(OLD_GridScreen.OLD_InputMode.MENU_FOCUSED);
                    game.activeOLDGridScreen.hud().addPopup(fap);
                } else if(!combatAfter){
                    unit.idle();
                    unit.setCannotMove();
                    game.activeOLDGridScreen.finishExecutingAction();
//                    game.activeGridScreen.checkLineOrder();
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

    public OLD_Path pathToNearestNeighborInRange(OLD_SimpleUnit toFindPathFor, OLD_LogicalTile destination) {
        int shortestDistance = Integer.MAX_VALUE;
        OLD_LogicalTile pathTarget = toFindPathFor.getOccupyingTile();
        for(OLD_LogicalTile tile : game.activeOLDGridScreen.reachableTiles) {
            int distance = distanceBetweenTiles(destination, toFindPathFor.getOccupyingTile());
            if(distanceBetweenTiles(tile, destination) <= toFindPathFor.getSimpleReach()
               && distance < shortestDistance) {
                pathTarget = tile;
                shortestDistance = distance;
            }

        }
        return game.activeOLDGridScreen.getRecursionHandler().shortestPath(toFindPathFor, pathTarget, true, false);
    }

    public Direction directionFromTileToTile(OLD_LogicalTile origin, OLD_LogicalTile destination) {
        // Nobody cares about inter-cardinals.
        if(origin.getColumnX() == destination.getColumnX()) {
            if(origin.getRowY() > destination.getRowY()) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }
        } else {
            if(origin.getColumnX() > destination.getColumnX()) {
                return Direction.WEST;
            } else {
                return Direction.EAST;
            }
        }
    }

    // --PLACERS--
    public void placeUnitAtPositionXY(OLD_SimpleUnit unit, int columnX, int rowY) {
        this.placeUnitAtPositionROWCOLUMN(unit,rowY,columnX);
    }
    private void placeUnitAtPositionROWCOLUMN(OLD_SimpleUnit unit, int rowY, int columnX) {
        internalLogicalMap[unit.getRowY()][unit.getColumnX()].setUnoccupied(); // clear the old tile

        internalLogicalMap[rowY][columnX].occupy(unit);

        if(unit.isWide()) {
            unit.setPosition(columnX - .5f, rowY);
        } else {
            unit.setPosition(columnX, rowY);
        }

        unit.occupyTile(internalLogicalMap[rowY][columnX]);
        unit.setRow(rowY);
        unit.setColumn(columnX);

        if(unit.getTeamAlignment() != TeamAlignment.PLAYER) { // FAP handles this for players on wait()
            game.activeOLDGridScreen.conditions().conversations().checkAreaTriggers(unit.rosterID, unit.getTeamAlignment(), new Vector2(columnX, rowY));
        }
    }
    public void placeMapObjectAtPosition(MapObject object, int columnX, int rowY) {
        object.occupyingTile = internalLogicalMap[rowY][columnX];
        internalLogicalMap[rowY][columnX].setProp(object);
        object.row = rowY;
        object.column = columnX;
        object.setPosition(internalLogicalMap[rowY][columnX].getCoordinatesXY().x, internalLogicalMap[rowY][columnX].getCoordinatesXY().y);
    }
    public void placeUnitAdjacentToTile(OLD_SimpleUnit unit, OLD_LogicalTile tile) {
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
    protected void setLogicalTilesToTypeXY(Array<OLD_LogicalTile> tiles, LogicalTileType type) {
        for(OLD_LogicalTile tile : tiles) {
            setLogicalTileToTypeXY(tile.getColumnX(), tile.getRowY(), type);
        }
    }
    protected void setLogicalTileToTypeXY(int columnXRight, int rowYUp, LogicalTileType newType) {
        setLogicalTileToTypeYX(rowYUp, columnXRight, newType);
    }
    private void setLogicalTileToTypeYX(int up, int right, LogicalTileType newType) {

        switch(newType) {
            case DOOR:
                internalLogicalMap[up][right] = new DoorTileOLD(game, right, up);
                break;
            case LAVA:
                internalLogicalMap[up][right] = new LavaTileOLD(game, right, up);
                break;
            case ROAD:
                internalLogicalMap[up][right] = new RoadTileOLD(game, right, up);
                break;
            case CHEST:
                internalLogicalMap[up][right] = new ChestTileOLD(game, right, up);
                break;
            case FOREST:
                internalLogicalMap[up][right] = new ForestTileOLD(game, right, up);
                break;
            case PLAINS:
                internalLogicalMap[up][right] = new PlainsTileOLD(game, right, up);
                break;
            case FORTRESS:
                internalLogicalMap[up][right] = new FortressTileOLD(game, right, up);
                break;
            case MOUNTAIN:
                internalLogicalMap[up][right] = new MountainTileOLD(game, right, up);
                break;
            case CORAL_REEF:
                internalLogicalMap[up][right] = new CoralReefTileOLD(game, right, up);
                break;
            case DEEP_WATER:
                internalLogicalMap[up][right] = new DeepWaterTileOLD(game, right, up);
                break;
            case ROUGH_HILLS:
                internalLogicalMap[up][right] = new RoughHillsTileOLD(game, right, up);
                break;
            case SHALLOW_WATER:
                internalLogicalMap[up][right] = new ShallowWaterTileOLD(game, right, up);
                break;
            case BREAKABLE_WALL:
                internalLogicalMap[up][right] = new BreakableWallTileOLD(game, right, up);
                break;
            case IMPASSIBLE_WALL:
                internalLogicalMap[up][right] = new ImpassibleWallTileOLD(game, right, up);
                break;
            case LOW_WALL:
                internalLogicalMap[up][right] = new LowWallTileOLD(game, right, up);
                break;
//            case OBJECTIVE_SEIZE:
            case OBJECTIVE_ESCAPE:
                internalLogicalMap[up][right] = new ObjectiveEscapeTileOLD(game, right, up, UnitRoster.LEIF);
                break;
//            case OBJECTIVE_DESTROY:
            default:
                break;
        }

    }

    public void debugShowAllTilesOfType(LogicalTileType type) {
        for(OLD_LogicalTile[] a : internalLogicalMap) {
            for(OLD_LogicalTile tile : a) {
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
    // TODO: nearestUnoccupiedNeighbor(LogicalTile tile) {}

    // --CALCULATORS--
    public int distanceBetweenTiles(@NotNull OLD_LogicalTile originTile, @NotNull OLD_LogicalTile destinationTile) {

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

    public OLD_LogicalTile nextTileUpFrom(OLD_LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX(), tile.getRowY() + 1);
    }
    public OLD_LogicalTile nextTileDownFrom(OLD_LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX(), tile.getRowY() - 1);
    }
    public OLD_LogicalTile nextTileLeftFrom(OLD_LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX() - 1, tile.getRowY());
    }
    public OLD_LogicalTile nextTileRightFrom(OLD_LogicalTile tile) {
        return getTileAtPositionXY(tile.getColumnX() + 1, tile.getRowY());
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public int getTilesWide() {
        return tilesWide;
    }

    public Array<OLD_LogicalTile> getTiles() {
        final Array<OLD_LogicalTile> tilesAsArray = new Array<>();
        for(OLD_LogicalTile[] a : internalLogicalMap) {
            for(OLD_LogicalTile tile : a) {
                tilesAsArray.add(tile);
            }
        }
        return tilesAsArray;
    }

    public boolean isBusy() { return busy; }

    public OLD_LogicalTile getTileAtPositionXY(int columnXRight, int rowYUp) {
        return getTileAtPositionROWCOLUMN(rowYUp, columnXRight);
    }

    private OLD_LogicalTile getTileAtPositionROWCOLUMN(int rowYUp, int columnXRight) {
        return internalLogicalMap[rowYUp][columnXRight];
    }

    public Array<OLD_LogicalTile> tilesWithinDistanceOfOrigin(OLD_LogicalTile origin, int distance) {
        Array<OLD_LogicalTile> tilesInRange = new Array<>();

        for(OLD_LogicalTile[] tileArray : internalLogicalMap) {
            for(OLD_LogicalTile tile : tileArray) {
                if(distanceBetweenTiles(origin, tile) <= distance) {
                    tilesInRange.add(tile);
                }
            }
        }

        return tilesInRange;
    }
}
