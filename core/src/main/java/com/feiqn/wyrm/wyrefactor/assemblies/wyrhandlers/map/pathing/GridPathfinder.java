package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;

import java.util.HashMap;


public final class GridPathfinder implements WyrFrame{

    private GridPathfinder() {}

    // Can be assumed he will always return the shortest
    // valid path for a given movement type.

    // that wasn't nice

    // what?

    public static GridPath shortestBetween(final WyrActor subject, final WyrActor object) {
        return shortestBetween(subject, object.getOccupiedTile()).shortenBy(1);
    }
    public static GridPath shortestBetween(final WyrActor actor, final WyrTile destinationTile) {
        return shorestBetween(actor.getOccupiedTile(), destinationTile, actor.getStats().getMovementType(), actor.getTeamAlignment());
    }
    public static GridPath shorestBetween(final WyrTile start, final WyrTile finish, final MobilityType moveType, final TeamAlignment forTeam) {
        Gdx.app.log("pathfinder", "finding shortest path");
        final Things localThings = reachableThings(start, 15, moveType, forTeam, 1, false);
        if(localThings.walkableTiles.containsKey(finish)) return localThings.walkableTiles.get(finish);
//        final Things xRayedThings = reachableThings(start, 30, moveType, forTeam, 1, true);
//        if(xRayedThings.tiles.containsKey(finish)) return xRayedThings.tiles.get(finish);

        int bestDistance = 999;
        WyrTile bestTile = null;

        for(WyrTile tile : localThings.walkableTiles.keySet()) {
            if(handlers.map().distanceBetweenTiles(tile, finish) < bestDistance) {
                bestDistance = handlers.map().distanceBetweenTiles(tile, finish);
                bestTile = tile;
            }
        }

        Gdx.app.log("pathfinder", "done");
        return bestTile == null ? new GridPath(start) : localThings.walkableTiles.get(bestTile);
    }

    public static Array<WyrTile> tilesTouchableFromTile(WyrTile tile, WyrActor forUnit) {
        return tilesTouchableFromTile(tile, forUnit.getReach());
    }

    public static Things currentlyAccessibleTo(WyrActor unit) {
        return reachableThings(unit.getOccupiedTile(), unit.stats().getAvailableSteps(), unit.stats().getMovementType(), unit.getTeamAlignment(), unit.getReach(), false);
    }
    private static Things currentlyAccessibleTo(WyrTile start, float speed, MobilityType RPGridMovementType, TeamAlignment alignment, int reach) {
        return reachableThings(start, speed, RPGridMovementType, alignment, reach, false);
    }
    public static Things potentiallyAccessibleTo(WyrActor unit) {
        return potentiallyAccessibleTo(unit.getOccupiedTile(), unit.stats().getMovementType(), unit.getTeamAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(WyrTile start, MobilityType byType, TeamAlignment alignment, int reach) {
        return reachableThings(start, 99, byType, alignment, reach, true);
    }
    private static Things reachableThings(WyrActor unit, boolean xRayUnits) {
        return reachableThings(unit.getOccupiedTile(), unit.stats().getAvailableSteps(), unit.stats().getMovementType(), unit.getTeamAlignment(), unit.getReach(), xRayUnits);
    }
    private static Things reachableThings(final WyrTile start, final float speed, final MobilityType moveType, final TeamAlignment team, final int reach, final boolean xRayActors) {
        final WyrMap grid = handlers.map();
        final Things reachable = new Things();
        // If we can't move, we can still return
        // things reachable from where we already are.
        if(speed <= 0) {
            for(WyrActor actor : actorsTouchableFromTile(start, reach)) {
                reachable.add(actor, new GridPath(start));
            }
            return reachable;
        }

        final Array<GridPath> paths     = new Array<>();
        final Array<GridPath> nextPaths = new Array<>();
        final HashMap<WyrTile, Float> tileCheckedAtSpeed = new HashMap<>();

        tileCheckedAtSpeed.put(start, 0f);

        // First loop, grab all tiles adjacent to start,
        // iterate through them, grabbing actors, as well
        // as grabbing any tiles available to continue pathing from.
        // Doing this first loop outside the main recursion keeps
        // things a little cleaner and neater overall.
        for(WyrTile adjacentTile : grid.allAdjacentTo(start)) {
            final GridPath pathEndingOnAdjacentTile = new GridPath(adjacentTile);
            tileCheckedAtSpeed.put(adjacentTile, adjacentTile.moveCostFor(moveType));
            for(WyrActor actor : adjacentTile.getActorsOnGround()) {
                reachable.added(actor, new GridPath(start), moveType);
            }
            // TODO: flyers and airspace
            //  (consider airspace height value with flyers having max altitude?
            //  maybe too complicated to communicate to player)
            if(adjacentTile.isTraversableBy(moveType)) {
                if(!adjacentTile.groundIsOccupied()
                    || teamsAreAllied(team, adjacentTile.getCorporealActor().getTeamAlignment())
                    || xRayActors) {
                    paths.add(pathEndingOnAdjacentTile);
                    if(!adjacentTile.groundIsOccupied()) {
                        reachable.added(adjacentTile, pathEndingOnAdjacentTile, moveType);
                    }
                }
            }
        }

        if(reachable.walkableTiles().isEmpty() && paths.isEmpty()) {
            // No tiles we can move to, bail out and return
            // things reachable from start.
            for(WyrActor actor : actorsTouchableFromTile(start, reach)) {
                reachable.add(actor, new GridPath(start));
            }
            return reachable;
        }

        boolean somethingWasAdded;

        // TODO: better commenting throughout

        do { // TODO: optimize && multi-thread (if necessary : test at Large values, 1000+ tiles)
            somethingWasAdded = false;

            for(GridPath thisPath : paths) {
                final float currentPathCost = thisPath.costFor(moveType);
                if(currentPathCost > speed) continue; // How did you even get here?

                for(WyrTile adjacentTile : grid.allAdjacentTo(thisPath.lastTile())) {
                    if(thisPath.contains(adjacentTile)) continue;
                    final float newCost = currentPathCost + adjacentTile.moveCostFor(moveType);

                    if(tileCheckedAtSpeed.containsKey(adjacentTile)) {
                        if(tileCheckedAtSpeed.get(adjacentTile) <= newCost) continue;
                    }
                    tileCheckedAtSpeed.put(adjacentTile, newCost);

                    // In the cases where an xRay flag is used to gather potential interactions, it is important
                    // to remember to call path.realize(unit) when done.
                    // Personal Responsibility doctrine dictates that methods should remain modular by
                    // sticking to their expressed scope, rather than trying to account for problems other
                    // handlers might run in to with the returned value.
                    // Give them what they ask for, nothing more or less.

                    // Only add the new thing to reachable values if the path we used to find it is actually accessible.
                    if(!thisPath.lastTile().groundIsOccupied() || xRayActors) {
                        for(WyrActor actor : adjacentTile.getActorsOnGround()) {
                            if(reachable.added(actor, thisPath, moveType)) somethingWasAdded = true;
                        }
                    }

                    // Only include the newTile if walking to it wouldn't break
                    // the speed budget; then account for reach.
                    if(newCost <= speed && adjacentTile.isTraversableBy(moveType)) {

                        // xRayUnits solves the problem of red team recognizing other
                        // red units as friends and moving through them; however,
                        // actual sorting of actors into categories is handled in Things(),
                        // and therefore all red units are tagged as "enemy", and so on.
                        // When calling Things() in other logic, this distinction is important
                        // to remember and work around.
                        // Can potentially engineer an automated solution around it later.
                        // ^ I did! It's called Things.opposition()
                        if(!adjacentTile.groundIsOccupied()
                            || teamsAreAllied(team, adjacentTile.getCorporealActor().getTeamAlignment())
                            || xRayActors) {

                            final GridPath branchingPath = new GridPath(thisPath);
                            branchingPath.append(adjacentTile);
                            nextPaths.add(branchingPath);

                            somethingWasAdded = true;

                            if(!adjacentTile.groundIsOccupied()) {
                                if(reachable.added(adjacentTile, branchingPath, moveType)) {
                                    reachable.touchableTiles.remove(adjacentTile);
                                }
                            }
                        }

                    } else {
                        // AdjacentTile is touchable but not walkable.
                        // Actors for this tile already added to things.
                        if(!reachable.walkableTiles.containsKey(adjacentTile)) {
                            if(reachable.added(adjacentTile, thisPath.lastTile())) { // true if adjacentTile added to reachable.touchableTiles
                                reachable.addIfUnique(tilesTouchableFromTile(adjacentTile, reach), adjacentTile);
                                // While something may have been added, touchable actors don't constitute another recursion loop.
                            }
                        }
                    }
                }
            }

            // Clear out paths we're done with.
            paths.clear();
            paths.addAll(nextPaths);
            nextPaths.clear();

        } while(somethingWasAdded);

        return reachable;
    }

    public static Array<WyrActor> actorsTouchableFromTile(WyrTile tile, int reach) {
        final Array<WyrActor> rV = new Array<>();
        final Array<WyrTile> tiles = tilesTouchableFromTile(tile, reach);
        for(WyrTile t : tiles) {
            rV.addAll(t.getActorsOnGround());
        }
        return rV;
    }

    public static Array<WyrTile> tilesTouchableFromTile(WyrTile tile, int reach) {

        final Array<WyrTile> tiles = new Array<>();

        boolean touchableAdded;

        Array<WyrTile> tilesToCheck = handlers.map().allAdjacentTo(tile);
        Array<WyrTile> nextTiles = new Array<>();
        HashMap<WyrTile, Integer> tileCheckedAtDistance = new HashMap<>();

        tileCheckedAtDistance.put(tile,0);

        int distance = 0;

        do {
            touchableAdded = false;
            for(WyrTile touchableTile : tilesToCheck) {
                if(tileCheckedAtDistance.containsKey(touchableTile) && tileCheckedAtDistance.get(tile) < distance) continue;
                tileCheckedAtDistance.put(touchableTile, distance);
                if(reach < handlers.map().distanceBetweenTiles(tile.getCoordinates(), touchableTile.getCoordinates())) continue;
                if(touchableTile.blocksLineOfSight()) continue;
                if(tiles.contains(touchableTile, true)) continue;
                touchableAdded = true;
                nextTiles.addAll(handlers.map().allAdjacentTo(touchableTile));
            }
            tilesToCheck.clear();
            tilesToCheck.addAll(nextTiles);
            nextTiles.clear();
            distance++;
        } while(touchableAdded);

        return tiles;

    }

    public static int turnsToReach(WyrTile destination, WyrActor pathFor) {
        return 1; // TODO
    }


    public static boolean teamsAreAllied(TeamAlignment alignment, TeamAlignment teamAlignment) {
        if(alignment == null || teamAlignment == null) return false;
        if(alignment == teamAlignment) return true;
        switch(alignment) {
            case PLAYER:
            case ALLY:
                switch(teamAlignment) {
                    case PLAYER:
                    case ALLY:
                        return true;

                    case STRANGER:
                    case ENEMY:
                    default:
                        return false;
                }

            case ENEMY:
            case STRANGER:
            case APOLITICAL_BYSTANDER:
            default:
                return false;
        }
    }


    public static final class Things {
        private MobilityType mobilityType;
        private final HashMap<WyrTile, GridPath> walkableTiles = new HashMap<>();
        private final HashMap<WyrTile, WyrTile> touchableTiles = new HashMap<>();
        private final HashMap<WyrTile, Array<WyrTile>> touchableTilesFromTile = new HashMap<>();
        private final HashMap<WyrActor, GridPath> props     = new HashMap<>();
        private final HashMap<WyrActor, GridPath> enemies   = new HashMap<>();
        private final HashMap<WyrActor, GridPath> allies    = new HashMap<>();
        private final HashMap<WyrActor, GridPath> strangers = new HashMap<>();
        private final HashMap<WyrActor, GridPath> players   = new HashMap<>();

        public Things() {

        }

        public Things(MobilityType movementTypeForPathingCosts) {
            this.mobilityType = movementTypeForPathingCosts;
        }

        public void addIfUnique( Array<WyrTile> touchableFromWalkableTile, WyrTile walkableTile) {
            for(WyrTile t : touchableFromWalkableTile) {
                added(t, walkableTile);
            }
        }

        public boolean added(WyrTile touchableTile, WyrTile walkableTile) {
            if(!touchableTilesFromTile.containsKey(walkableTile)) touchableTilesFromTile.put(walkableTile, new Array<>());
            if(!touchableTilesFromTile.get(walkableTile).contains(touchableTile, true)) touchableTilesFromTile.get(walkableTile).add(touchableTile);
            if(!touchableTiles.containsKey(touchableTile)) {
                touchableTiles.put(touchableTile, walkableTile);
                return true;
            }
            return false;
        }

        public boolean added(WyrTile touchableTile, WyrTile walkableTile, MobilityType forMobilityType) {
            // If the newly passed in tile is not already in touchable tiles, add it and return true.
            // If the new tile is not unique, check if the new reachableTile from which this newTime
            // is touchable has a stored path already.
            // If this new walkableTile and the stored walkableTile both have paths,
            // compare the lengths and only add the new time if the path cost is lower.
            // If a path is cached for this new walkableTile but not the old walkableTile currently stored in
            // touchableTiles, prefer to save the tile with a pre-computed path, and vice versa.
            // The computational difference is probably negligible, but surely worth accouting for.
            // If the walkableTile has a path, check if the walkableTile currently associated with
            // this touchableTile also has a path stored.
            if(!touchableTiles.containsKey(touchableTile)
//                || (!walkableTiles.containsKey(walkableTile)
                || (walkableTiles.containsKey(touchableTiles.get(touchableTile))
                && walkableTiles.get(touchableTile).costFor(forMobilityType) < walkableTiles.get(touchableTiles.get(touchableTile)).costFor(forMobilityType))) {
                add(touchableTile, walkableTile);
                return true;
            }
            return false;
        }

        public boolean added(WyrTile tile, GridPath path, MobilityType forType) {
            if(!walkableTiles.containsKey(tile) || walkableTiles.get(tile).costFor(forType) > path.costFor(forType)) {
                add(tile, path);
                return true;
            }
            return false;
        }

        public boolean added(WyrActor actor, GridPath path, MobilityType forType) {
            if(actor == null) return false;
            if(path == null) return false;
            if(forType == null) return false;
            switch(actor.getActorType()) {
                case PROP:
                    if(!props.containsKey(actor) || props.get(actor).costFor(forType) > path.costFor(forType)) {
                        add(actor, path);
                        return true;
                    }
                    break;
                case ENTITY:
                    switch(((WyrActor.Unit)actor).getTeamAlignment()) {
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
                        case STRANGER:
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

        private void add(WyrTile tile, GridPath shortestPathTo) {
            walkableTiles.put(tile, shortestPathTo);
        }

        private void add(WyrTile touchableTile, WyrTile walkableTile) {
            touchableTiles.put(touchableTile, walkableTile);
        }

        public void add(WyrActor actor, GridPath shortestPathTo) {
            switch(actor.getActorType()) {
                case PROP:
                    props.put(actor, shortestPathTo);
                    break;

                case ENTITY:
                    switch(((WyrActor.Unit)actor).getTeamAlignment()) {
                        case PLAYER:
                            players.put(actor, shortestPathTo);
                            break;

                        case ALLY:
                            allies.put(actor, shortestPathTo);
                            break;

                        case ENEMY:
                            enemies.put(actor, shortestPathTo);
                            break;

                        case STRANGER:
                            strangers.put(actor, shortestPathTo);
                            break;
                    }
                    break;
            }
        }

        public GridPath pathTo(WyrActor actor) {
            if(!actors().contains(actor, true)) return new GridPath();
            if(enemies.containsKey(actor)) return enemies.get(actor);
            if(strangers.containsKey(actor)) return strangers.get(actor);
            if(allies.containsKey(actor)) return allies.get(actor);
            if(players.containsKey(actor)) return players.get(actor);
            return new GridPath();
        }

        public Array<WyrTile> touchableTilesFromTile(WyrTile fromTile) {
            return touchableTilesFromTile.getOrDefault(fromTile, new Array<>());
        }

        public Array<WyrActor> actors() {
            final Array<WyrActor> returnValue = new Array<>();
            for(WyrActor prop : props.keySet()) {
                returnValue.add(prop);
            }
            for(WyrActor friend : players.keySet()) {
                returnValue.add(friend);
            }
            for(WyrActor enemy : enemies.keySet()) {
                returnValue.add(enemy);
            }
            for(WyrActor stranger : strangers.keySet()) {
                returnValue.add(stranger);
            }
            return returnValue;
        }

        public HashMap<WyrActor, GridPath> opposition(TeamAlignment to) {
            final HashMap<WyrActor, GridPath> opposition = new HashMap<>();
            switch(to) {
                case PLAYER:
                case ALLY:
                    opposition.putAll(enemies);
                    opposition.putAll(strangers);
                    break;
                case ENEMY:
                    opposition.putAll(players);
                    opposition.putAll(allies);
                    opposition.putAll(strangers);
                    break;
                case STRANGER:
                    opposition.putAll(players);
                    opposition.putAll(allies);
                    opposition.putAll(enemies);
            }
            return opposition;
        }

        public HashMap<WyrActor, GridPath> props()     { return props; }
        public HashMap<WyrActor, GridPath> allies()    { return allies; }
        public HashMap<WyrActor, GridPath> enemies()   { return enemies; }
        public HashMap<WyrActor, GridPath> players()   { return players; }
        public HashMap<WyrActor, GridPath> strangers() { return strangers; }
        public HashMap<WyrTile, GridPath> walkableTiles() { return walkableTiles; }
        public HashMap<WyrTile, WyrTile> touchableTiles() { return touchableTiles; }

    }
}
