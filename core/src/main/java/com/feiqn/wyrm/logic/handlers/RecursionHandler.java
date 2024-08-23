package com.feiqn.wyrm.logic.handlers;

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
        internalReachableTileRecursion(unit.getRow(), unit.getColumn(), unit.getModifiedMobility(), unit.getMovementType());
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

            boolean continueUp = false;
            boolean continueDown = false;
            boolean continueLeft = false;
            boolean continueRight = false;

            LogicalTile nextTileLeft = new LogicalTile(game, -1, -1);
            LogicalTile nextTileRight = new LogicalTile(game, -1, -1);
            LogicalTile nextTileDown = new LogicalTile(game, -1, -1);
            LogicalTile nextTileUp = new LogicalTile(game, -1, -1);

            final int newX = startX - 1;
            final Vector2 nextPos = new Vector2(newX, startY);

            if (nextPos.x >= 0) {
                nextTileLeft = abs.logicalMap.getTileAtPosition(nextPos);

                if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileLeft, moveSpeed);

                    if (!nextTileLeft.isOccupied) {

                        if (nextTileLeft.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileLeft, true)) {
                                abs.reachableTiles.add(nextTileLeft);
                            }
                            continueLeft = true;
                        }

                    } else if (((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueLeft = true;

                    } else if (!abs.attackableUnits.contains(nextTileLeft.occupyingUnit, true)) {
                        // TODO: later qol improvement to directly click enemy rather than moving then selecting attack
                        abs.attackableUnits.add(nextTileLeft.occupyingUnit);
                    }
                }
            }


            final int newX1 = startX + 1;
            final Vector2 nextPos1 = new Vector2(newX1, startY);

            if (nextPos1.x < abs.logicalMap.getTilesWide()) {
                nextTileRight = abs.logicalMap.getTileAtPosition(nextPos1);

                if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileRight, moveSpeed);

                    if (!nextTileRight.isOccupied) {

                        if (nextTileRight.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileRight, true)) {
                                abs.reachableTiles.add(nextTileRight);
                            }
                            continueRight = true;

                        }
                    } else if (((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueRight = true;

                    } else if (!abs.attackableUnits.contains(nextTileRight.occupyingUnit, true)) {
                        abs.attackableUnits.add(nextTileRight.occupyingUnit);
                    }
                }
            }


            final int newY = startY - 1;
            final Vector2 nextPos2 = new Vector2(startX, newY);

            if (nextPos2.y >= 0) {
                nextTileDown = abs.logicalMap.getTileAtPosition(nextPos2);

                if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileDown, moveSpeed);

                    if (!nextTileDown.isOccupied) {

                        if (nextTileDown.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileDown, true)) {
                                abs.reachableTiles.add(nextTileDown);
                            }
                            continueDown = true;

                        }
                    } else if (((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueDown = true;

                    } else if (!abs.attackableUnits.contains(nextTileDown.occupyingUnit, true)) {
                        abs.attackableUnits.add(nextTileDown.occupyingUnit);
                    }
                }
            }


            final int newY1 = startY + 1;
            final Vector2 nextPos3 = new Vector2(startX, newY1);

            if (nextPos3.y < abs.logicalMap.getTilesHigh()) {
                nextTileUp = abs.logicalMap.getTileAtPosition(nextPos3);

                if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileUp, moveSpeed);

                    if (!nextTileUp.isOccupied) {

                        if (nextTileUp.isTraversableByUnitType(movementType)) {
                            if (!abs.reachableTiles.contains(nextTileUp, true)) {
                                abs.reachableTiles.add(nextTileUp);
                            }
                            continueUp = true;
                        }

                    } else if (((abs.currentPhase == Phase.PLAYER_PHASE || abs.currentPhase == Phase.ALLY_PHASE) &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (abs.currentPhase == Phase.ENEMY_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (abs.currentPhase == Phase.OTHER_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueUp = true;

                    } else if (!abs.attackableUnits.contains(nextTileUp.occupyingUnit, true)) {
                        abs.attackableUnits.add(nextTileUp.occupyingUnit);
                    }
                }
            }


            if (continueUp) {
                internalReachableTileRecursion(startX, newY1, moveSpeed - nextTileUp.getMovementCostForMovementType(movementType), movementType);
            }
            if (continueLeft) {
                internalReachableTileRecursion(newX, startY, moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType), movementType);
            }
            if (continueDown) {
                internalReachableTileRecursion(startX, newY, moveSpeed - nextTileDown.getMovementCostForMovementType(movementType), movementType);
            }
            if (continueRight) {
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

        pathFound = false;
        shortPath = new Path(game, unit.occupyingTile);
        tileCheckedAtSpeed = new HashMap<>();

        Bloom(unit, destination);

        return shortPath;

    }

    private void Bloom(Unit unit, LogicalTile destination) {
        /* Create an array of paths,
         * loop through the entire array,
         * during each loop, take each path in the array and check
         * if all adjacent tiles are either accessible or already
         * within path. If Tile is accessible and not within path,
         * create a new path containing Tile for each direction,
         * and add to array at beginning of next loop, then
         * remove the paths that were just iterated upon and
         * not within reach of destination.
         */

        final Array<Path> paths = new Array<>();
        paths.add(new Path(game, unit.occupyingTile));

        do {

            final Array<Integer> indexesToRemove = new Array<>();

            final int loopBound = paths.size;

            for (int p = 0; p < loopBound; p++) {

                final Path path = new Path(paths.get(p));

                if (path.lastTile().getColumn() - 1 >= 0) {
                    LogicalTile nextTileLeft = abs.logicalMap.nextTileWestFrom(path.lastTile());
                    if (abs.reachableTiles.contains(nextTileLeft, true)) {
                        if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileLeft, (float) path.size());
                            if (!path.contains(nextTileLeft)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.LEFT);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getColumn() + 1 < abs.logicalMap.getTilesWide()) {
                    LogicalTile nextTileRight = abs.logicalMap.nextTileEastFrom(path.lastTile());
                    if (abs.reachableTiles.contains(nextTileRight, true)) {
                        if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileRight, (float) path.size());
                            if (!path.contains(nextTileRight)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.RIGHT);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRow() - 1 >= 0) {
                    LogicalTile nextTileDown = abs.logicalMap.nextTileSouthFrom(path.lastTile());
                    if (abs.reachableTiles.contains(nextTileDown, true)) {
                        if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileDown, (float) path.size());
                            if (!path.contains(nextTileDown)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.DOWN);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRow() + 1 < abs.logicalMap.getTilesHigh()) {
                    LogicalTile nextTileUp = abs.logicalMap.nextTileNorthFrom(path.lastTile());
                    if (abs.reachableTiles.contains(nextTileUp, true)) {
                        if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > path.size()) {
                            tileCheckedAtSpeed.put(nextTileUp, (float) path.size());
                            if (!path.contains(nextTileUp)) {

                                final Path branchingPathU = new Path(path);
                                branchingPathU.incorporateNextTile(Direction.UP);
                                paths.add(branchingPathU);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

            }

            for (int i = paths.size + 1; i >= 0; i--) {
                if (indexesToRemove.contains(i, true)) {
                    paths.removeIndex(i);
                }
            }

        } while (!containsTileInReachOf(paths, destination, unit.getReach()));

    }

    private boolean containsTileInReachOf(@NotNull Array<Path> paths, LogicalTile destination, int reach) {

        pathFound = false;

        for (Path path : paths) {
            if (!pathFound) {
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
}
