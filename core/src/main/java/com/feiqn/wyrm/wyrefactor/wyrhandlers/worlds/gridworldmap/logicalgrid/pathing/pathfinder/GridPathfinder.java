package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.WyrGrid;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

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

    public static GridPath toFarthestInRangeAlongShortestPath(GridTile start, GridTile finish, MovementType forType, boolean xRayUnits, boolean xRayProps) {

        return null;

    }

    public static GridPath shortestPathTo(GridUnit pathFor, GridTile finish, boolean xRayUnits, boolean xRayProps) {

        Things accessible;

        if(canGetTo(pathFor, finish)) {
            accessible = currentlyAccessibleTo(pathFor);
        } else if(couldGetTo(pathFor, finish)){
            accessible = potentiallyAccessibleTo(pathFor);
        } else {
            Gdx.app.log("Pathfinder", "No potential paths for " + pathFor.getName() + " to " + finish.getCoordinates());
            return null;
        }

        final Array<GridPath> paths = new Array<>();
        for(GridTile tile : grid.allAdjacentTo(pathFor.occupyingTile())) {
            paths.add(new GridPath(tile));
        }
        if(pathWithTile(paths, finish) != null) return pathWithTile(paths,finish);

        boolean terminating = false;

        Gdx.app.log("Pathfinder", "It's all");
        do {
            // Bloom() goes here for more or less
        } while(pathWithTile(paths, finish) == null || terminating);
        Gdx.app.log("Pathfinder", "over."); // Gonna do it right this time around.


    }
     private static GridPath pathWithTile(Array<GridPath> paths, GridTile tile) {
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
    private static Things accessibleThings(GridTile start, float speed, MovementType moveType, TeamAlignment alignment, int reach, boolean xRayUnits, boolean xRayProps) {
        final Things things = new Things();

        // recursively add Things

        return things;
    }



    public static int turnsToReach(GridTile destination, GridUnit pathFor) {
        return 1;
    }

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
