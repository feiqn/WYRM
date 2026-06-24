package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.SPEED;


public final class GridPathfinder {

    private GridPathfinder() {}

    // Can be assumed he will always return the shortest
    // valid path for a given movement type.

    // that wasn't nice

    // what?

    public  static Things reachableFromTile(RPGridTile tile, WyrActor forUnit) {
        return thingsInReachOfTile(tile, forUnit.getReach());
    }

    public  static Things currentlyAccessibleTo(WyrActor.Unit unit) {
        return reachableThings(unit.getOccupiedTile(), unit.stats().getAvailableSteps(), unit.getMobilityType(), unit.getTeamAlignment(), unit.getReach(), false, false);
    }
    private static Things currentlyAccessibleTo(RPGridTile start, float speed, MobilityType RPGridMovementType, WyrFrame.TeamAlignment alignment, int reach) {
        return reachableThings(start, speed, RPGridMovementType, alignment, reach, false, false);
    }
    public  static Things potentiallyAccessibleTo(WyrActor.Unit unit) {
        return potentiallyAccessibleTo(unit.getOccupiedTile(), unit.getMobilityType(), unit.getTeamAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(RPGridTile start, MobilityType byType, WyrFrame.TeamAlignment alignment, int reach) {
        return reachableThings(start, 99, byType, alignment, reach, true, true);
    }
    private static Things reachableThings(WyrActor.Unit unit, boolean xRayUnits, boolean xRayProps) {
        return reachableThings(unit.getOccupiedTile(), unit.stats().getAvailableSteps(), unit.getMobilityType(), unit.getTeamAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }
    private static Things reachableThings(final RPGridTile start, final float speed, final MobilityType moveType, final WyrFrame.TeamAlignment team, final int reach, final boolean xRayUnits, final boolean xRayProps) {
        final WyrMap grid = WyrFrame.handlers.map();
        final Things reachable = new Things();
        // If we can't move, we can still return
        // things reachable from where we already are.
        if(speed <= 0) return thingsInReachOfTile(start, reach);

        final Array<GridPath> paths     = new Array<>();
        final Array<GridPath> nextPaths = new Array<>();
        final HashMap<RPGridTile, Float> tileCheckedAtSpeed = new HashMap<>();

        // TODO: watch for issues here:
//        reachable.add(start, new GridPath());
        tileCheckedAtSpeed.put(start, 0f);

//        for(RPGridInteraction interaction : thingsInReachOfTile(grid, start, reach).interactables()) {
//            if(interaction.interactableRange() <= reach) start.addEphemeralInteractable(interaction);
//            reachable.added(interaction.getSubject(), new GridPath(start), moveType);
//        }
        // TODO: through here.

        // First loop, grab all tiles adjacent to start,
        // iterate through them, grabbing actors, as well
        // as grabbing any tiles available to continue pathing from.
        // Doing this first loop outside the main recursion keeps
        // things a little cleaner and neater overall.
        for(RPGridTile tile : grid.allAdjacentTo(start)) {
            final GridPath path = new GridPath(tile);
            tileCheckedAtSpeed.put(tile, tile.moveCostFor(moveType));
            if(tile.hasProp())    reachable.added(tile.prop(), path, moveType);
            if(tile.isOccupied()) reachable.added(tile.occupier(), new GridPath(start), moveType);
            // TODO: flyers and airspace
            //  (consider airspace height value with flyers having max altitude?
            //  maybe too complicated to communicate to player)
            if(tile.groundIsObstructed(team, moveType)) continue;
            if(!tile.isOccupied()
                || xRayUnits
                || teamCanPass(team, tile.occupier().getTeamAlignment())) {
                    paths.add(path);
                    if(!tile.isOccupied()) {
                        if(reachable.added(tile, path, moveType)) {
//                            for(RPGridInteraction interaction : thingsInReachOfTile(grid, tile, reach).interactables()) {
//                                if(interaction.interactableRange() <= reach) {
//                                    reachable.added(interaction.getSubject(), new GridPath(tile), moveType);
//                                }
//                            }
                        }
                    }
            }
        }

        if(reachable.tiles().isEmpty() && paths.isEmpty()) {
            // No tiles we can move to, bail out and return
            // things reachable from start.
            return thingsInReachOfTile(start, reach);
        }

        // TODO:
        //  account for aerials in airspace.

        boolean somethingWasAdded;
        for(GridPath path : reachable.tiles.values()) {
            paths.add(path);
        }

        // TODO: better commenting throughout

        do { // TODO: optimize && multi-thread (if necessary : test at Large values, 1000+ tiles)
            somethingWasAdded = false;

            for(GridPath thisPath : paths) {
                final float currentPathCost = thisPath.costFor(moveType);
                if(currentPathCost > speed) continue; // How did you even get here?

                for(RPGridTile newTile : grid.allAdjacentTo(thisPath.lastTile())) {
                    if(thisPath.contains(newTile)) continue;
                    final float newCost = currentPathCost + newTile.moveCostFor(moveType);

                    if(tileCheckedAtSpeed.containsKey(newTile)) {
                        if(tileCheckedAtSpeed.get(newTile) <= newCost) continue;
                    }
                    tileCheckedAtSpeed.put(newTile, newCost);

                    // TODO: instead of || xRayUnits, switch based on tile.getObstruction() (prop, unit, terrain)
                    // In the cases where an xRay flag is used to gather potential interactions, it is important
                    // to remember to call path.realize(unit) when done.
                    // Personal Responsibility doctrine dictates that methods should remain modular by
                    // sticking to their expressed scope, rather than trying to account for problems other
                    // handlers might run in to with the returned value.
                    // Give them what they ask for, nothing more or less.
                    // Only add the new thing to reachable values if the path we used to find it is actually accessible.
                    if(newTile.hasProp() && (reachable.tiles.containsKey(thisPath.lastTile())) || xRayUnits) {
                        // TODO: handle breaking for solid props i.e. doors
                        if(reachable.added(newTile.prop(), thisPath, moveType)) somethingWasAdded = true;
                    }
                    if(newTile.isOccupied() && (reachable.tiles.containsKey(thisPath.lastTile())) || xRayUnits) {
                        if(reachable.added(newTile.occupier(), thisPath, moveType)) somethingWasAdded = true;
                    }

                    // Only include the newTile if walking to it wouldn't break
                    // the speed budget; then account for reach.
                    if(newCost <= speed && newTile.isTraversableBy(moveType)) {
                        // TODO:
                        //  account for units or tiles turned solid,
                        //  as well as solid props like doors.

                        if(!newTile.isOccupied()
                            // xRayUnits solves the problem of red team recognizing other
                            // red units as friends and moving through them; however,
                            // actual sorting of actors into categories is handled in Things(),
                            // and therefore all red units are tagged as "enemy", and so on.
                            // When calling Things() in other logic, this distinction is important
                            // to remember and work around.
                            // Can potentially engineer an automated solution around it later.
                            // ^ I did! It's called Things.opposition()
                            || xRayUnits
                            || teamCanPass(team, newTile.occupier().getTeamAlignment())) {

                                final GridPath branchingPath = new GridPath(thisPath);
                                branchingPath.append(newTile);
                                nextPaths.add(branchingPath);

                                somethingWasAdded = true;

                                if(!newTile.isOccupied()) {
                                    if(reachable.added(newTile, branchingPath, moveType)) {
//                                        for(RPGridInteraction interaction : thingsInReachOfTile(grid, newTile, reach).interactables()) {
//                                            if(interaction.interactableRange() <= reach) {
//                                                reachable.added(interaction.getSubject(), new GridPath(newTile), moveType);
//                                            }
//                                        }
//                                        somethingWasAdded = true;
                                    }
                                }

                                // TODO: populate each tile with things we can do at a distance from said tile (within reach)
//                                for(GridActor actor : thingsInReachOf(grid, newTile, reach).actors()) {
//                                    final boolean a = reachable.added(actor, branchingPath, moveType);
//                                    if(!added) added = a;
//                                }

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
                        final Things reachableThings = thingsInReachOfTile(newTile, reach);
                        for(WyrActor actor : reachableThings.actors()) {
                            if(reachable.added(actor, thisPath, actor.getMobilityType())) somethingWasAdded = true;
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

    private static Things thingsInReachOfTile(RPGridTile tile, int reach) {
        final Things reachable = new Things();

        // TODO:
        //  fill out with needed logic from reachableThings loop,
        //  account for airspace and flyers,

        reachable.add(tile, new GridPath(tile));
        if(tile.isOccupied()) reachable.add(tile.occupier(), new GridPath(tile));
        if(tile.hasProp()) reachable.add(tile.prop(), new GridPath(tile));

        for(RPGridTile t : WyrFrame.handlers.map().tilesWithinDistanceOf(reach, tile)) {
            if(t.isOccupied()) reachable.add(t.occupier(), new GridPath(tile));
            if(t.hasProp()) reachable.add(t.prop(), new GridPath(t));
        }
        return reachable;
    }

    public static int turnsToReach(RPGridTile destination, WyrActor pathFor) {
        return 1; // TODO
    }


    public static boolean teamCanPass(WyrFrame.TeamAlignment alignment, WyrFrame.TeamAlignment teamAlignment) {
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
            default:
                return false;
        }
    }


    public  static final class Things {
        private final HashMap<RPGridTile,   GridPath> tiles     = new HashMap<>();
        private final HashMap<WyrActor, GridPath> props     = new HashMap<>();
        private final HashMap<WyrActor, GridPath> enemies   = new HashMap<>();
        private final HashMap<WyrActor, GridPath> allies    = new HashMap<>();
        private final HashMap<WyrActor, GridPath> strangers = new HashMap<>();
        private final HashMap<WyrActor, GridPath> players   = new HashMap<>();

        public Things() {}

        public boolean added(RPGridTile tile, GridPath path, MobilityType forType) {
            if(!tiles.containsKey(tile) || tiles.get(tile).costFor(forType) > path.costFor(forType)) {
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

        private void add(RPGridTile tile, GridPath shortestPathTo) {
            tiles.put(tile, shortestPathTo);
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

        public Array<WyrInteraction> interactables() {
            final Array<WyrInteraction> returnValue = new Array<>();
            for(WyrActor actor : actors()) {
                returnValue.addAll(actor.getInteractions());
            }
            return returnValue;
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
        public HashMap<WyrActor, GridPath> opposition(WyrFrame.TeamAlignment to) {
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
        public HashMap<RPGridTile, GridPath> tiles() { return tiles; }
        public HashMap<WyrActor, GridPath> allies()    { return allies; }
        public HashMap<WyrActor, GridPath> enemies()   { return enemies; }
        public HashMap<WyrActor, GridPath> players()   { return players; }
        public HashMap<WyrActor, GridPath> strangers() { return strangers; }
    }
}
