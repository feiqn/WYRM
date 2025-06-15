package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.Gdx;
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
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RecursionHandler {
    // Let's keep it clean and contained.

    private final WYRMGame game;
    private final GridScreen ags;

    private boolean pathFound;
    private Path shortPath;
    private Array<Path> validatedPaths;

    private HashMap<LogicalTile, Float> tileCheckedAtSpeed;

    public RecursionHandler(WYRMGame game) {
        this.game = game;
        ags = game.activeGridScreen;
        pathFound = false;
        tileCheckedAtSpeed = new HashMap<>();
    }

    private void internalXRayRecursion(int startX, int startY, float moveCostToGetHere, MovementType movementType, LogicalTile destination) {
        boolean continueUp = false;
        boolean continueDown = false;
        boolean continueLeft = false;
        boolean continueRight = false;

        LogicalTile nextTileLeft = new LogicalTile(game, -1, -1);
        LogicalTile nextTileRight = new LogicalTile(game, -1, -1);
        LogicalTile nextTileDown = new LogicalTile(game, -1, -1);
        LogicalTile nextTileUp = new LogicalTile(game, -1, -1);


        final int newXLeft = startX - 1;

        if (newXLeft >= 0) {
            nextTileLeft = ags.getLogicalMap().getTileAtPositionXY(newXLeft, startY);

            if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > moveCostToGetHere) {
                tileCheckedAtSpeed.put(nextTileLeft, moveCostToGetHere);

                if (nextTileLeft.isTraversableByUnitType(movementType)) {
                    if (!ags.reachableTiles.contains(nextTileLeft, true)) {
                        ags.reachableTiles.add(nextTileLeft);
                    }
                    continueLeft = true;
                }
            }
        }

        final int newXRight = startX + 1;

        if (newXRight < ags.getLogicalMap().getTilesWide()) {
            nextTileRight = ags.getLogicalMap().getTileAtPositionXY(newXRight, startY);

            if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > moveCostToGetHere) {
                tileCheckedAtSpeed.put(nextTileRight, moveCostToGetHere);

                if (nextTileRight.isTraversableByUnitType(movementType)) {
                    if (!ags.reachableTiles.contains(nextTileRight, true)) {
                        ags.reachableTiles.add(nextTileRight);
                    }
                    continueRight = true;

                }
            }
        }


        final int newYDown = startY - 1;

        if (newYDown >= 0) {
            nextTileDown = ags.getLogicalMap().getTileAtPositionXY(startX, newYDown);

            if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) < moveCostToGetHere) {
                tileCheckedAtSpeed.put(nextTileDown, moveCostToGetHere);

                if (nextTileDown.isTraversableByUnitType(movementType)) {
                    if (!ags.reachableTiles.contains(nextTileDown, true)) {
                        ags.reachableTiles.add(nextTileDown);
                    }
                    continueDown = true;

                }
            }
        }


        final int newYUp = startY + 1;

        if (newYUp < ags.getLogicalMap().getTilesHigh()) {
            nextTileUp = ags.getLogicalMap().getTileAtPositionXY(startX, newYUp);

            if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) < moveCostToGetHere) {
                tileCheckedAtSpeed.put(nextTileUp, moveCostToGetHere);

                if (nextTileUp.isTraversableByUnitType(movementType)) {
                    if (!ags.reachableTiles.contains(nextTileUp, true)) {
                        ags.reachableTiles.add(nextTileUp);
                    }
                    continueUp = true;
                }
            }
        }

        if(ags.reachableTiles.contains(destination, true)) return;

        if (continueUp) {
            internalXRayRecursion(startX, newYUp, moveCostToGetHere + nextTileUp.getMovementCostForMovementType(movementType), movementType, destination);
        }
        if (continueLeft) {
            internalXRayRecursion(newXLeft, startY, moveCostToGetHere + nextTileLeft.getMovementCostForMovementType(movementType), movementType, destination);
        }
        if (continueDown) {
            internalXRayRecursion(startX, newYDown, moveCostToGetHere + nextTileDown.getMovementCostForMovementType(movementType), movementType, destination);
        }
        if (continueRight) {
            internalXRayRecursion(newXRight, startY, moveCostToGetHere + nextTileRight.getMovementCostForMovementType(movementType), movementType, destination);
        }
    }
    private void trimXRayPathToFarthestReachableTile(SimpleUnit unit) {
        for(int i = 1; i <= shortPath.size(); i++) {
            if (shortPath.retrievePath().get(i).isOccupied() && // TODO: .retrievePath returns a standard array indexed from 0 and so this should maybe be i-1
                shortPath.retrievePath().get(i).getOccupyingUnit().getTeamAlignment() != unit.getTeamAlignment()) {
                shortPath.truncate(i-1);
            }
        }
    }
    public Path xRayPath(SimpleUnit movingUnit, LogicalTile destination) {
        ags.reachableTiles = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();

        shortPath = new Path(game, movingUnit.getOccupyingTile());

        internalXRayRecursion(movingUnit.getColumnX(), movingUnit.getRowY(), 0 ,movingUnit.getMovementType(), destination);
        trimXRayPathToFarthestReachableTile(movingUnit);

        return shortPath;
    }

    public void recursivelySelectAll(SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach());
    }
    public void recursivelySelectReachableTiles(@NotNull SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getColumnX(), unit.getRowY(), unit.modifiedSimpleSpeed(), unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach());
    }
    // Don't talk to me about these two constructors. I know.
    public void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType, TeamAlignment currentTeam, int reach) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(startX, startY, moveSpeed, movementType, currentTeam, reach);
    }
    private void internalReachableTileRecursion(int startX, int startY, float moveSpeed, MovementType movementType, TeamAlignment currentTeam, int reach) {
        /* Called by highlightAllTilesUnitCanReach()
         * Called by AIHandler
         * Called before shortestPath()
         */

        /* Selects all accessible tiles and attackable enemies
         * for movementType within distance moveSpeed of selected tile.
         * TODO: later, this will also select supportable allies.
         */

        if (moveSpeed >= 0) {

            boolean continueUp    = false;
            boolean continueDown  = false;
            boolean continueLeft  = false;
            boolean continueRight = false;

            LogicalTile nextTileLeft = new LogicalTile(game, -1, -1);
            LogicalTile nextTileRight = new LogicalTile(game, -1, -1);
            LogicalTile nextTileDown = new LogicalTile(game, -1, -1);
            LogicalTile nextTileUp = new LogicalTile(game, -1, -1);

            final int newXLeft = startX - 1;

            if (newXLeft >= 0) {
                nextTileLeft = ags.getLogicalMap().getTileAtPositionXY(newXLeft, startY);

                if (!tileCheckedAtSpeed.containsKey(nextTileLeft) ||
                    tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {

                    tileCheckedAtSpeed.put(nextTileLeft, moveSpeed);

                    if (!nextTileLeft.isOccupied()) {
                        if (nextTileLeft.isTraversableByUnitType(movementType)) {
                            if(moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType) >= 0) {
                                if (!ags.reachableTiles.contains(nextTileLeft, true)) {
                                    ags.reachableTiles.add(nextTileLeft);
                                }
                                continueLeft = true;
                            }
                        }

                    } else if (((ags.conditions().getCurrentPhase() == Phase.PLAYER_PHASE ||
                                 ags.conditions().getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileLeft.getOccupyingUnit().getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileLeft.getOccupyingUnit().getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditions().getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileLeft.getOccupyingUnit().getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditions().getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileLeft.getOccupyingUnit().getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueLeft = true;

                    } else if (!ags.attackableUnits.contains(nextTileLeft.getOccupyingUnit(), true)) {
                        // TODO: later qol improvement to directly click enemy rather than moving then selecting attack
                        ags.attackableUnits.add(nextTileLeft.getOccupyingUnit());
                    }
                }
            }


            final int newXRight = startX + 1;

            if (newXRight < ags.getLogicalMap().getTilesWide()) {
                nextTileRight = ags.getLogicalMap().getTileAtPositionXY(newXRight, startY);

                if (!tileCheckedAtSpeed.containsKey(nextTileRight) ||
                    tileCheckedAtSpeed.get(nextTileRight) < moveSpeed) {

                    tileCheckedAtSpeed.put(nextTileRight, moveSpeed);

                    if (!nextTileRight.isOccupied()) {
                        if (nextTileRight.isTraversableByUnitType(movementType)) {
                            if(moveSpeed - nextTileRight.getMovementCostForMovementType(movementType) >= 0) {
                                if (!ags.reachableTiles.contains(nextTileRight, true)) {
                                    ags.reachableTiles.add(nextTileRight);
                                }
                                continueRight = true;
                            }
                        }
                    } else if (((ags.conditions().getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditions().getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileRight.getOccupyingUnit().getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileRight.getOccupyingUnit().getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditions().getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileRight.getOccupyingUnit().getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditions().getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileRight.getOccupyingUnit().getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueRight = true;

                    } else if (!ags.attackableUnits.contains(nextTileRight.getOccupyingUnit(), true)) {
                        ags.attackableUnits.add(nextTileRight.getOccupyingUnit());
                    }
                }
            }


            final int newYDown = startY - 1;

            if (newYDown >= 0) {
                nextTileDown = ags.getLogicalMap().getTileAtPositionXY(startX, newYDown);

                if (!tileCheckedAtSpeed.containsKey(nextTileDown) ||
                    tileCheckedAtSpeed.get(nextTileDown) < moveSpeed) {

                    tileCheckedAtSpeed.put(nextTileDown, moveSpeed);

                    if (!nextTileDown.isOccupied()) {
                        if(moveSpeed - nextTileDown.getMovementCostForMovementType(movementType) >= 0) {
                            if (nextTileDown.isTraversableByUnitType(movementType)) {
                                if (!ags.reachableTiles.contains(nextTileDown, true)) {
                                    ags.reachableTiles.add(nextTileDown);
                                }
                                continueDown = true;
                            }
                        }

                    } else if (((ags.conditions().getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditions().getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileDown.getOccupyingUnit().getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileDown.getOccupyingUnit().getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditions().getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileDown.getOccupyingUnit().getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditions().getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileDown.getOccupyingUnit().getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueDown = true;

                    } else if (!ags.attackableUnits.contains(nextTileDown.getOccupyingUnit(), true)) {
                        ags.attackableUnits.add(nextTileDown.getOccupyingUnit());
                    }
                }
            }


            final int newYUp = startY + 1;

            if (newYUp < ags.getLogicalMap().getTilesHigh()) {
                nextTileUp = ags.getLogicalMap().getTileAtPositionXY(startX, newYUp);

                if (!tileCheckedAtSpeed.containsKey(nextTileUp) ||
                    tileCheckedAtSpeed.get(nextTileUp) < moveSpeed) {

                    tileCheckedAtSpeed.put(nextTileUp, moveSpeed);

                    if (!nextTileUp.isOccupied()) {
                        if (nextTileUp.isTraversableByUnitType(movementType)) {
                            if(moveSpeed - nextTileUp.getMovementCostForMovementType(movementType) >= 0) {
                                if (!ags.reachableTiles.contains(nextTileUp, true)) {
                                    ags.reachableTiles.add(nextTileUp);
                                }
                                continueUp = true;
                            }
                        }

                    } else if (((ags.conditions().getCurrentPhase() == Phase.PLAYER_PHASE || ags.conditions().getCurrentPhase() == Phase.ALLY_PHASE) &&
                        nextTileUp.getOccupyingUnit().getTeamAlignment() != TeamAlignment.ENEMY &&
                        nextTileUp.getOccupyingUnit().getTeamAlignment() != TeamAlignment.OTHER) ||
                        (ags.conditions().getCurrentPhase() == Phase.ENEMY_PHASE &&
                            nextTileUp.getOccupyingUnit().getTeamAlignment() == TeamAlignment.ENEMY) ||
                        (ags.conditions().getCurrentPhase() == Phase.OTHER_PHASE &&
                            nextTileUp.getOccupyingUnit().getTeamAlignment() == TeamAlignment.OTHER)) {

                        continueUp = true;

                    } else if (!ags.attackableUnits.contains(nextTileUp.getOccupyingUnit(), true)) {
                        ags.attackableUnits.add(nextTileUp.getOccupyingUnit());
                    }
                }
            }

            final Array<LogicalTile> tilesInReach = ags.getLogicalMap().tilesWithinDistanceOfOrigin(ags.getLogicalMap().getTileAtPositionXY(startX, startY), reach);

            for (LogicalTile tile : tilesInReach) {
                if(tile.isOccupied()) {
                    if(tile.getOccupyingUnit().getTeamAlignment() != currentTeam) {
                        if(!ags.attackableUnits.contains(tile.getOccupyingUnit(), true)) {
                            ags.attackableUnits.add(tile.getOccupyingUnit());
                        }
                    }
                }
            }


            if (continueUp) {
                internalReachableTileRecursion(startX, newYUp, moveSpeed - nextTileUp.getMovementCostForMovementType(movementType), movementType, currentTeam, reach);
            }

            if (continueLeft) {
                internalReachableTileRecursion(newXLeft, startY, moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType), movementType, currentTeam, reach);
            }

            if (continueDown) {
                internalReachableTileRecursion(startX, newYDown, moveSpeed - nextTileDown.getMovementCostForMovementType(movementType), movementType, currentTeam, reach);
            }

            if (continueRight) {
                internalReachableTileRecursion(newXRight, startY, moveSpeed - nextTileRight.getMovementCostForMovementType(movementType), movementType, currentTeam, reach);
            }
        }
    }

    @NotNull
    @Contract(pure = true)
    public Path shortestPath(@NotNull SimpleUnit unit, @NotNull LogicalTile destination, boolean continuous) {
        // Returns the shortest path for a given unit to another tile.

        // assume unlimited movement
        recursivelySelectReachableTiles(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach());

        ags.reachableTiles.add(unit.getOccupyingTile());

        pathFound = false;
        shortPath = new Path(game, unit.getOccupyingTile());
        tileCheckedAtSpeed = new HashMap<>();

        Bloom(unit, destination, continuous);

        if(unit.getTeamAlignment() != TeamAlignment.PLAYER) {
            shortPath.clearSeedTile();
        }

        return shortPath;

    }

    // TODO: DEBUG HERE!(?)
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
         * TODO: ^ update description of function to account for new features
         */

        final Array<Path> paths = new Array<>();
        paths.add(new Path(game, unit.getOccupyingTile()));

        float lowestCost;
        boolean terminating = false;
        boolean reassign;

        do {

            final Array<Integer> indexesToRemove = new Array<>();
            final int loopBound = paths.size;

            for (int p = 0; p < loopBound; p++) {

                final Path path = new Path(paths.get(p));
                final float cost = path.cost(unit);

                if (path.lastTile().getColumnX() - 1 >= 0) {
                    LogicalTile nextTileLeft = ags.getLogicalMap().nextTileLeftFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileLeft, true)) {
                        final float newCost = cost+nextTileLeft.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > newCost) {
                            tileCheckedAtSpeed.put(nextTileLeft, newCost);
                            if (!path.contains(nextTileLeft)) {

                                final Path branchingPathLeft = new Path(path);
                                branchingPathLeft.incorporateNextTile(Direction.LEFT);
                                paths.add(branchingPathLeft);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getColumnX() + 1 < ags.getLogicalMap().getTilesWide()) {
                    LogicalTile nextTileRight = ags.getLogicalMap().nextTileRightFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileRight, true)) {
                        final float newCost = cost+nextTileRight.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > newCost) {
                            tileCheckedAtSpeed.put(nextTileRight, newCost);
                            if (!path.contains(nextTileRight)) {

                                final Path branchingPathRight = new Path(path);
                                branchingPathRight.incorporateNextTile(Direction.RIGHT);
                                paths.add(branchingPathRight);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRowY() - 1 >= 0) {
                    LogicalTile nextTileDown = ags.getLogicalMap().nextTileDownFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileDown, true)) {
                        final float newCost = cost+nextTileDown.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > newCost) {
                            tileCheckedAtSpeed.put(nextTileDown, newCost);
                            if (!path.contains(nextTileDown)) {

                                final Path branchingPathDown = new Path(path);
                                branchingPathDown.incorporateNextTile(Direction.DOWN);
                                paths.add(branchingPathDown);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (path.lastTile().getRowY() + 1 < ags.getLogicalMap().getTilesHigh()) {
                    LogicalTile nextTileUp = ags.getLogicalMap().nextTileUpFrom(path.lastTile());
                    if (ags.reachableTiles.contains(nextTileUp, true)) {
                        final float newCost = cost+nextTileUp.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > newCost) {
                            tileCheckedAtSpeed.put(nextTileUp, newCost);
                            if (!path.contains(nextTileUp)) {

                                final Path branchingPathUp = new Path(path);
                                branchingPathUp.incorporateNextTile(Direction.UP);
                                paths.add(branchingPathUp);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                indexesToRemove.add(p);

            }

            if(!terminating) terminating = (continuous ? closeEnough(paths, destination) : containsTileInReachOf(paths, destination, unit.getSimpleReach()));
            /* If a continuous path is requested, check if any path is within 1 tile of destination,
             * as the destination tile may never be in a path at this point due to being occupied already.
             *
             * If a continuous path is not requested, check for any tile within the unit's attack range of target.
             */

            lowestCost = 1000;

            reassign = false;

            if(terminating) {
                lowestCost = shortPath.cost(unit);
//                Gdx.app.log("bloom", "Short Path assigned, length: " + shortPath.size() + ", cost: " + shortPath.cost(unit));

                for(Path path : paths) {
                    if(path.cost(unit) < lowestCost) {
                        lowestCost = path.cost(unit);

                        reassign = (continuous ? validateClosePath(path, destination) : validateDistantPath(path, destination, unit.getSimpleReach()));
                        if(reassign) Gdx.app.log("bloom", "Short Path reAssigned");
                    } else if (path.cost(unit) >= shortPath.cost(unit)) {
                        indexesToRemove.add(paths.indexOf(path, true));
                    }
                }
            }

//            if(terminating) {
                for (int i = paths.size + 1; i >= 0; i--) {
                    if (indexesToRemove.contains(i, true)) {
                        if(!paths.get(i).equals(shortPath)) paths.removeIndex(i);
                    }
                }
//            }



        } while (!terminating || shortPath.cost(unit) != lowestCost || reassign);


    }

    private boolean validateClosePath(Path path, LogicalTile destination) {
        final Array<Path> a = new Array<>();
        a.add(path);
        return closeEnough(a, destination);
    }

    private boolean validateDistantPath(Path path, LogicalTile destination, int reach) {
        final Array<Path> a = new Array<>();
        a.add(path);
        return containsTileInReachOf(a, destination, reach);
    }

    private boolean closeEnough(Array<Path> paths, LogicalTile destination) {

        pathFound = false;

        for (Path path : paths) {
//            if (!pathFound) {

                if(ags.getLogicalMap().distanceBetweenTiles(path.lastTile(), destination) <= 1) {
                    shortPath = new Path(path);
                    shortPath.iDoThinkThatIKnowWhatIAmDoingAndSoIFeelQuiteComfortableArbitrarilyAddingThisTileToTheEndOfThisPath(destination);
                    pathFound = true;
//                    break;
                }

//            }
        }

//        Gdx.app.log("closeEnough", "" + pathFound);
        return pathFound;

    }

    private boolean containsTileInReachOf(@NotNull Array<Path> paths, LogicalTile destination, int reach) {

        pathFound = false;

        for (Path path : paths) {
//            if (!pathFound) {
                for (LogicalTile tile : path.retrievePath()) {
                    if (ags.getLogicalMap().distanceBetweenTiles(tile, destination) <= reach) {
                        shortPath = path;
                        pathFound = true;
//                        break;
                    }
                }
//            }
        }

        return pathFound;

    }
}
