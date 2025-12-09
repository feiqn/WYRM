package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

import java.util.HashMap;

public class GridPathfinder /*extends WyrPathfinder*/ {

    private static WyrGrid grid = null;

    public GridPathfinder(WyrGrid grid) { GridPathfinder.grid = grid; }

    // Can be assumed he will always return the shortest
    // valid path for a given movement type.

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

//    public static GridPath toFarthestAccessibleAlongShortestPath(GridTile start, GridTile finish, MovementType forType) {
//        // call shortestPathTo
//        // check if path is obstructed
//        // trim to obstructions if needed
//        // then return path
//        return null;
//    }

//    private static GridPath shortestPathTo(GridUnit pathFor, GridTile finish) {
//
//        // Always puts you on the finish tile, not next to it.
//        // Will first try to find an unobstructed path, then if
//        // one cannot be found, will automatically return an
//        // ideal path ignoring obstructions.
//
//        // Truncate return value after calling this method in most use cases.
//        // TODO:
//        //  Consider making this method private and wrapping in methods to
//        //  trim and truncate per use case.
//
//        boolean xRayActors = false;
//
//        Things accessible;
//
//        if(canGetTo(pathFor, finish)) {
//            accessible = currentlyAccessibleTo(pathFor);
//        } else if(couldGetTo(pathFor, finish)){
//            xRayActors = true;
//            accessible = potentiallyAccessibleTo(pathFor);
//        } else {
//            Gdx.app.log("Pathfinder", "No potential paths for " + pathFor.getName() + " to " + finish.getCoordinates());
//            return null;
//        }
//
//        final Array<GridPath> paths = new Array<>();
//        // Won't return out of bounds values, but will return potentially
//        // inaccessible tiles, so we cross-reference against Things accessible
//        // as a shortcut to calling canAccess() on every tile, which just
//        // wraps currentlyAccessibleTo.
//        for(GridTile tile : grid.allAdjacentTo(pathFor.occupyingTile())) {
//            // accessible.tiles will contain tiles with obstructions if
//            // xRayActors is true.
//            if(accessible.tiles.contains(tile, true)) paths.add(new GridPath(tile));
//        }
//        // If no adjacent tiles are accessible, we can't move.
//        if(paths.size == 0) return null;
//        // If we're only moving one tile, we can stop and return now.
//        for(GridPath firstPaths : paths) {
//            if(!firstPaths.isObstructed()) {
//                if(accessible.tiles.contains(firstPaths.lastTile(), true)) {
//                    return firstPaths;
//                }
//            } else if(firstPaths.lastTile() == finish) {
//                // Here, finish is right next to pathFor;
//                // however, finish is obstructed, and
//                // thus no path can be formed.
//                return null;
//            }
//        }
//
//        // At this point we can be assured that the destination
//        // is at least potentially reachable, that there is at
//        // least one adjacent tile accessible to path from, and
//        // that none of the adjacent tiles are the destination.
//        float lowestCost = 255;
//        float cost = 255;
//        boolean terminating;
//        GridPath shortestPath = null;
//        final Array<GridPath> pathsToRemove = new Array<>();
//        final HashMap<GridTile, Float> tileCheckedAtSpeed = new HashMap<>();
//
//        Gdx.app.log("Pathfinder", "It's not");
//        do {
//            // Bloom() goes here more or less
//            pathsToRemove.clear();
//
//            terminating = (shortestPath != null); // A path to finish has been found on a previous loop.
//
//            // We can skip the first check by Bloom() since
//            // out of bounds values won't be added to our tile
//            // pool in this implementation.
//            for(GridPath path : paths) {
//                // Iterate on each path rather directly than
//                // running multiple directional loops.
//                cost = path.costFor(pathFor);
//
//                final Array<GridTile> neighbors = grid.allAdjacentTo(path.lastTile());
//                for(GridTile neighbor : neighbors) {
//                    if(accessible.tiles.contains(neighbor, true)) {
//                        // General accessibility
//                        if(!neighbor.isOccupied() || xRayActors) {
//                            // Make sure to check for occupied tiles in
//                            // return paths as necessary when xRaying happens.
//                            final float newCost = cost+neighbor.moveCostFor(pathFor.getMovementType());
//                            if(!tileCheckedAtSpeed.containsKey(neighbor) || tileCheckedAtSpeed.get(neighbor) > newCost) {
//                                tileCheckedAtSpeed.put(neighbor, newCost);
//                                // Don't check a tile that's already
//                                // been reached via a faster path;
//                                // but also, overwrite that path if
//                                // this one is shorter.
//                                if(!path.contains(neighbor)) {
//                                    // Don't loop tiles, Greg.
//                                    // You're better than that.
//                                    final GridPath branchingPath = new GridPath(path);
//                                    branchingPath.append(neighbor);
//                                    paths.add(branchingPath);
//
//                                    if(neighbor == finish) {
//                                        shortestPath = branchingPath;
//                                        if(!terminating) terminating = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                // Insure we don't repeat finished checks.
//                pathsToRemove.add(path);
//            }
//
//            if(terminating) {
//                // A path has been found, but is it the shortest one?
//                // This is how we account for the movement cost values
//                // of tiles, rather than raw number of tiles in a path.
//                lowestCost = shortestPath.costFor(pathFor);
//
//                for(GridPath path : paths) {
//                    if(path.costFor(pathFor) < lowestCost) {
//                        // We still have paths that may
//                        // potentially reach finish with
//                        // a lower cost despite having
//                        // more raw steps.
//                        if(terminating) terminating = false;
//                        // Don't break, in order to allow for
//                        // continued removal of longer paths.
//                    } else if(path.costFor(pathFor) >= lowestCost) {
//                        // Remove any remaining paths
//                        // which are already of a
//                        // higher cost than shortestPath
//                        pathsToRemove.add(path);
//                    }
//                }
//            }
//
//            // Clear out paths we're done with.
//            for(GridPath path : pathsToRemove) {
//                if(paths.contains(path, true))  paths.removeValue(path, true);
//            }
//
//            // If we have somehow hit a wall and there are no further paths to check,
//            // we should just escape and return null.
//            // This should never happen, but you never know.
//            if(paths.size == 0) return null;
//
//        } while(pathContainingTile(paths, finish) == null || !terminating);
//        Gdx.app.log("Pathfinder", "over."); // Gonna do it right this time around.
//
//        return shortestPath;
//    }

//    private static GridPath pathContainingTile(Array<GridPath> paths, GridTile tile) {
//        // Return any path within the array that has the desired tile.
//        for(GridPath path : paths) {
//            if(path.contains(tile)) return path;
//        }
//        return null;
//    }

    public static Things currentlyAccessibleTo(GridUnit unit) {
        return reachableThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.teamAlignment(), unit.getReach(), false, false);
    }
    private static Things currentlyAccessibleTo(GridTile start, float speed, MovementType movementType, TeamAlignment alignment, int reach) {
        return reachableThings(start, speed, movementType, alignment, reach, false, false);
    }
    public static Things potentiallyAccessibleTo(GridUnit unit) {
        return potentiallyAccessibleTo(unit.occupyingTile(), unit.getMovementType(), unit.teamAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(GridTile start, MovementType byType, TeamAlignment alignment, int reach) {
        return reachableThings(start, 999, byType, alignment, reach, true, true);
    }
    private static Things reachableThings(GridUnit unit, boolean xRayUnits, boolean xRayProps) {
        return reachableThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.teamAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }
    private static Things reachableThings(final GridTile start, final float speed, final MovementType moveType, final TeamAlignment alignment, final int reach, final boolean xRayUnits, final boolean xRayProps) {
        final Things reachable = new Things();
        // If we can't move, we can still
        // return things reachable from
        // where we already are.
        if(speed <= 0) return thingsInReachOf(start, reach);

        final Array<GridPath> paths = new Array<>();
        final Array<GridPath> pathsToRemove = new Array<>();
        final Array<GridPath> newPaths = new Array<>();
        final Array<GridTile> adjacentTiles = new Array<>();

        // First loop, grab all tiles adjacent to start,
        // iterate through them, grabbing actors, as well
        // as grabbing any tiles available to continue pathing from.
        // Doing this first loop outside the main recursion keeps
        // things a little cleaner and neater overall.
        for(GridTile tile : grid.allAdjacentTo(start)) {
            final GridPath path = new GridPath(tile);
            if(tile.hasProp()) reachable.add(tile.prop(), path, moveType);
            if(tile.isOccupied()) reachable.add(tile.occupier(), path, moveType);
            if(!tile.isOccupied()
                || xRayUnits
                || canPass(alignment, tile.occupier().teamAlignment())) {
                    reachable.add(tile, path, moveType);
                    paths.add(path);
            }
        }

        if(reachable.tiles().isEmpty()) {
            // No tiles we can move to, bail out and return
            // things reachable from start.,=
            return thingsInReachOf(start, reach);
        }

        // TODO:
        //  account for aerials in airspace.

        boolean somethingWasAdded;
        do {
            somethingWasAdded = false;
            pathsToRemove.clear();

            // TODO:
            //  account for units or tiles turned solid,
            //  as well as solid props like doors.

            for(GridPath path : paths) {
                final float currentPathCost = path.costFor(moveType);

                if(currentPathCost >= speed) break; // How did you even get here?

                newPaths.clear();
                adjacentTiles.clear();

                adjacentTiles.addAll(grid.allAdjacentTo(path.lastTile()));
                for(GridTile newTile : adjacentTiles) {
                    if(path.contains(newTile)) continue;

                    final float newCost = currentPathCost + newTile.moveCostFor(moveType);
                    // Only include the newTile if walking to it wouldn't break
                    // the speed budget; then account for reach.
                    if(newCost <= speed) {
                        if(!newTile.isOccupied()
                            || xRayUnits
                            || canPass(alignment, newTile.occupier().teamAlignment())) {
                                final GridPath branchingPath = new GridPath(path);
                                branchingPath.append(newTile);
                                newPaths.add(branchingPath);

                                reachable.add(newTile, branchingPath, moveType);
                                for(GridActor actor : thingsInReachOf(newTile, reach).actors()) {
                                    reachable.add(actor, branchingPath, moveType);
                                }

                                if(!somethingWasAdded) somethingWasAdded = true;
                        }
                    } else {
                        // Since we can't reach the next tile, we can go
                        // ahead and check for any interactable within
                        // reach at this extreme.
                        // TODO:
                        //  Keep an eye on behavior when interacting with
                        //  things that should be interactable at a distance,
                        //  particularly when they can or should also be walked
                        //  up to directly or at range 0 (on top of) instead.
                        final Things reachableThings = thingsInReachOf(newTile, reach);
                        for(GridActor actor : reachableThings.actors()) {
                            reachable.add(actor, path, moveType);
                            somethingWasAdded = true;
                        }
                    }
                }
            }

            // Clear out paths we're done with.
            for(GridPath path : pathsToRemove) {
                if(paths.contains(path, true)) paths.removeValue(path, true);
            }
            // Add new paths we should check.
            for(GridPath pathN : newPaths) {
                paths.add(pathN);
            }
        } while(somethingWasAdded);

        // No idea if this will work.

        return reachable;
    }

    private static Things thingsInReachOf(GridTile tile, int reach) {
        final Things reachable = new Things();

        // TODO:
        //  account for airspace and flyers

        reachable.add(tile, new GridPath(tile));
        if(tile.isOccupied()) reachable.add(tile.occupier(), new GridPath(tile));
        if(tile.hasProp()) reachable.add(tile.prop(), new GridPath(tile));

        for(GridTile t : grid.tilesWithinDistanceOf(reach, tile)) {
            if(t.isOccupied()) reachable.add(t.occupier(), new GridPath(tile));
            if(t.hasProp()) reachable.add(t.prop(), new GridPath(t));
        }
        return reachable;
    }

//    private static Array<GridTile> purgeOccupiedTiles(Array<GridTile> tiles) {
//        for(GridTile tile : tiles) {
//            if(tile.isOccupied()) tiles.removeValue(tile, true);
//        }
//        return tiles;
//    }

    public static int turnsToReach(GridTile destination, GridUnit pathFor) {
        return 1; // TODO
    }


    /**
     * These functions should be called in simple, one-off use cases, not iterated
     * upon in loops, ideally. Ultimately it's fine if they are looped, however it
     * represents a micro-inefficiency in most cases where currentlyAccessibleTo()
     * could be called once directly , and its return values then iterated upon.
     */
//    public static boolean canGetTo(GridUnit unit, GridTile tile) {
//        for(GridTile t : currentlyAccessibleTo(unit).tiles.keySet()) {
//            // TODO: watch here for error, may need to compare coordinate value instead
//            if(t == tile) return true;
//        }
//        return false;
//    }
//    public static boolean couldGetTo(GridUnit unit, GridTile tile) {
//        for(GridTile t : potentiallyAccessibleTo(unit).tiles.keySet()) {
//            if(t == tile) return true;
//        }
//        return false;
//    }

//    public static boolean canReach(GridUnit unit, GridTile tile) {
//
//        return false;
//    }
//    public static boolean couldReach(GridUnit unit, GridTile tile) {
//
//        return false;
//    }

    public static boolean canPass(TeamAlignment alignment, TeamAlignment teamAlignment) {
        if(alignment == null || teamAlignment == null) return false;
        if(alignment == teamAlignment) return true;
        switch(alignment) {
            case PLAYER:
            case ALLY:
                switch(teamAlignment) {
                    case PLAYER:
                    case ALLY:
                        return true;

                    case OTHER:
                    case ENEMY:
                    default:
                        return false;
                }

            case ENEMY:
            case OTHER:
            default:
                return false;
        }
    }



    public static final class Things {
        private final HashMap<GridTile, GridPath> tiles     = new HashMap<>();
        private final HashMap<GridProp, GridPath> props     = new HashMap<>();
        private final HashMap<GridUnit, GridPath> enemies   = new HashMap<>();
        private final HashMap<GridUnit, GridPath> allies    = new HashMap<>();
        private final HashMap<GridUnit, GridPath> strangers = new HashMap<>();
        private final HashMap<GridUnit, GridPath> friends   = new HashMap<>();

        public Things() {}

        public void add(GridTile tile, GridPath path, MovementType forType) {
            if(!tiles.containsKey(tile) || tiles.get(tile).costFor(forType) > path.costFor(forType)) {
                add(tile, path);
            }
        }

        public void add(GridActor actor, GridPath path, MovementType forType) {
            switch(actor.getActorType()) {
                case PROP:
                    assert actor instanceof GridProp;
                    if(!props.containsKey(actor) || props.get(actor).costFor(forType) > path.costFor(forType)) {
                        add(actor, path);
                    }
                    break;
                case UNIT:
                    assert actor instanceof GridUnit;
                    switch(((GridUnit) actor).teamAlignment()) {
                        case PLAYER:
                            if(!friends.containsKey(actor) || friends.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                            }
                            break;
                        case ALLY:
                            if(!allies.containsKey(actor) || allies.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                            }
                            break;
                        case ENEMY:
                            if(!enemies.containsKey(actor) || enemies.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                            }
                            break;
                        case OTHER:
                            if(!strangers.containsKey(actor) || strangers.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                            }
                            break;
                    }
                    break;
            }
        }

        public void add(GridTile tile, GridPath shortestPathTo) {
            tiles.put(tile, shortestPathTo);
        }

        public void add(GridActor actor, GridPath shortestPathTo) {
            switch(actor.getActorType()) {
                case PROP:
                    assert actor instanceof GridProp;
                    props.put((GridProp) actor, shortestPathTo);
                    break;

                case UNIT:
                    assert actor instanceof GridUnit;
                    switch(((GridUnit) actor).teamAlignment()) {
                        case PLAYER:
                            friends.put((GridUnit) actor, shortestPathTo);
                            break;

                        case ALLY:
                            allies.put((GridUnit) actor, shortestPathTo);
                            break;

                        case ENEMY:
                            enemies.put((GridUnit) actor, shortestPathTo);
                            break;

                        case OTHER:
                            strangers.put((GridUnit) actor, shortestPathTo);
                            break;
                    }
                    break;
            }
        }

        public Array<WyrInteraction> interactables() {
            final Array<WyrInteraction> returnValue = new Array<>();
            for(WyrActor actor : actors()) {
                returnValue.addAll(actor.getInteractables());
            }
            return returnValue;
        }
        public Array<GridActor> actors() {
            final Array<GridActor> returnValue = new Array<>();
            for(GridProp prop : props.keySet()) {
                returnValue.add(prop);
            }
            for(GridUnit friend : friends.keySet()) {
                returnValue.add(friend);
            }
            for(GridUnit enemy : enemies.keySet()) {
                returnValue.add(enemy);
            }
            for(GridUnit stranger : strangers.keySet()) {
                returnValue.add(stranger);
            }
            return returnValue;
        }
        public HashMap<GridProp, GridPath> props() { return props; }
        public HashMap<GridTile, GridPath> tiles() { return tiles; }
        public HashMap<GridUnit, GridPath> allies() { return allies; }
        public HashMap<GridUnit, GridPath> enemies() { return enemies; }
        public HashMap<GridUnit, GridPath> friends() { return friends; }
        public HashMap<GridUnit, GridPath> strangers() { return strangers; }
    }
}
