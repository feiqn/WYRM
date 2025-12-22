package com.feiqn.wyrm.OLD_DATA.logic.handlers.ai;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.Direction;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.OLD_Path;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.Phase;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class OLD_RecursionHandler {
    // Let's keep it clean and contained.

    private final WYRMGame game;
    private final OLD_GridScreen ags;

    private boolean pathFound;
    private OLD_Path shortOLDPath;
    private Array<OLD_Path> validatedPaths;

    private HashMap<OLD_LogicalTile, Float> tileCheckedAtSpeed;

    public OLD_RecursionHandler(WYRMGame game) {
        this.game = game;
        ags = game.activeOLDGridScreen;
        pathFound = false;
        tileCheckedAtSpeed = new HashMap<>();
    }

    private void internalXRayRecursion(int startX, int startY, float moveCostToGetHere, MovementType movementType, OLD_LogicalTile destination) {
        boolean continueUp = false;
        boolean continueDown = false;
        boolean continueLeft = false;
        boolean continueRight = false;

        OLD_LogicalTile nextTileLeft = new OLD_LogicalTile(game, -1, -1);
        OLD_LogicalTile nextTileRight = new OLD_LogicalTile(game, -1, -1);
        OLD_LogicalTile nextTileDown = new OLD_LogicalTile(game, -1, -1);
        OLD_LogicalTile nextTileUp = new OLD_LogicalTile(game, -1, -1);


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
    private void trimXRayPathToFarthestReachableTile(OLD_SimpleUnit unit) {
        for(int i = 1; i <= shortOLDPath.size(); i++) {
            if (shortOLDPath.retrievePath().get(i-1).isOccupied() && // TODO: .retrievePath returns a standard array indexed from 0 and so this should maybe be i-1
                shortOLDPath.retrievePath().get(i-1).getOccupyingUnit().getTeamAlignment() != unit.getTeamAlignment()) {
                shortOLDPath.truncate(i-2);
            }
        }
    }
    public OLD_Path xRayPath(OLD_SimpleUnit movingUnit, OLD_LogicalTile destination) {
        ags.reachableTiles = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();

        shortOLDPath = shortestPath(movingUnit, destination, true, true);

        return shortOLDPath;
    }

    public void recursivelyXRayAll(OLD_SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach(), true);

    }
    public void recursivelySelectAll(OLD_SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach(), false);
    }
    public void recursivelySelectReachableTiles(@NotNull OLD_SimpleUnit unit) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(unit.getColumnX(), unit.getRowY(), unit.modifiedSimpleSpeed(), unit.getMovementType(), unit.getTeamAlignment(), unit.getSimpleReach(), false);
    }
    public void recursivelySelectReachableTiles(int startX, int startY, float moveSpeed, MovementType movementType, TeamAlignment currentTeam, int reach) {
        ags.reachableTiles = new Array<>();
        ags.attackableUnits = new Array<>();
        tileCheckedAtSpeed = new HashMap<>();
        internalReachableTileRecursion(startX, startY, moveSpeed, movementType, currentTeam, reach, false);
    }
    private void internalReachableTileRecursion(int startX, int startY, float moveSpeed, MovementType movementType, TeamAlignment currentTeam, int reach, boolean xRayUnits) {
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

            OLD_LogicalTile nextTileLeft = new OLD_LogicalTile(game, -1, -1);
            OLD_LogicalTile nextTileRight = new OLD_LogicalTile(game, -1, -1);
            OLD_LogicalTile nextTileDown = new OLD_LogicalTile(game, -1, -1);
            OLD_LogicalTile nextTileUp = new OLD_LogicalTile(game, -1, -1);

            final int newXLeft = startX - 1;

            if (newXLeft >= 0) {
                nextTileLeft = ags.getLogicalMap().getTileAtPositionXY(newXLeft, startY);

                if (!tileCheckedAtSpeed.containsKey(nextTileLeft) ||
                    tileCheckedAtSpeed.get(nextTileLeft) < moveSpeed) {

                    tileCheckedAtSpeed.put(nextTileLeft, moveSpeed);

                    if (!nextTileLeft.isOccupied() || xRayUnits) {
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

                    if (!nextTileRight.isOccupied() || xRayUnits) {
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

                    if (!nextTileDown.isOccupied() || xRayUnits) {
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

                    if (!nextTileUp.isOccupied() || xRayUnits) {
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

            final Array<OLD_LogicalTile> tilesInReach = ags.getLogicalMap().tilesWithinDistanceOfOrigin(ags.getLogicalMap().getTileAtPositionXY(startX, startY), reach);

            for (OLD_LogicalTile tile : tilesInReach) {
                if(tile.isOccupied()) {
                    if(tile.getOccupyingUnit().getTeamAlignment() != currentTeam) {
                        if(!ags.attackableUnits.contains(tile.getOccupyingUnit(), true)) {
                            ags.attackableUnits.add(tile.getOccupyingUnit());
                        }
                    }
                }
            }


            if (continueUp) {
                internalReachableTileRecursion(startX, newYUp, moveSpeed - nextTileUp.getMovementCostForMovementType(movementType), movementType, currentTeam, reach, xRayUnits);
            }

            if (continueLeft) {
                internalReachableTileRecursion(newXLeft, startY, moveSpeed - nextTileLeft.getMovementCostForMovementType(movementType), movementType, currentTeam, reach, xRayUnits);
            }

            if (continueDown) {
                internalReachableTileRecursion(startX, newYDown, moveSpeed - nextTileDown.getMovementCostForMovementType(movementType), movementType, currentTeam, reach, xRayUnits);
            }

            if (continueRight) {
                internalReachableTileRecursion(newXRight, startY, moveSpeed - nextTileRight.getMovementCostForMovementType(movementType), movementType, currentTeam, reach, xRayUnits);
            }
        }
    }

    // Dec. 5, 2025
    // I've deleted method named "recursiveTruth()" at least
    // three times before today, and this represents the
    // fourth time it's shown back up throwing errors.
    // I know I write bad code when I'm drunk, but its usually
    // at least clear what I was going for and I otherwise
    // always remember writing it.
    // I have no idea what inspiration keeps hitting me and
    // then leaving as soon as I'm sober the next day, but
    // protected abstract Truth recursiveTruth(MemoryManager memMng);
    // has shown up repeatedly, only throwing errors at runtime
    // because both Truth and MemoryManager are unrecognized types
    // with no import reference. I can't remember what either is
    // supposed to be, or what the function is intended to do with
    // them, but clearly it's something good.
    // Next time I get drunk and come here to create recursiveTruth(),
    // note to self: comment the code, please. What should it do?

    // Dec. 15. 2025
    // I guess drunk me didn't real the last note.
    // Deleting the line again in the hopes that it
    // will prompt me to read the comment next time.

    @NotNull
    @Contract(pure = true)
    public OLD_Path shortestPath(@NotNull OLD_SimpleUnit unit, @NotNull OLD_LogicalTile destination, boolean continuous, boolean xRayUnits) {
        // Returns the shortest path for a given unit to another tile.

        // assume unlimited movement
        if(xRayUnits) {
            recursivelyXRayAll(unit);
        } else {
            recursivelySelectAll(unit);
        }

        // Can't believe we've lived like this for so long lol.

        ags.reachableTiles.add(unit.getOccupyingTile());

        pathFound = false;
        shortOLDPath = new OLD_Path(game, unit.getOccupyingTile());
        tileCheckedAtSpeed = new HashMap<>();

        if(unit.getOccupyingTile() == destination) return shortOLDPath;

//        Gdx.app.log("shortestPath", "starting bloom");

        Bloom(unit, destination, continuous);

//        Gdx.app.log("shortestPath", "bloomed");

        if(unit.getTeamAlignment() != TeamAlignment.PLAYER && shortOLDPath.size() > 1) {
            shortOLDPath.clearSeedTile();
//            Gdx.app.log("shortestPath", "cleared seed");
        }


//        Gdx.app.log("shortestPath", "returning");

        return shortOLDPath;

    }

    private void Bloom(OLD_SimpleUnit unit, OLD_LogicalTile destination, boolean continuous) {
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

        final Array<OLD_Path> paths = new Array<>();
        paths.add(new OLD_Path(game, unit.getOccupyingTile()));

        float lowestCost;
        boolean terminating = false;
        boolean reassign;

        do {

            final Array<Integer> indexesToRemove = new Array<>();
            final int loopBound = paths.size;

            for (int p = 0; p < loopBound; p++) {

                final OLD_Path OLDPath = new OLD_Path(paths.get(p));
                final float cost = OLDPath.cost(unit);

                if (OLDPath.lastTile().getColumnX() - 1 >= 0) {
                    OLD_LogicalTile nextTileLeft = ags.getLogicalMap().nextTileLeftFrom(OLDPath.lastTile());
                    if (ags.reachableTiles.contains(nextTileLeft, true)) {
                        final float newCost = cost+nextTileLeft.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileLeft) || tileCheckedAtSpeed.get(nextTileLeft) > newCost) {
                            tileCheckedAtSpeed.put(nextTileLeft, newCost);
                            if (!OLDPath.contains(nextTileLeft)) {

                                final OLD_Path branchingOLDPathLeft = new OLD_Path(OLDPath);
                                branchingOLDPathLeft.incorporateNextTile(Direction.WEST);
                                paths.add(branchingOLDPathLeft);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (OLDPath.lastTile().getColumnX() + 1 < ags.getLogicalMap().getTilesWide()) {
                    OLD_LogicalTile nextTileRight = ags.getLogicalMap().nextTileRightFrom(OLDPath.lastTile());
                    if (ags.reachableTiles.contains(nextTileRight, true)) {
                        final float newCost = cost+nextTileRight.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileRight) || tileCheckedAtSpeed.get(nextTileRight) > newCost) {
                            tileCheckedAtSpeed.put(nextTileRight, newCost);
                            if (!OLDPath.contains(nextTileRight)) {

                                final OLD_Path branchingOLDPathRight = new OLD_Path(OLDPath);
                                branchingOLDPathRight.incorporateNextTile(Direction.EAST);
                                paths.add(branchingOLDPathRight);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (OLDPath.lastTile().getRowY() - 1 >= 0) {
                    OLD_LogicalTile nextTileDown = ags.getLogicalMap().nextTileDownFrom(OLDPath.lastTile());
                    if (ags.reachableTiles.contains(nextTileDown, true)) {
                        final float newCost = cost+nextTileDown.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileDown) || tileCheckedAtSpeed.get(nextTileDown) > newCost) {
                            tileCheckedAtSpeed.put(nextTileDown, newCost);
                            if (!OLDPath.contains(nextTileDown)) {

                                final OLD_Path branchingOLDPathDown = new OLD_Path(OLDPath);
                                branchingOLDPathDown.incorporateNextTile(Direction.SOUTH);
                                paths.add(branchingOLDPathDown);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                if (OLDPath.lastTile().getRowY() + 1 < ags.getLogicalMap().getTilesHigh()) {
                    OLD_LogicalTile nextTileUp = ags.getLogicalMap().nextTileUpFrom(OLDPath.lastTile());
                    if (ags.reachableTiles.contains(nextTileUp, true)) {
                        final float newCost = cost+nextTileUp.getMovementCostForMovementType(unit.getMovementType());
                        if (!tileCheckedAtSpeed.containsKey(nextTileUp) || tileCheckedAtSpeed.get(nextTileUp) > newCost) {
                            tileCheckedAtSpeed.put(nextTileUp, newCost);
                            if (!OLDPath.contains(nextTileUp)) {

                                final OLD_Path branchingOLDPathUp = new OLD_Path(OLDPath);
                                branchingOLDPathUp.incorporateNextTile(Direction.NORTH);
                                paths.add(branchingOLDPathUp);

                            } // break: path already contains tile
                        } // break: tile already checked with fewer steps
                    } // break: not in reachableTiles
                } // break: out of map bounds

                indexesToRemove.add(p);

            }

            if(!terminating) terminating = (continuous ? closeEnough(paths, destination, unit) : containsTileInReachOf(paths, destination, unit.getSimpleReach(), unit));
            /* If a continuous path is requested, check if any path is within 1 tile of destination,
             * as the destination tile may never be in a path at this point due to being occupied already.
             *
             * If a continuous path is not requested, check for any tile within the unit's attack range of target.
             */

            lowestCost = 1000;

            reassign = false;

            if(terminating) {
                lowestCost = shortOLDPath.cost(unit);

                for(OLD_Path OLDPath : paths) {
                    if(OLDPath.cost(unit) < lowestCost) {
                        lowestCost = OLDPath.cost(unit);

                        reassign = (continuous ? validateClosePath(OLDPath, destination, unit) : validateDistantPath(OLDPath, destination, unit.getSimpleReach(), unit));
                    } else if (OLDPath.cost(unit) >= shortOLDPath.cost(unit)) {
                        indexesToRemove.add(paths.indexOf(OLDPath, true));
                    }
                }

            }

            // I'm currently working on refactoring this class for
            // the 0.2 rebuild, and damn if I am not amazed at what
            // I've been able to pull off with the sticks and glue
            // of coding knowledge that I have had to rely on these
            // past five or so years.
            // I really had no idea what I was doing, but I more or
            // less figured it out and got there on pure determination.
            // I'm sure I'll still make plenty of mistakes in the new
            // data, but damn it feels good to have come so far, and
            // to have the skills and knowledge that I have now.
            // God, I hope I live long enough to finish this video game.

            for (int i = paths.size + 1; i >= 0; i--) {
                if (indexesToRemove.contains(i, true)) {
                    if(!paths.get(i).equals(shortOLDPath)) paths.removeIndex(i);
                }
            }

        } while (!terminating || shortOLDPath.cost(unit) != lowestCost || reassign);


    }

    private boolean validateClosePath(OLD_Path OLDPath, OLD_LogicalTile destination, OLD_SimpleUnit unit) {
        final Array<OLD_Path> a = new Array<>();
        a.add(OLDPath);
        return closeEnough(a, destination, unit);
    }

    private boolean validateDistantPath(OLD_Path OLDPath, OLD_LogicalTile destination, int reach, OLD_SimpleUnit unit) {
        final Array<OLD_Path> a = new Array<>();
        a.add(OLDPath);
        return containsTileInReachOf(a, destination, reach, unit);
    }

    private boolean closeEnough(Array<OLD_Path> paths, OLD_LogicalTile destination, OLD_SimpleUnit unit) {

        pathFound = false;

        for (OLD_Path OLDPath : paths) {

                if(ags.getLogicalMap().distanceBetweenTiles(OLDPath.lastTile(), destination) <= 1) {
                    if(shortOLDPath.size() == 1 || OLDPath.cost(unit) < shortOLDPath.cost(unit)) {
                        shortOLDPath = new OLD_Path(OLDPath);
                        shortOLDPath.iDoThinkThatIKnowWhatIAmDoingAndSoIFeelQuiteComfortableArbitrarilyAddingThisTileToTheEndOfThisPath(destination);
                        pathFound = true;
                    }
                }

        }

        return pathFound;

    }

    private boolean containsTileInReachOf(@NotNull Array<OLD_Path> paths, OLD_LogicalTile destination, int reach, OLD_SimpleUnit unit) {

        pathFound = false;

        for (OLD_Path OLDPath : paths) {
                for (OLD_LogicalTile tile : OLDPath.retrievePath()) {
                    if (ags.getLogicalMap().distanceBetweenTiles(tile, destination) <= reach) {
                        if(shortOLDPath.size() == 1 || OLDPath.cost(unit) < shortOLDPath.cost(unit)) {
                            shortOLDPath = OLDPath;
                            pathFound = true;
                        }
                    }
                }

        }

        return pathFound;

    }
}
