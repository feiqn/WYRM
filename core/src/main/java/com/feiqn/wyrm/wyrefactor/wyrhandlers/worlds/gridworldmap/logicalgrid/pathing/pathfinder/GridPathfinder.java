package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

import java.util.HashMap;

public final class GridPathfinder /*extends WyrPathfinder*/ {

    private static WyrGrid grid = null;

    public GridPathfinder(WyrGrid grid) { GridPathfinder.grid = grid; }

    // Can be assumed he will always return the shortest
    // valid path for a given unit.

    public static GridPath toNearestAdjacentInRange(GridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestAdjacentInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestAdjacentInRange(GridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestAdjacentInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestAdjacentInRange(GridActor pathFor, int x, int y) {
        return null; // TODO
    }

    public static GridPath toNearestInRange(GridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestInRange(GridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestInRange(GridActor pathFor, int x, int y) {
        // Returns a path to the tile in range of pathFor which
        // is closest by raw distance to the target.
        // Get as close as possible to them when you can't
        // get all the way to them.
        // TODO:
        //  method to return a path to the tile in range of
        //  pathFor which is closest by traversability.
        //  would this encapsulate xRayPath?

        return null; // TODO
    }

    public static GridPath toNearestAvailableInRange(GridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestAvailableInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestAvailableInRange(GridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestAvailableInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestAvailableInRange(GridActor pathFor, int x, int y) {
        return null; // TODO
    }

    public static GridPath toFarthestAccessibleAlongShortestPath(GridTile start, GridTile finish, MovementType forType) {
        // call shortestPathTo
        // check if path is obstructed
        // trim to obstructions if needed
        // then return path
        return null;
    }

    private static GridPath shortestPathTo(GridUnit pathFor, GridTile finish) {

        // Always puts you on the finish tile, not next to it.
        // Will first try to find an unobstructed path, then if
        // one cannot be found, will automatically return an
        // ideal path ignoring obstructions.

        // Truncate return value after calling this method in most use cases.
        // TODO:
        //  Consider making this method private and wrapping in methods to
        //  trim and truncate per use case.

        boolean xRayActors = false;

        Things accessible;

        if(canGetTo(pathFor, finish)) {
            accessible = currentlyAccessibleTo(pathFor);
        } else if(couldGetTo(pathFor, finish)){
            xRayActors = true;
            accessible = potentiallyAccessibleTo(pathFor);
        } else {
            Gdx.app.log("Pathfinder", "No potential paths for " + pathFor.getName() + " to " + finish.getCoordinates());
            return null;
        }

        final Array<GridPath> paths = new Array<>();
        // Won't return out of bounds values, but will return potentially
        // inaccessible tiles, so we cross-reference against Things accessible
        // as a shortcut to calling canAccess() on every tile, which just
        // wraps currentlyAccessibleTo.
        for(GridTile tile : grid.allAdjacentTo(pathFor.occupyingTile())) {
            // accessible.tiles will contain tiles with obstructions if
            // xRayActors is true.
            if(accessible.tiles.contains(tile, true)) paths.add(new GridPath(tile));
        }
        // If no adjacent tiles are accessible, we can't move.
        if(paths.size == 0) return null;
        // If we're only moving one tile, we can stop and return now.
        for(GridPath firstPaths : paths) {
            if(!firstPaths.isObstructed()) {
                if(accessible.tiles.contains(firstPaths.lastTile(), true)) {
                    return firstPaths;
                }
            } else if(firstPaths.lastTile() == finish) {
                // Here, finish is right next to pathFor;
                // however, finish is obstructed, and
                // thus no path can be formed.
                return null;
            }
        }

        // At this point we can be assured that the destination
        // is at least potentially reachable, that there is at
        // least one adjacent tile accessible to path from, and
        // that none of the adjacent tiles are the destination.
        float lowestCost = 255;
        float cost = 255;
        boolean terminating;
        GridPath shortestPath = null;
        final Array<GridPath> pathsToRemove = new Array<>();
        final HashMap<GridTile, Float> tileCheckedAtSpeed = new HashMap<>();

        Gdx.app.log("Pathfinder", "It's not");
        do {
            // Bloom() goes here more or less
            pathsToRemove.clear();

            terminating = (shortestPath != null); // A path to finish has been found on a previous loop.

            // We can skip the first check by Bloom() since
            // out of bounds values won't be added to our tile
            // pool in this implementation.
            for(GridPath path : paths) {
                // Iterate on each path rather directly than
                // running multiple directional loops.
                cost = path.costFor(pathFor);

                final Array<GridTile> neighbors = grid.allAdjacentTo(path.lastTile());
                for(GridTile neighbor : neighbors) {
                    if(accessible.tiles.contains(neighbor, true)) {
                        // General accessibility
                        if(!neighbor.isOccupied() || xRayActors) {
                            // Make sure to check for occupied tiles in
                            // return paths as necessary when xRaying happens.
                            final float newCost = cost+neighbor.moveCostFor(pathFor.getMovementType());
                            if(!tileCheckedAtSpeed.containsKey(neighbor) || tileCheckedAtSpeed.get(neighbor) > newCost) {
                                tileCheckedAtSpeed.put(neighbor, newCost);
                                // Don't check a tile that's already
                                // been reached via a faster path;
                                // but also, overwrite that path if
                                // this one is shorter.
                                if(!path.contains(neighbor)) {
                                    // Don't loop tiles, Greg.
                                    // You're better than that.
                                    final GridPath branchingPath = new GridPath(path);
                                    branchingPath.append(neighbor);
                                    paths.add(branchingPath);

                                    if(neighbor == finish) {
                                        shortestPath = branchingPath;
                                        if(!terminating) terminating = true;
                                    }
                                }
                            }
                        }
                    }
                }
                // Insure we don't repeat finished checks.
                pathsToRemove.add(path);
            }

            if(terminating) {
                // A path has been found, but is it the shortest one?
                // This is how we account for the movement cost values
                // of tiles, rather than raw number of tiles in a path.
                lowestCost = shortestPath.costFor(pathFor);

                for(GridPath path : paths) {
                    if(path.costFor(pathFor) < lowestCost) {
                        // We still have paths that may
                        // potentially reach finish with
                        // a lower cost despite having
                        // more raw steps.
                        if(terminating) terminating = false;
                        // Don't break, in order to allow for
                        // continued removal of longer paths.
                    } else if(path.costFor(pathFor) >= lowestCost) {
                        // Remove any remaining paths
                        // which are already of a
                        // higher cost than shortestPath
                        pathsToRemove.add(path);
                    }
                }
            }

            // Clear out paths we're done with.
            for(GridPath path : pathsToRemove) {
                if(paths.contains(path, true))  paths.removeValue(path, true);
            }

            // If we have somehow hit a wall and there are no further paths to check,
            // we should just escape and return null.
            // This should never happen, but you never know.
            if(paths.size == 0) return null;

        } while(pathContainingTile(paths, finish) == null || !terminating);
        Gdx.app.log("Pathfinder", "over."); // Gonna do it right this time around.

        return shortestPath;
    }

    private static GridPath pathContainingTile(Array<GridPath> paths, GridTile tile) {
        // Return any path within the array that has the desired tile.
        for(GridPath path : paths) {
            if(path.contains(tile)) return path;
        }
        return null;
    }

    private static Things currentlyAccessibleTo(GridUnit unit) {
        return accessibleThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.getAlignment(), unit.getReach(), false, false);
    }
    private static Things currentlyAccessibleTo(GridTile start, float speed, MovementType movementType, TeamAlignment alignment, int reach) {
        return accessibleThings(start, speed, movementType, alignment, reach, false, false);
    }
    private static Things potentiallyAccessibleTo(GridUnit unit) {
        return potentiallyAccessibleTo(unit.occupyingTile(), unit.getMovementType(), unit.getAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(GridTile start, MovementType byType, TeamAlignment alignment, int reach) {
        return accessibleThings(start, 999, byType, alignment, reach, true, true);
    }
    private static Things accessibleThings(GridUnit unit, boolean xRayUnits, boolean xRayProps) {
        return accessibleThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.getAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }

    private static Things accessibleThings(final GridTile start, final float speed, final MovementType moveType, final TeamAlignment alignment, final int reach, final boolean xRayUnits, final boolean xRayProps) {
        final Things accessible = new Things();
        if(speed <= 0) return accessible; // I guess technically you can play with yourself if you want.

        // GridPaths come with a build in function to check
        // the cost value of the entire path to a tile on
        // the fly, which is helpful here.
        final Array<GridPath> paths = new Array<>();
        final Array<GridPath> pathsToRemove = new Array<>();
        final Array<GridPath> newPaths = new Array<>();
        final Array<GridTile> adjacentTiles = new Array<>();
        final HashMap<GridTile, Float> tileCheckedAtSpeed = new HashMap<>();

        // Occupied tiles will also populate withing things.tiles,
        // uses purgeOccupiedTiles() at end if shouldn't xRay.
        accessible.tiles.addAll(grid.allAdjacentTo(start));

        if(accessible.tiles.size == 0) return accessible;

        // TODO:
        //  account for aerials in airspace.

        // First check is a little different, I think? Maybe?
        for(GridTile tile : accessible.tiles) {
            if(tile.isOccupied()) {
                switch(tile.occupier().getAlignment()) {
                    case PLAYER:
                        if(!accessible.friends.contains(tile.occupier(), true)) {
                            accessible.friends.add(tile.occupier());
                        }
                        break;
                    case ENEMY:
                        if(!accessible.enemies.contains(tile.occupier(), true)) {
                            accessible.enemies.add(tile.occupier());
                        }
                        break;
                    case ALLY:
                        if(!accessible.allies.contains(tile.occupier(), true)) {
                            accessible.allies.add(tile.occupier());
                        }
                        break;
                    case OTHER:
                        if(!accessible.strangers.contains(tile.occupier(), true)) {
                            accessible.strangers.add(tile.occupier());
                        }
                        break;
                }
            }
            if(tile.hasProp()) {
                if(!accessible.props.contains(tile.getProp(), true)) accessible.props.add(tile.getProp());
            }

            if(!tile.isOccupied() || xRayUnits) {
                paths.add(new GridPath(tile));
            } else if(tile.isOccupied() && !xRayUnits) {
                accessible.tiles.removeValue(tile, true);

//              final Array<GridTile> unoccupied = purgeOccupiedTiles(accessible.tiles);
//              accessible.tiles.clear();
//              accessible.tiles.addAll(unoccupied);

            }
        }

        // Array paths has been seeded, adjacent tiles have had
        // their Actors cataloged, and occupied tiles have
        // been purged if !xRayUnits.

        // I think I'm sun-downing on this one.

        boolean somethingWasAdded;
        do {
            somethingWasAdded = false;
            pathsToRemove.clear();

            // TODO:
            //  account for units or tiles turned solid,
            //  as well as solid props like doors.

            for(GridPath path : paths) {
                final float currentPathCost = path.costFor(moveType);

                if(currentPathCost > speed) break; // How did you even get here?

                newPaths.clear();
                adjacentTiles.clear();

                adjacentTiles.addAll(grid.allAdjacentTo(path.lastTile()));
                for(GridTile newTile : adjacentTiles) {
                    final float newCost = currentPathCost + newTile.moveCostFor(moveType);
                    // Only include the newTile if walking to it wouldn't break
                    // the speed budget; then account for reach.
                    if(newCost <= speed) {
                        if(!newTile.isOccupied() || xRayUnits) {
                            final GridPath newPath = new GridPath(path);
                            newPath.append(newTile);
                            newPaths.add(newPath);

                            if(!somethingWasAdded) somethingWasAdded = true;
                        }
                    } else {
                        // Since we can't reach the next tile, we can go
                        // ahead and check for any interactables within
                        // reach at this extreme.
                        for(GridTile reachableTile : grid.tilesWithinDistanceOf(reach, start)) {
                            // TODO: pull out modular method to add things to accessible
                            if(reachableTile.isOccupied()) {
                                for(WyrInteraction interaction : reachableTile.getInteractables()) {
                                    if(interaction.getInteractableRange() <= reach) {
                                        // TODO: create a running list of interactables to
                                        //  populate from.
                                    }
                                }
                            }
                            if(reachableTile.hasProp()) {

                            }
                        }
                    }

                    if(newTile.isOccupied()) {
                        switch(newTile.occupier().getAlignment()) {
                            case PLAYER:
                                if(!accessible.friends.contains(newTile.occupier(), true)) {
                                    accessible.friends.add(newTile.occupier());
                                    if(!somethingWasAdded) somethingWasAdded = true;
                                }
                                break;
                            case ENEMY:
                                if(!accessible.enemies.contains(newTile.occupier(), true)) {
                                    accessible.enemies.add(newTile.occupier());
                                    if(!somethingWasAdded) somethingWasAdded = true;
                                }
                                break;
                            case ALLY:
                                if(!accessible.allies.contains(newTile.occupier(), true)) {
                                    accessible.allies.add(newTile.occupier());
                                    if(!somethingWasAdded) somethingWasAdded = true;
                                }
                                break;
                            case OTHER:
                                if(!accessible.strangers.contains(newTile.occupier(), true)) {
                                    accessible.strangers.add(newTile.occupier());
                                    if(!somethingWasAdded) somethingWasAdded = true;
                                }
                                break;
                        }
                    }
                    if(newTile.hasProp()) {
                        // TODO: account for prop's interactability range (per interactable.)
                        if(!accessible.props.contains(newTile.getProp(), true)) {
                            accessible.props.add(newTile.getProp());
                            if(!somethingWasAdded) somethingWasAdded = true;
                        }
                    }
                }

                // TODO: stopping here, come back to this line later and check existing logic above.

//                for(GridTile tile : path.getPath()) {
//                    final float newCost = currentPathCost +
//                    if(!tileCheckedAtSpeed.containsKey(tile) || tileCheckedAtSpeed.get(tile) < speed) {
//                        tileCheckedAtSpeed.put(tile, speed);
//                        if(!tile.isOccupied() || xRayUnits) {
//
//                        }
//                    }
//                }
            }

            // Clear out paths we're done with.
            for(GridPath path : pathsToRemove) {
                if(paths.contains(path, true))  paths.removeValue(path, true);
            }

        } while(somethingWasAdded);


        return accessible;
    }

    private static Array<GridTile> purgeOccupiedTiles(Array<GridTile> tiles) {
        for(GridTile tile : tiles) {
            if(tile.isOccupied()) tiles.removeValue(tile, true);
        }
        return tiles;
    }

    public static int turnsToReach(GridTile destination, GridUnit pathFor) {
        return 1;
    }

    /**
     * These functions should be called in simple, one-off use cases, not iterated
     * upon in loops, ideally. Ultimately it's fine if they are looped, however it
     * represents a micro-inefficiency in most cases where currentlyAccessibleTo()
     * could be called once directly , and its return values then iterated upon.
     */
    public static boolean canGetTo(GridUnit unit, GridTile tile) {
        return currentlyAccessibleTo(unit).tiles.contains(tile, true);
    }
    public static boolean couldGetTo(GridUnit unit, GridTile tile) {
        return potentiallyAccessibleTo(unit).tiles.contains(tile, true);
    }

//    public static boolean canReach(GridUnit unit, GridTile tile) {
//
//        return false;
//    }
//    public static boolean couldReach(GridUnit unit, GridTile tile) {
//
//        return false;
//    }



    private static final class Things {
        private final Array<GridTile> tiles     = new Array<>();
        private final Array<GridProp> props     = new Array<>();
        private final Array<GridUnit> enemies   = new Array<>();
        private final Array<GridUnit> allies    = new Array<>();
        private final Array<GridUnit> strangers = new Array<>();
        private final Array<GridUnit> friends   = new Array<>();

        public Things() {}
    }
}
