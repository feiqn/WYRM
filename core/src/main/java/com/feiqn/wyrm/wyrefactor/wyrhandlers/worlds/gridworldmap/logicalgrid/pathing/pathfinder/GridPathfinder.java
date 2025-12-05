package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder;

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

public class GridPathfinder /*extends WyrPathfinder*/ {

    private final WyrGrid grid;

    public GridPathfinder(WyrGrid grid) { this.grid = grid; }

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
    public static GridPath shortestPathBetween(GridTile start, GridTile finish, MovementType forType, boolean xRayUnits, boolean xRayProps) {

        final Array<GridTile> reachableTiles = (
                if(xRayProps) {
                    xRayUnits ? potentiallyInReachOf(start.getCoordinates(), forType) :
                } else {

                }
            );
        return null;
    }

    private static Array<GridTile> currentlyInRangeOf(GridUnit unit) {

        return null;
    }

    private static Array<GridTile> currentlyInReachOf(GridUnit unit) {

        return null;
    }

    private static Array<GridTile> currentlyAccessibleTo(GridUnit unit) {

        return null;
    }

    private static Array<GridTile> potentiallyInReachOf(GridUnit unit) {
        return potentiallyInReachOf(unit.gridPosition(), unit.getMovementType(), unit.getReach());
    }
    private static Array<GridTile> potentiallyInReachOf(Vector2 start, MovementType byType, int reach) {

        return null;
    }

    private static Array<GridTile> potentiallyAccessibleTo(GridUnit unit) {
        return potentiallyAccessibleTo(unit.gridPosition(), unit.getMovementType());
    }
    private static Array<GridTile> potentiallyAccessibleTo(Vector2 start, MovementType byType) {

        return null;
    }

    private static Things reachableThings(GridUnit unit, boolean xRayUnits, boolean xRayProps) {
        // reachable with your weapon but not directly to interact with
        return accessibleThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.getAlignment(), unit.getReach(), xRayUnits, xRayProps);
    }

    private static Things accessibleThings(GridUnit unit, boolean xRayUnits, boolean xRayProps) {
        return accessibleThings(unit.occupyingTile(), unit.modifiedStatValue(StatTypes.SPEED), unit.getMovementType(), unit.getAlignment(), 1, xRayUnits, xRayProps);
    }
    private static Things accessibleThings(GridTile start, float speed, MovementType moveType, TeamAlignment alignment, int reach, boolean xRayUnits, boolean xRayProps) {
        final Things things = new Things();



        return things;
    }

    public static boolean canGetTo(GridUnit unit, GridTile tile) {

        return false;
    }

    public static boolean couldAccess(GridUnit unit, GridTile tile) {

        return false;
    }

    public static boolean canReach(GridUnit unit, GridTile tile) {

        return false;
    }

    public static boolean couldReach(GridUnit unit, GridTile tile) {

        return false;
    }



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
