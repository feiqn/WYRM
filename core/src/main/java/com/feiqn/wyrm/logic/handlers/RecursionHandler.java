package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RecursionHandler {
    // Let's keep it clean and contained.

    private final WYRMGame game;
    private final BattleScreen abs;

    private boolean pathFound;
    private Path shortPath;

    private HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public RecursionHandler(WYRMGame game) {
        this.game = game;
        abs = game.activeBattleScreen;
        pathFound = false;
        tileCheckedAtSpeed = new HashMap<>();
    }

    public void recursivelySelectReachableTiles(@NotNull Unit unit) {
        abs.reachableTiles = new Array<>();
        abs.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getRow(), unit.getColumn(), unit.getBaseMovementSpeed(), unit.getMovementType());
    }

    public void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType) {
        abs.reachableTiles = new Array<>();
        abs.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(startX, startY, moveSpeed, movementType);
    }

    private void internalReachableTileRecursion(int startX, int startY, float moveSpeed, MovementType movementType) {
        /* Called by highlightAllTilesUnitCanReach()
         * Called by AIHandler
         * Called before shortestPath()
         */

        /* Selects all accessible tiles and attackable enemies
         * for movementType within distance moveSpeed of selected tile.
         * TODO: later, this will also select supportable allies.
         *
         * NOTE: This method fills attackableEnemies with hostile
         * units as it comes across them, however it does NOT
         * currently fill hostile units within currentUnit's attack
         * reach at the extremes of it's movement speed.
         */

        if (moveSpeed >= 1) {

            boolean continueUp    = false;
            boolean continueDown  = false;
            boolean continueLeft  = false;
            boolean continueRight = false;

            LogicalTile nextTileLeft  = new LogicalTile(game, -1, -1);
            LogicalTile nextTileRight = new LogicalTile(game, -1, -1);
            LogicalTile nextTileDown  = new LogicalTile(game, -1, -1);
            LogicalTile nextTileUp    = new LogicalTile(game, -1, -1);

            final int newX = startX - 1;
            final Vector2 nextPos = new Vector2(newX, startY);

            if(nextPos.x >= 0) {
                nextTileLeft = abs.logicalMap.getTileAtPosition(nextPos);

                if(!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileLeft, moveSpeed);

                    if(!nextTileLeft.isOccupied) {

                        if(nextTileLeft.isTraversableByUnitType(movementType)) {
                            if(!abs.reachableTiles.contains(nextTileLeft, true)) {
                                abs.reachableTiles.add(nextTileLeft);
                            }
                            continueLeft = true;
                        }

                    } else if(((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueLeft = true;

                    } else if(!abs.attackableUnits.contains(nextTileLeft.occupyingUnit, true)){
                        // TODO: later qol improvement to directly click enemy rather than moving then selecting attack
                        abs.attackableUnits.add(nextTileLeft.occupyingUnit);
//                        nextTileLeft.occupyingUnit.redColor();
//                        nextTileLeft.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newX1 = startX + 1;
            final Vector2 nextPos1 = new Vector2(newX1, startY);

            if(nextPos1.x < abs.logicalMap.getTilesWide()) {
                nextTileRight = abs.logicalMap.getTileAtPosition(nextPos1);

                if(!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileRight, moveSpeed);

                    if(!nextTileRight.isOccupied) {

                        if (nextTileRight.isTraversableByUnitType(movementType)) {
                            if(!abs.reachableTiles.contains(nextTileRight, true)) {
                                abs.reachableTiles.add(nextTileRight);
                            }
                            continueRight = true;

                        }
                    } else if(((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueRight = true;

                    } else if(!abs.attackableUnits.contains(nextTileRight.occupyingUnit, true)){
                        abs.attackableUnits.add(nextTileRight.occupyingUnit);
//                        nextTileRight.occupyingUnit.redColor();
//                        nextTileRight.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newY = startY - 1;
            final Vector2 nextPos2 = new Vector2(startX, newY);

            if (nextPos2.y >= 0) {
                nextTileDown = abs.logicalMap.getTileAtPosition(nextPos2);

                if(!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileDown, moveSpeed);

                    if(!nextTileDown.isOccupied) {

                        if (nextTileDown.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileDown, true)) {
                                abs.reachableTiles.add(nextTileDown);
                            }
                            continueDown = true;

                        }
                    } else if(((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueDown = true;

                    } else if(!abs.attackableUnits.contains(nextTileDown.occupyingUnit, true)){
                        abs.attackableUnits.add(nextTileDown.occupyingUnit);
//                        nextTileDown.occupyingUnit.redColor();
//                        nextTileDown.occupyingUnit.constructAndAddAttackListener(activeUnit);
//                        Gdx.app.log("unit", "i see an enemy");

                    }
                }
            }


            final int newY1 = startY + 1;
            final Vector2 nextPos3 = new Vector2(startX, newY1);

            if (nextPos3.y < abs.logicalMap.getTilesHigh()) {
                nextTileUp = abs.logicalMap.getTileAtPosition(nextPos3);

                if(!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileUp, moveSpeed);

                    if(!nextTileUp.isOccupied) {

                        if (nextTileUp.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileUp, true)) {
                                abs.reachableTiles.add(nextTileUp);
                            }
                            continueUp = true;
                        }

                    } else if(((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueUp = true;

                    } else if(!abs.attackableUnits.contains(nextTileUp.occupyingUnit, true)){
                        abs.attackableUnits.add(nextTileUp.occupyingUnit);
//                        nextTileUp.occupyingUnit.redColor();
//                        nextTileUp.occupyingUnit.constructAndAddAttackListener(activeUnit);
                        Gdx.app.log("unit", "i see an enemy");
                    }
                }
            }


            if(continueUp) {
                internalReachableTileRecursion(startX, newY1, moveSpeed - nextTileUp.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueLeft) {
                internalReachableTileRecursion(newX, startY, moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueDown) {
                internalReachableTileRecursion(startX, newY, moveSpeed - nextTileDown.getMovementCostForMovementType(movementType), movementType);
            }
            if(continueRight) {
                internalReachableTileRecursion(newX1, startY, moveSpeed - nextTileRight.getMovementCostForMovementType(movementType), movementType);
            }
        }
    }

    @NotNull
    @Contract(pure = true)
    public Path shortestPath(@NotNull Unit unit, @NotNull LogicalTile destination) {
        // Returns the shortest path for a given unit to another tile.

        // assume unlimited movement.
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), 100, unit.getMovementType());

        abs.reachableTiles.add(unit.occupyingTile);

//        for(LogicalTile tile : abs.reachableTiles) {
//            tile.highlightCanSupport();
//        }


//        final LogicalTile startingTile = unit.occupyingTile;

//        final Array<LogicalTile> path = new Array<>();
//        Gdx.app.log("Short Path: ", "seeding first path with: " + startingTile.getColumn() + " , " + startingTile.getRow());
//        path.add(startingTile);
//        Gdx.app.log("short path", "new path size: " + path.size);

        pathFound = false;
        shortPath = new Path(game, unit.occupyingTile);
        tileCheckedAtSpeed = new HashMap<>();
//        Gdx.app.log("Short Path: ", "destination tile: " + destination.getRow() + " , " + destination.getColumn());
//        internalShortPathRecursion(path, destination, unit,0);

        Bloom(unit, destination);

        Gdx.app.log("short path", "bloomed");

//        for(LogicalTile tile : abs.reachableTiles) {
//            tile.highlightCanSupport();
//        }
//
//        for(LogicalTile tile : growingPath) {
//            tile.highlightCanSupport();
//        }

        return shortPath;

    }

    private void Bloom(Unit unit, LogicalTile destination) {
        /*
          Create an array of paths,
          loop through the entire array,
          during each loop, take each path in the array and check if all adjacent tiles are either
          accessible or already within path. If Tile is accessible and not within path, create a new
          path containing Tile for each direction, and add to array at beginning of next loop, then
          remove the paths that were just iterated upon and not within reach of destination.
         */

        final Array<Path> paths = new Array<>();
        paths.add(new Path(game, unit.occupyingTile));

        do {

            for(Path path : paths) { // TODO: thinking this doesn't update for dynamic add / remove?

                boolean pathHasGrown = false;

                if(path.lastTile().getColumn() - 1 >= 0) {
                    LogicalTile nextTileLeft = abs.logicalMap.nextTileWestFrom(path.lastTile());
                    if(abs.reachableTiles.contains(nextTileLeft, true)) {
                        if(!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileLeft, (float) path.size());
                            if(!path.contains(nextTileLeft)) {
                                path.incorporateNextTile(Direction.LEFT);
                                pathHasGrown = true;
                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if(path.lastTile().getColumn() + 1 < abs.logicalMap.getTilesWide()) {
                    LogicalTile nextTileRight = abs.logicalMap.nextTileEastFrom(path.lastTile());
                    if(abs.reachableTiles.contains(nextTileRight, true)) {
                        if(!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileRight, (float) path.size());
                            if(!path.contains(nextTileRight)) {
                                if(!pathHasGrown) {
                                    path.incorporateNextTile(Direction.RIGHT);
                                    pathHasGrown = true;
                                } else {
                                    final Path branchingPath = new Path(path);
                                    branchingPath.incorporateNextTile(Direction.RIGHT);
                                    paths.add(branchingPath);
                                }
                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if(path.lastTile().getRow() - 1 >= 0) {
                    LogicalTile nextTileDown = abs.logicalMap.nextTileSouthFrom(path.lastTile());
                    if(abs.reachableTiles.contains(nextTileDown, true)) {
                        if(!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileDown, (float) path.size());
                            if(!path.contains(nextTileDown)) {
                                if(!pathHasGrown) {
                                    path.incorporateNextTile(Direction.DOWN);
                                    pathHasGrown = true;
                                } else {
                                    final Path branchingPath = new Path(path);
                                    branchingPath.incorporateNextTile(Direction.DOWN);
                                    paths.add(branchingPath);
                                }
                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if(path.lastTile().getRow() + 1 < abs.logicalMap.getTilesHigh()) {
                    LogicalTile nextTileUp = abs.logicalMap.nextTileNorthFrom(path.lastTile());
                    if(abs.reachableTiles.contains(nextTileUp, true)) {
                        if(!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileUp, (float) path.size());
                            if(!path.contains(nextTileUp)) {
                                if(!pathHasGrown) {
                                    path.incorporateNextTile(Direction.UP);
                                    pathHasGrown = true;
                                } else {
                                    final Path branchingPath = new Path(path);
                                    branchingPath.incorporateNextTile(Direction.UP);
                                    paths.add(branchingPath);
                                }
                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if(!pathHasGrown) paths.removeValue(path, true);

            }

        } while(!containsTileInReachOf(paths, destination, unit.getReach()));

    }

    private boolean containsTileInReachOf(@NotNull Array<Path> paths, LogicalTile destination, int reach) {


        // this looks messy i think need fix it up pls thx

        for(Path path : paths) {
            if(!pathFound) {
                for (LogicalTile tile : path.retrievePath()) {
                    if (abs.distanceBetweenTiles(tile, destination) <= reach) {
                        shortPath = path;
                        pathFound = true;
                        break;
                    }
                }
            }
        }

        return pathFound;

    }

//    private void internalShortPathRecursion(Array<LogicalTile> path, LogicalTile destination, Unit unit, int steps) {
//        /*
//         * start with last tile in path
//         *
//         * simultaneously expand to all accessible surrounding tiles (up to 4)
//         * that aren't already part of the path, creating new paths for each
//         *
//         * check if any of the tiles expanded to are the destination
//         *
//         * if not, recurse self with each new path
//         */
//
//        // TODO: Sort of works now. It returns A path. Definitely not the Shortest Path.
//        // TODO: The path is broken as hell. Something is completely wrong here.
//
//        if(!pathFound) {
//            final LogicalTile lastTileInPath = path.get(path.size - 1);
//
////            Gdx.app.log("ISPR: ", "lastTileInPath: " + lastTileInPath.getRow() + " , " + lastTileInPath.getColumn());
//
//            boolean continueUp = false;
//            boolean continueDown = false;
//            boolean continueLeft = false;
//            boolean continueRight = false;
//
//            final Array<LogicalTile> newLeftPath = new Array<>();
//            for(int l = 0; l < path.size; l++) {
//                newLeftPath.add(path.get(l));
//            }
//
//            final Array<LogicalTile> newRightPath = new Array<>();
//            for(int r = 0; r < path.size; r++) {
//                newRightPath.add(path.get(r));
//            }
//
//            final Array<LogicalTile> newUpPath = new Array<>();
//            for(int u = 0; u < path.size; u++) {
//                newUpPath.add(path.get(u));
//            }
//
//            final Array<LogicalTile> newDownPath = new Array<>();
//            for(int d = 0; d < path.size; d++) {
//                newDownPath.add(path.get(d));
//            }
//
//            if (lastTileInPath.getColumn() - 1 >= 0) {
//                LogicalTile nextTileLeft = abs.logicalMap.nextTileWestFrom(lastTileInPath);
//
//                if (abs.reachableTiles.contains(nextTileLeft, true)) {
//                    if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > steps) {
//                        tileCheckedAtSpeed.put(nextTileLeft, (float) steps);
//                        if (!path.contains(nextTileLeft, true)) {
//                            continueLeft = true;
//                            newLeftPath.add(nextTileLeft);
//                        } else {
////                            Gdx.app.log("ISPR: ", "error: path already contains tile");
//                        }
//                    } else {
////                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
//                    }
//                } else {
////                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
//                }
//            } else {
////                Gdx.app.log("ISPR: ", "error: out of map bounds");
//            }
//
//            if (lastTileInPath.getColumn() + 1 < abs.logicalMap.getTilesWide()) {
//                LogicalTile nextTileRight = abs.logicalMap.nextTileEastFrom(lastTileInPath);
//
//                if (abs.reachableTiles.contains(nextTileRight, true)) {
//                    if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > steps) {
//                        tileCheckedAtSpeed.put(nextTileRight, (float) steps);
//                        if (!path.contains(nextTileRight, true)) {
//                            continueRight = true;
//                            newRightPath.add(nextTileRight);
//                        } else {
////                            Gdx.app.log("ISPR: ", "error: path already contains tile");
//                        }
//                    } else {
////                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
//                    }
//                } else {
////                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
//                }
//            } else {
////                Gdx.app.log("ISPR: ", "error: out of map bounds");
//            }
//
//            if (lastTileInPath.getRow() - 1 >= 0) {
//                LogicalTile nextTileDown = abs.logicalMap.nextTileSouthFrom(lastTileInPath);
//
//                if (abs.reachableTiles.contains(nextTileDown, true)) {
//                    if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > steps) {
//                        tileCheckedAtSpeed.put(nextTileDown, (float) steps);
//                        if (!path.contains(nextTileDown, true)) {
//                            continueDown = true;
//                            newDownPath.add(nextTileDown);
//                        } else {
////                            Gdx.app.log("ISPR: ", "error: path already contains tile");
//                        }
//                    } else {
////                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
//                    }
//                } else {
////                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
//                }
//            } else {
////                Gdx.app.log("ISPR: ", "error: out of map bounds");
//            }
//
//            if (lastTileInPath.getRow() + 1 < abs.logicalMap.getTilesHigh()) {
//                LogicalTile nextTileUp = abs.logicalMap.nextTileNorthFrom(lastTileInPath);
//
//                if (abs.reachableTiles.contains(nextTileUp, true)) {
//                    if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > steps) {
//                        tileCheckedAtSpeed.put(nextTileUp, (float) steps);
//                        if (!path.contains(nextTileUp, true)) {
//                            continueUp = true;
//                            newUpPath.add(nextTileUp);
//                        } else {
////                            Gdx.app.log("ISPR: ", "error: path already contains tile");
//                        }
//                    } else {
////                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
//                    }
//                } else {
////                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
//                }
//            } else {
////                Gdx.app.log("ISPR: ", "error: out of map bounds");
//            }
//
//            boolean NUPInRange = false;
//            for(LogicalTile tile : newUpPath) {
//                if(abs.distanceBetweenTiles(tile, destination) <= unit.getReach()) {
//                    NUPInRange = true;
//                    break;
//                }
//            }
//
//            boolean NDPInRange = false;
//            for(LogicalTile tile : newDownPath) {
//                if(abs.distanceBetweenTiles(tile, destination) <= unit.getReach()) {
//                    NDPInRange = true;
//                    break;
//                }
//            }
//
//            boolean NRPInRange = false;
//            for(LogicalTile tile : newRightPath) {
//                if(abs.distanceBetweenTiles(tile, destination) <= unit.getReach()) {
//                    NRPInRange = true;
//                    break;
//                }
//            }
//
//            boolean NLPInRange = false;
//            for(LogicalTile tile : newLeftPath) {
//                if(abs.distanceBetweenTiles(tile, destination) <= unit.getReach()) {
//                    NLPInRange = true;
//                    break;
//                }
//            }
//
//            if(continueUp && !pathFound) {
//                if(NUPInRange) {
//                    shortPath = newUpPath;
//                    pathFound = true;
//                } else {
////                    Gdx.app.log("new up", "" + newUpPath.size);
//                    internalShortPathRecursion(newUpPath, destination, unit,steps + 1);
//                }
//            }
//
//            if(continueDown && !pathFound) {
//                if(NDPInRange) {
//                    shortPath = newDownPath;
//                    pathFound = true;
//                } else {
////                    Gdx.app.log("new down", "" + newDownPath.size);
//                    internalShortPathRecursion(newDownPath, destination, unit,steps + 1);
//                }
//            }
//
//            if(continueRight && !pathFound) {
//                if(NRPInRange) {
//                    shortPath = newRightPath;
//                    pathFound = true;
//                } else {
////                    Gdx.app.log("new right", "" + newRightPath.size);
//                    internalShortPathRecursion(newRightPath, destination, unit,steps + 1);
//                }
//            }
//
//            if(continueLeft && !pathFound) {
//                if(NLPInRange) {
//                    shortPath = newLeftPath;
//                    pathFound = true;
//                } else {
////                    Gdx.app.log("new left", "" + newLeftPath.size);
//                    internalShortPathRecursion(newLeftPath, destination, unit,steps + 1);
//                }
//            }
//        }
//    }

}


