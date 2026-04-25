package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid.RPGridStats.RPGStatType;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.RPGridMapHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import java.util.HashMap;

public final class GridPathfinder /*extends WyrPathfinder*/ {

    private GridPathfinder() {}

    // Can be assumed he will always return the shortest
    // valid path for a given movement type.

    public static GridPath toNearestAdjacentInRange(RPGridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestAdjacentInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestAdjacentInRange(RPGridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestAdjacentInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestAdjacentInRange(RPGridActor pathFor, int x, int y) {
        return null; // TODO
    }

    public static GridPath toNearestInRange(RPGridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestInRange(RPGridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestInRange(RPGridActor pathFor, int x, int y) {
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

    public static GridPath toNearestAvailableInRange(RPGridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestAvailableInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestAvailableInRange(RPGridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestAvailableInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestAvailableInRange(RPGridActor pathFor, int x, int y) {
        return null; // TODO
    }

//    public static GridPath toFarthestAccessibleAlongShortestPath(GridTile start, GridTile finish, MovementType forType) {
//        // call shortestPathTo
//        // check if path is obstructed
//        // trim to obstructions if needed
//        // then return path
//        return null;
//    }

//    private static GridPath pathContainingTile(Array<GridPath> paths, GridTile tile) {
//        // Return any path within the array that has the desired tile.
//        for(GridPath path : paths) {
//            if(path.contains(tile)) return path;
//        }
//        return null;
//    }
//    private abstract recursiveTruth() {}

    public static Things reachableFromTile(RPGridMapHandler grid, GridTile tile, RPGridUnit forUnit) {
        return thingsInReachOfTile(grid, tile, forUnit.getReach());
    }

    public static Things currentlyAccessibleTo(RPGridMapHandler grid, RPGridUnit unit) {
        return reachableThings(grid, unit.getOccupiedTile(), unit.getModifiedStatValue(RPGStatType.SPEED), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach(), false, false);
    }
    private static Things currentlyAccessibleTo(RPGridMapHandler grid, GridTile start, float speed, RPGridMovementType RPGridMovementType, TeamAlignment alignment, int reach) {
        return reachableThings(grid, start, speed, RPGridMovementType, alignment, reach, false, false);
    }
    public static Things potentiallyAccessibleTo(RPGridMapHandler grid, RPGridUnit unit) {
        return potentiallyAccessibleTo(grid, unit.getOccupiedTile(), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(RPGridMapHandler grid, GridTile start, RPGridMovementType byType, TeamAlignment alignment, int reach) {
        return reachableThings(grid, start, 999, byType, alignment, reach, true, true);
    }
    private static Things reachableThings(RPGridMapHandler grid, RPGridUnit unit, boolean xRayUnits, boolean xRayProps) {
        return reachableThings(grid, unit.getOccupiedTile(), unit.getModifiedStatValue(RPGStatType.SPEED), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }
    private static Things reachableThings(RPGridMapHandler grid, final GridTile start, final float speed, final RPGridMovementType moveType, final TeamAlignment alignment, final int reach, final boolean xRayUnits, final boolean xRayProps) {
        final Things reachable = new Things();
        // If we can't move, we can still
        // return things reachable from
        // where we already are.
        if(speed <= 0) return thingsInReachOfTile(grid, start, reach);

//        final Array<GridPath> paths = new Array<>();
//        final Array<GridPath> pathsToRemove = new Array<>();
//        final Array<GridPath> newPaths = new Array<>();
//        final Array<GridTile> adjacentTiles = new Array<>();

        // First loop, grab all tiles adjacent to start,
        // iterate through them, grabbing actors, as well
        // as grabbing any tiles available to continue pathing from.
        // Doing this first loop outside the main recursion keeps
        // things a little cleaner and neater overall.
        for(GridTile tile : grid.allAdjacentTo(start)) {
            final GridPath path = new GridPath(tile);
            if(tile.hasProp()) reachable.added(tile.prop(), path, moveType);
            if(tile.isOccupied()) reachable.added(tile.occupier(), path, moveType);
            if(!tile.isTraversableBy(moveType)) continue;
            if(!tile.isOccupied()
                || xRayUnits
                || teamCanPass(alignment, tile.occupier().getTeamAlignment())) {
                    reachable.added(tile, path, moveType);
//                    paths.add(path);
            }
        }

        if(reachable.tiles().isEmpty()) {
            // No tiles we can move to, bail out and return
            // things reachable from start.,=
            return thingsInReachOfTile(grid, start, reach);
        }

        // TODO:
        //  account for aerials in airspace.

        boolean somethingWasAdded;

        do { // TODO: either multithread this or take it out of the do-while, it's causing lag
            somethingWasAdded = false;

            // TODO:
            //  account for units or tiles turned solid,
            //  as well as solid props like doors.

            final Array<GridPath> paths = new Array<>();
            for(GridPath path : reachable.tiles.values()) {
                paths.add(path);
            }

            for(GridPath path : paths) {
                final float currentPathCost = path.costFor(moveType);

                if(currentPathCost > speed) break; // How did you even get here?

                for(GridTile newTile : grid.allAdjacentTo(path.lastTile())) {
                    if(path.contains(newTile)) continue;
                    if(!newTile.isTraversableBy(moveType)) continue;

                    final float newCost = currentPathCost + newTile.moveCostFor(moveType);
                    // Only include the newTile if walking to it wouldn't break
                    // the speed budget; then account for reach.
                    if(newCost <= speed) {
                        if(!newTile.isOccupied()
                            || xRayUnits
                            || teamCanPass(alignment, newTile.occupier().getTeamAlignment())) {
                                boolean added;

                                final GridPath branchingPath = new GridPath(path);
                                branchingPath.append(newTile);

                                added = reachable.added(newTile, branchingPath, moveType);
//                                for(GridActor actor : thingsInReachOf(grid, newTile, reach).actors()) {
//                                    final boolean a = reachable.added(actor, branchingPath, moveType);
//                                    if(!added) added = a;
//                                }

                                if(!somethingWasAdded) somethingWasAdded = added;
                        } else {
                            // Add unit blocking tile to Things
                            final GridPath branchingPath = new GridPath(path);
                            branchingPath.append(newTile);

                            final boolean added = reachable.added(newTile.occupier(), branchingPath, moveType);

                            if(!somethingWasAdded) somethingWasAdded = added;
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
//                        final Things reachableThings = thingsInReachOf(grid, newTile, reach);
//                        for(GridActor actor : reachableThings.actors()) {
//                            reachable.add(actor, path, moveType);
//                            somethingWasAdded = true;
//                        }
                    }
                }

//                paths.removeValue(path, true);
//                pathsToRemove.add(path);
                // Add new paths we should check.
//                for(GridPath pathN : newPaths) {
//                    paths.add(pathN);
//                }

            }

            // Clear out paths we're done with.
//            for(GridPath path : pathsToRemove) {
//                if(paths.contains(path, true)) paths.removeValue(path, true);
//            }

        } while(somethingWasAdded);

        return reachable;
    }

    private static Things thingsInReachOfTile(RPGridMapHandler grid, GridTile tile, int reach) {
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

    public static int turnsToReach(GridTile destination, RPGridUnit pathFor) {
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

    public static boolean teamCanPass(TeamAlignment alignment, TeamAlignment teamAlignment) {
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
        private final HashMap<RPGridProp, GridPath> props     = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> enemies   = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> allies    = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> strangers = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> players = new HashMap<>();

        public Things() {}

        public boolean added(GridTile tile, GridPath path, RPGridMovementType forType) {
            if(!tiles.containsKey(tile) || tiles.get(tile).costFor(forType) > path.costFor(forType)) {
                add(tile, path);
                return true;
            }
            return false;
        }

        public boolean added(RPGridActor actor, GridPath path, RPGridMovementType forType) {
            switch(actor.getActorType()) {
                case PROP:
                    assert actor instanceof RPGridProp;
                    if(!props.containsKey(actor) || props.get(actor).costFor(forType) > path.costFor(forType)) {
                        add(actor, path);
                        return true;
                    }
                    break;
                case UNIT:
                    assert actor instanceof RPGridUnit;
                    switch(((RPGridUnit) actor).getTeamAlignment()) {
                        case PLAYER:
                            if(!players.containsKey(actor) || players.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                                return true;
                            }
                            break;
                        case ALLY:
                            if(!allies.containsKey(actor) || allies.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                                return true;
                            }
                            break;
                        case ENEMY:
                            if(!enemies.containsKey(actor) || enemies.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                                return true;
                            }
                            break;
                        case OTHER:
                            if(!strangers.containsKey(actor) || strangers.get(actor).costFor(forType) > path.costFor(forType)) {
                                add(actor, path);
                                return true;
                            }
                            break;
                    }
                    break;
            }
            return false;
        }

        private void add(GridTile tile, GridPath shortestPathTo) {
            tiles.put(tile, shortestPathTo);
        }

        public void add(RPGridActor actor, GridPath shortestPathTo) {
            switch(actor.getActorType()) {
                case PROP:
                    assert actor instanceof RPGridProp;
                    props.put((RPGridProp) actor, shortestPathTo);
                    break;

                case UNIT:
                    assert actor instanceof RPGridUnit;
                    switch(((RPGridUnit) actor).getTeamAlignment()) {
                        case PLAYER:
                            players.put((RPGridUnit) actor, shortestPathTo);
                            break;

                        case ALLY:
                            allies.put((RPGridUnit) actor, shortestPathTo);
                            break;

                        case ENEMY:
                            enemies.put((RPGridUnit) actor, shortestPathTo);
                            break;

                        case OTHER:
                            strangers.put((RPGridUnit) actor, shortestPathTo);
                            break;
                    }
                    break;
            }
        }

        public Array<WyrInteraction<?,?>> interactables() {
            final Array<WyrInteraction<?,?>> returnValue = new Array<>();
            for(WyrActor<?,?,?, ?> actor : actors()) {
                returnValue.addAll(actor.getInteractions());
            }
            return returnValue;
        }
        public Array<RPGridActor> actors() {
            final Array<RPGridActor> returnValue = new Array<>();
            for(RPGridProp prop : props.keySet()) {
                returnValue.add(prop);
            }
            for(RPGridUnit friend : players.keySet()) {
                returnValue.add(friend);
            }
            for(RPGridUnit enemy : enemies.keySet()) {
                returnValue.add(enemy);
            }
            for(RPGridUnit stranger : strangers.keySet()) {
                returnValue.add(stranger);
            }
            return returnValue;
        }
        public HashMap<RPGridProp, GridPath> props() { return props; }
        public HashMap<GridTile, GridPath> tiles() { return tiles; }
        public HashMap<RPGridUnit, GridPath> allies() { return allies; }
        public HashMap<RPGridUnit, GridPath> enemies() { return enemies; }
        public HashMap<RPGridUnit, GridPath> players() { return players; }
        public HashMap<RPGridUnit, GridPath> strangers() { return strangers; }
    }
}
