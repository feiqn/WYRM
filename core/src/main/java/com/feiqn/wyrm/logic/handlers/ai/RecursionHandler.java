package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.SimpleUnit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RecursionHandler {
    // Let's keep it clean and contained.

    private final WYRMGame game;
    private final GridScreen ags;

    private boolean pathFound;
    private Path shortPath;

    private HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public RecursionHandler(WYRMGame game) {
        this.game = game;
        ags = game.activeGridScreen;
        pathFound = false;
        tileCheckedAtSpeed = new HashMap<>();
    }

    public void recursivelySelectReachableTiles(@NotNull SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getRow(), unit.getColumn(), unit.modifiedSimpleSpeed(), unit.getMovementType());
    }
    // Don't talk to me about these two constructors. I know.
    public void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
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
                nextTileLeft = ags.getLogicalMap().getTileAtPosition(nextPos);

                if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileLeft, moveSpeed);

                    if (!nextTileLeft.isOccupied) {

                        if (nextTileLeft.isTraversableByUnitType(movementType)) {
                            if (!ags.reachableTiles.contains(nextTileLeft, true)) {
                                ags.reachableTiles.add(nextTileLeft);
                            }
                            continueLeft = true;
                        }

                    } else if (((ags.conditionsHandler.getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditionsHandler.getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileLeft.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileLeft.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueLeft = true;

                    } else if (!ags.attackableUnits.contains(nextTileLeft.occupyingUnit, true)) {
                        // TODO: later qol improvement to directly click enemy rather than moving then selecting attack
                        ags.attackableUnits.add(nextTileLeft.occupyingUnit);
                    }
                }
            }


            final int newX1 = startX + 1;
            final Vector2 nextPos1 = new Vector2(newX1, startY);

            if (nextPos1.x < ags.getLogicalMap().getTilesWide()) {
                nextTileRight = ags.getLogicalMap().getTileAtPosition(nextPos1);

                if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileRight, moveSpeed);

                    if (!nextTileRight.isOccupied) {

                        if (nextTileRight.isTraversableByUnitType(movementType)) {
                            if (!ags.reachableTiles.contains(nextTileRight, true)) {
                                ags.reachableTiles.add(nextTileRight);
                            }
                            continueRight = true;

                        }
                    } else if (((ags.conditionsHandler.getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditionsHandler.getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileRight.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileRight.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueRight = true;

                    } else if (!ags.attackableUnits.contains(nextTileRight.occupyingUnit, true)) {
                        ags.attackableUnits.add(nextTileRight.occupyingUnit);
                    }
                }
            }


            final int newY = startY - 1;
            final Vector2 nextPos2 = new Vector2(startX, newY);

            if (nextPos2.y >= 0) {
                nextTileDown = ags.getLogicalMap().getTileAtPosition(nextPos2);

                if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileDown, moveSpeed);

                    if (!nextTileDown.isOccupied) {

                        if (nextTileDown.isTraversableByUnitType(movementType)) {
                            if (!ags.reachableTiles.contains(nextTileDown, true)) {
                                ags.reachableTiles.add(nextTileDown);
                            }
                            continueDown = true;

                        }
                    } else if (((ags.conditionsHandler.getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditionsHandler.getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileDown.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileDown.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueDown = true;

                    } else if (!ags.attackableUnits.contains(nextTileDown.occupyingUnit, true)) {
                        ags.attackableUnits.add(nextTileDown.occupyingUnit);
                    }
                }
            }


            final int newY1 = startY + 1;
            final Vector2 nextPos3 = new Vector2(startX, newY1);

            if (nextPos3.y < ags.getLogicalMap().getTilesHigh()) {
                nextTileUp = ags.getLogicalMap().getTileAtPosition(nextPos3);

                if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) < moveSpeed) {
                    tileCheckedAtSpeed.put(nextTileUp, moveSpeed);

                    if (!nextTileUp.isOccupied) {

                        if (nextTileUp.isTraversableByUnitType(movementType)) {
                            if (!ags.reachableTiles.contains(nextTileUp, true)) {
                                ags.reachableTiles.add(nextTileUp);
                            }
                            continueUp = true;
                        }

                    } else if (((ags.conditionsHandler.getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditionsHandler.getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileUp.occupyingUnit.getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditionsHandler.getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileUp.occupyingUnit.getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueUp = true;

                    } else if (!ags.attackableUnits.contains(nextTileUp.occupyingUnit, true)) {
                        ags.attackableUnits.add(nextTileUp.occupyingUnit);
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
    public Path shortestPath(@NotNull SimpleUnit unit, @NotNull LogicalTile destination, boolean continuous) {
        // Returns the shortest path for a given unit to another tile.

        // assume unlimited movement.
        recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), 100, unit.getMovementType());

        ags.reachableTiles.add(unit.occupyingTile);

        pathFound = false;
        shortPath = new Path(game, unit.occupyingTile);
        tileCheckedAtSpeed = new HashMap<>();

        Bloom(unit, destination, continuous);

        return shortPath;

    }

    private void Bloom(SimpleUnit unit, LogicalTile destination, boolean continuous) {
        /* Create an array of paths,
         * loop through the entire array,
         * during each loop, take each path in the array and check
         * if all adjacent tiles are either accessible or already
         * within path.
         * If Tile is accessible and not within path,
         * create a new path containing Tile for each direction,
         * and add to array at beginning of next loop, then
         * remove the paths that were just iterated upon and
         * not within reach of destination.
         *
         *  TODO: ^ update description of function to account for new features
         *        also its still getting stuck and not counting cost right
         */

        final Array<Path> paths = new Array<>();
        paths.add(new Path(game, unit.occupyingTile));

        do {

            final Array<Integer> indexesToRemove = new Array<>();
            final int loopBound = paths.size;

            for (int p = 0; p < loopBound; p++) {

                final Path path = new Path(paths.get(p));
                final float cost = path.cost(unit);

                if (path.lastTile().getColumn() - 1 >= 0) {
                    LogicalTile nextTileLeft = ags.getLogicalMap().nextTileWestFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileLeft, true)) {
                        final float newCost = cost+nextTileLeft.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > newCost) {
                            tileCheckedAtSpeed.put(nextTileLeft, newCost);
                            if (!path.contains(nextTileLeft)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.LEFT);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getColumn() + 1 < ags.getLogicalMap().getTilesWide()) {
                    LogicalTile nextTileRight = ags.getLogicalMap().nextTileEastFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileRight, true)) {
                        final float newCost = cost+nextTileRight.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > newCost) {
                            tileCheckedAtSpeed.put(nextTileRight, newCost);
                            if (!path.contains(nextTileRight)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.RIGHT);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRow() - 1 >= 0) {
                    LogicalTile nextTileDown = ags.getLogicalMap().nextTileSouthFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileDown, true)) {
                        final float newCost = cost+nextTileDown.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > newCost) {
                            tileCheckedAtSpeed.put(nextTileDown, newCost);
                            if (!path.contains(nextTileDown)) {

                                final Path branchingPath = new Path(path);
                                branchingPath.incorporateNextTile(Direction.DOWN);
                                paths.add(branchingPath);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRow() + 1 < ags.getLogicalMap().getTilesHigh()) {
                    LogicalTile nextTileUp = ags.getLogicalMap().nextTileNorthFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileUp, true)) {
                        final float newCost = cost+nextTileUp.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > newCost) {
                            tileCheckedAtSpeed.put(nextTileUp, newCost);
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

        } while (continuous ? !closeEnough(paths, destination) : !containsTileInReachOf(paths, destination, unit.getSimpleReach()));
        /* If a continuous path is requested, check if any path is within 1 tile of destination,
         * as the destination tile may never be in a path due to being occupied already.
         *
         * If a continuous path is not requested, check for any path within unit's attack range of target.
         */

    }


    private boolean closeEnough(Array<Path> paths, LogicalTile destination) {

        pathFound = false;

        for (Path path : paths) {
            if (!pathFound) {
                for (LogicalTile tile : path.retrievePath()) {
                    if (ags.getLogicalMap().distanceBetweenTiles(tile, destination) == 1) {
                        shortPath = path;
                        shortPath.iDoThinkThatIKnowWhatIAmDoingAndSoIFeelQuiteComfortableArbitrarilyAddingThisTileToTheEndOfThisPath(destination);
                        pathFound = true;
                        break;
                    }
                }
            }
        }

        return pathFound;

    }

    private boolean containsTileInReachOf(@NotNull Array<Path> paths, LogicalTile destination, int reach) {

        pathFound = false;

        for (Path path : paths) {
            if (!pathFound) {
                for (LogicalTile tile : path.retrievePath()) {
                    if (ags.getLogicalMap().distanceBetweenTiles(tile, destination) <= reach) {
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
