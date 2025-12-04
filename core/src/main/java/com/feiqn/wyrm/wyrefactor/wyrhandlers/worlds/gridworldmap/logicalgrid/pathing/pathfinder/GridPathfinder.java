package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public final class GridPathfinder /*extends WyrPathfinder*/ {

    private GridPathfinder() {}

    public static GridPath toNearestInRange(GridActor pathFor, GridTile tile) {
        return GridPathfinder.toNearestInRange(pathFor, tile.getCoordinates());
    }
    public static GridPath toNearestInRange(GridActor pathFor, Vector2 destination) {
        return GridPathfinder.toNearestInRange(pathFor, (int)destination.x, (int)destination.y);
    }
    public static GridPath toNearestInRange(GridActor pathFor, int x, int y) {
        return null; // TODO
    }



}
