package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;


public class GridPath /*extends WyrPath*/ {

    // still indexing from 0

    private final Array<GridTile> internalPath = new Array<>();


    public GridPath() {

    }

    private void mirror(GridPath toMirror) {

    }

    public void incorporateNext(Direction direction) {

    }

    protected void shortenBy(int toTrim) {

    }

    protected void truncateTo(int newLength) {

    }

    public Array<GridTile> getPath() { return internalPath; }
    public int length() { return internalPath.size; }
    public float costFor(GridUnit findCostFor) {
        return 0f; // TODO
    }
    public GridTile lastTile() {
        return null; // TODO
    }
    public boolean contains(GridTile tile) {
        return false; // TODO
    }
}
