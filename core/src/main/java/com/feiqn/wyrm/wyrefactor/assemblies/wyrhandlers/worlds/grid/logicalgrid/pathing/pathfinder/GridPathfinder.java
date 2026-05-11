package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridMovementType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.TeamAlignment;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.RPGridMapHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.RPGStatType.SPEED;

public final class GridPathfinder /*extends WyrPathfinder*/ {

    private GridPathfinder() {}

    // Can be assumed he will always return the shortest
    // valid path for a given movement type.

    // that wasn't nice

    public  static Things reachableFromTile(RPGridMapHandler grid, GridTile tile, RPGridUnit forUnit) {
        return thingsInReachOfTile(grid, tile, forUnit.getReach());
    }

    public  static Things currentlyAccessibleTo(RPGridMapHandler grid, RPGridUnit unit) {
        return reachableThings(grid, unit.getOccupiedTile(), unit.getModifiedStatValue(SPEED), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach(), false, false);
    }
    private static Things currentlyAccessibleTo(RPGridMapHandler grid, GridTile start, float speed, RPGridMovementType RPGridMovementType, TeamAlignment alignment, int reach) {
        return reachableThings(grid, start, speed, RPGridMovementType, alignment, reach, false, false);
    }
    public  static Things potentiallyAccessibleTo(RPGridMapHandler grid, RPGridUnit unit) {
        return potentiallyAccessibleTo(grid, unit.getOccupiedTile(), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach());
    }
    private static Things potentiallyAccessibleTo(RPGridMapHandler grid, GridTile start, RPGridMovementType byType, TeamAlignment alignment, int reach) {
        return reachableThings(grid, start, 99, byType, alignment, reach, true, true);
    }
    private static Things reachableThings(RPGridMapHandler grid, RPGridUnit unit, boolean xRayUnits, boolean xRayProps) {
        return reachableThings(grid, unit.getOccupiedTile(), unit.getModifiedStatValue(SPEED), unit.getMovementType(), unit.getTeamAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }
    private static Things reachableThings(RPGridMapHandler grid, final GridTile start, final float speed, final RPGridMovementType moveType, final TeamAlignment team, final int reach, final boolean xRayUnits, final boolean xRayProps) {
        final Things reachable = new Things();
        // If we can't move, we can still return
        // things reachable from where we already are.
        if(speed <= 0) return thingsInReachOfTile(grid, start, reach);

        final Array<GridPath> paths     = new Array<>();
        final Array<GridPath> nextPaths = new Array<>();
        final HashMap<GridTile, Float> tileCheckedAtSpeed = new HashMap<>();

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
        for(GridTile tile : grid.allAdjacentTo(start)) {
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
            return thingsInReachOfTile(grid, start, reach);
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

                for(GridTile newTile : grid.allAdjacentTo(thisPath.lastTile())) {
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
                        final Things reachableThings = thingsInReachOfTile(grid, newTile, reach);
                        for(RPGridActor actor : reachableThings.actors()) {
                            if(reachable.added(actor, thisPath, actor.getMovementType())) somethingWasAdded = true;
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

    private static Things thingsInReachOfTile(RPGridMapHandler grid, GridTile tile, int reach) {
        final Things reachable = new Things();

        // TODO:
        //  fill out with needed logic from reachableThings loop,
        //  account for airspace and flyers,

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
        private final HashMap<GridTile,   GridPath> tiles     = new HashMap<>();
        private final HashMap<RPGridProp, GridPath> props     = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> enemies   = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> allies    = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> strangers = new HashMap<>();
        private final HashMap<RPGridUnit, GridPath> players   = new HashMap<>();

        public Things() {}

        public boolean added(GridTile tile, GridPath path, RPGridMovementType forType) {
            if(!tiles.containsKey(tile) || tiles.get(tile).costFor(forType) > path.costFor(forType)) {
                add(tile, path);
                return true;
            }
            return false;
        }

        public boolean added(RPGridActor actor, GridPath path, RPGridMovementType forType) {
            if(actor == null) return false;
            if(path == null) return false;
            if(forType == null) return false;
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

                        case STRANGER:
                            strangers.put((RPGridUnit) actor, shortestPathTo);
                            break;
                    }
                    break;
            }
        }

        public Array<RPGridInteraction> interactables() {
            final Array<RPGridInteraction> returnValue = new Array<>();
            for(RPGridActor actor : actors()) {
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
        public HashMap<RPGridUnit, GridPath> opposition(TeamAlignment to) {
            final HashMap<RPGridUnit, GridPath> opposition = new HashMap<>();
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
        public HashMap<RPGridProp, GridPath> props()     { return props; }
        public HashMap<GridTile,   GridPath> tiles()     { return tiles; }
        public HashMap<RPGridUnit, GridPath> allies()    { return allies; }
        public HashMap<RPGridUnit, GridPath> enemies()   { return enemies; }
        public HashMap<RPGridUnit, GridPath> players()   { return players; }
        public HashMap<RPGridUnit, GridPath> strangers() { return strangers; }
    }
}
