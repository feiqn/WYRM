package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.mapdata.Direction;
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
    private Array<LogicalTile> shortPath;

    private HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public RecursionHandler(WYRMGame game) {
        this.game = game;
        abs = game.activeBattleScreen;
        pathFound = false;
        shortPath = new Array<>();
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
                        Gdx.app.log("unit", "i see an enemy");

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
        public Array<LogicalTile> shortestPath(@NotNull Unit unit, @NotNull LogicalTile destination) {
        // Returns the shortest path for a given unit to another tile.

        // assume unlimited movement.
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), 100, unit.getMovementType());

        abs.reachableTiles.add(unit.occupyingTile);

//        for(LogicalTile tile : abs.reachableTiles) {
//            tile.highlightCanSupport();
//        }


        final LogicalTile startingTile = unit.occupyingTile;

        final Array<LogicalTile> path = new Array<>();
//        Gdx.app.log("Short Path: ", "seeding first path with: " + startingTile.getColumn() + " , " + startingTile.getRow());
        path.add(startingTile);

        pathFound = false;
        shortPath = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
//        Gdx.app.log("Short Path: ", "destination tile: " + destination.getRow() + " , " + destination.getColumn());
        internalShortPathRecursion(path, destination, 0);

//        for(LogicalTile tile : abs.reachableTiles) {
//            tile.highlightCanSupport();
//        }
//
//        for(LogicalTile tile : growingPath) {
//            tile.highlightCanSupport();
//        }

        return shortPath;

    }

    private void internalShortPathRecursion(Array<LogicalTile> path, LogicalTile destination, int steps) {
        /*
         * start with last tile in path
         *
         * simultaneously expand to all accessible surrounding tiles (up to 4)
         * that aren't already part of the path, creating new paths for each
         *
         * check if any of the tiles expanded to are the destination
         *
         * if not, recurse self with each new path
         */

        // ERROR: returning 0-length arrays.

        if(!pathFound) {
            final LogicalTile lastTileInPath = path.get(path.size - 1);

//            Gdx.app.log("ISPR: ", "lastTileInPath: " + lastTileInPath.getRow() + " , " + lastTileInPath.getColumn());

            boolean continueUp = false;
            boolean continueDown = false;
            boolean continueLeft = false;
            boolean continueRight = false;

            Array<LogicalTile> newLeftPath = new Array<>();
            Array<LogicalTile> newRightPath = new Array<>();
            Array<LogicalTile> newUpPath = new Array<>();
            Array<LogicalTile> newDownPath = new Array<>();

            if (lastTileInPath.getColumn() - 1 >= 0) {
                LogicalTile nextTileLeft = abs.logicalMap.nextTileWestFrom(lastTileInPath);

                if (abs.reachableTiles.contains(nextTileLeft, true)) {
                    if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > steps) {
                        tileCheckedAtSpeed.put(nextTileLeft, (float) steps);
                        if (!path.contains(nextTileLeft, true)) {
                            continueLeft = true;
                            newLeftPath = path;
                            newLeftPath.add(nextTileLeft);
                        } else {
//                            Gdx.app.log("ISPR: ", "error: path already contains tile");
                        }
                    } else {
//                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
                    }
                } else {
//                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
                }
            } else {
//                Gdx.app.log("ISPR: ", "error: out of map bounds");
            }

            if (lastTileInPath.getColumn() + 1 < abs.logicalMap.getTilesWide()) {
                LogicalTile nextTileRight = abs.logicalMap.nextTileEastFrom(lastTileInPath);

                if (abs.reachableTiles.contains(nextTileRight, true)) {
                    if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > steps) {
                        tileCheckedAtSpeed.put(nextTileRight, (float) steps);
                        if (!path.contains(nextTileRight, true)) {
                            continueRight = true;
                            newRightPath = path;
                            newRightPath.add(nextTileRight);
                        } else {
//                            Gdx.app.log("ISPR: ", "error: path already contains tile");
                        }
                    } else {
//                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
                    }
                } else {
//                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
                }
            } else {
//                Gdx.app.log("ISPR: ", "error: out of map bounds");
            }

            if (lastTileInPath.getRow() - 1 >= 0) {
                LogicalTile nextTileDown = abs.logicalMap.nextTileSouthFrom(lastTileInPath);

                if (abs.reachableTiles.contains(nextTileDown, true)) {
                    if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > steps) {
                        tileCheckedAtSpeed.put(nextTileDown, (float) steps);
                        if (!path.contains(nextTileDown, true)) {
                            continueDown = true;
                            newDownPath = path;
                            newDownPath.add(nextTileDown);
                        } else {
//                            Gdx.app.log("ISPR: ", "error: path already contains tile");
                        }
                    } else {
//                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
                    }
                } else {
//                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
                }
            } else {
//                Gdx.app.log("ISPR: ", "error: out of map bounds");
            }

            if (lastTileInPath.getRow() + 1 < abs.logicalMap.getTilesHigh()) {
                LogicalTile nextTileUp = abs.logicalMap.nextTileNorthFrom(lastTileInPath);

                if (abs.reachableTiles.contains(nextTileUp, true)) {
                    if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > steps) {
                        tileCheckedAtSpeed.put(nextTileUp, (float) steps);
                        if (!path.contains(nextTileUp, true)) {
                            continueUp = true;
                            newUpPath = path;
                            newUpPath.add(nextTileUp);
                        } else {
//                            Gdx.app.log("ISPR: ", "error: path already contains tile");
                        }
                    } else {
//                        Gdx.app.log("ISPR: ", "error: tile already checked with fewer steps");
                    }
                } else {
//                    Gdx.app.log("ISPR: ", "error: not in reachableTiles");
                }
            } else {
//                Gdx.app.log("ISPR: ", "error: out of map bounds");
            }

            if(continueUp && !pathFound) {
                if (newUpPath.contains(abs.logicalMap.getTileAtPosition(destination.getCoordinates()), true)) {
                    shortPath = newUpPath;
                    pathFound = true;
                } else {
                    // todo: print new path lengths here to debug
                    internalShortPathRecursion(newUpPath, destination, steps + 1);
                }
            }
            if(continueDown && !pathFound) {
                if (newDownPath.contains(abs.logicalMap.getTileAtPosition(destination.getCoordinates()), true)) {
                    shortPath = newDownPath;
                    pathFound = true;
                } else {
                    internalShortPathRecursion(newDownPath, destination, steps + 1);
                }
            }
            if(continueRight && !pathFound) {
                if (newRightPath.contains(abs.logicalMap.getTileAtPosition(destination.getCoordinates()), true)) {
                    shortPath = newRightPath;
                    pathFound = true;
                } else {
                    internalShortPathRecursion(newRightPath, destination, steps + 1);
                }
            }
            if(continueLeft && !pathFound) {
                if (newLeftPath.contains(abs.logicalMap.getTileAtPosition(destination.getCoordinates()), true)) {
                    shortPath = newLeftPath;
                    pathFound = true;
                } else {
                    internalShortPathRecursion(newLeftPath, destination, steps + 1);
                }
            }
        }
    }

}


