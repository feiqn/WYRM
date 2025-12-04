package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridActorHandler {

    private final WYRMGame root;

    // map objects
    // units

    // TODO: GridPathfinder() to hold Path functions in WyrMap and AIHandler
    //  -

    public GridActorHandler(WYRMGame root) {
        this.root = root;
    }

    public void placeActor(GridActor actor, GridTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(GridActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(GridActor actor, int x, int y) {
        switch(actor.getActorType()) {
            case UNIT:
                // TODO
                break;

            case PROP:
                // TODO ALSO
                break;
        }
    }

    public void followPath(GridActor actor, GridPath path) {
        // TODO LOL
    }

}
