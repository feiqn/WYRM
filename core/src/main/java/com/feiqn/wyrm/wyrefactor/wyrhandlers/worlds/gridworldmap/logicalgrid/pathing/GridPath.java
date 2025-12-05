package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;


public class GridPath /*extends WyrPath*/ {

    // still indexing from 0

    private final Array<GridTile> internalPath = new Array<>();

    public GridPath() {

    }

    public GridPath(GridTile startingTile) {
        internalPath.add(startingTile);
    }

    public GridPath(GridPath mirror) {
        mirror(mirror);
    }

    private void mirror(GridPath toMirror) {
        this.internalPath.addAll(toMirror.getPath());
    }

    public void append(GridTile tile) {

    }

    public void incorporateNext(Direction direction) {

    }

    public void trimToObstructions() {

    }

    protected void shortenBy(int toTrim) {

    }

    protected void truncateTo(int newLength) {

    }

    public boolean reaches(GridTile tileToReach, GridUnit forUnit) {
        // check if last tile is < forUnit.getReach() distanceFrom tileToReach
        return false;
    }
    public Array<GridTile> getPath() { return internalPath; }
    public int length() { return internalPath.size; }
    public float costFor(GridUnit findCostFor) {
        return costFor(findCostFor.getMovementType());
    }
    public float costFor(MovementType type) {
        float cost = 0;
        for(GridTile tile : internalPath) {
            cost += tile.moveCostFor(type);
        }
        return cost;
    }
    public boolean isObstructed() { // TODO
        return false;
    }
    public GridTile lastTile() {
        return internalPath.get(internalPath.size - 1);
    }
    public boolean contains(GridTile tile) {
        return internalPath.contains(tile, true);
    }
}
