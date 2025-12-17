package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;


public class GridPath /*extends WyrPath*/ {

    // still indexing from 0

    private final Array<GridTile> internalPath = new Array<>();

    public GridPath() {}

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
        internalPath.add(tile);
    }

//    public void incorporateNext(Direction direction) {
//
//    }

    public void trimToObstructions(TeamAlignment alignment) {
        for(int i = 0; i < internalPath.size; i++) {
            if(internalPath.get(i).groundIsObstructed(alignment)) {
                truncateTo(i+1);
                break;
            }
        }
    }

//    protected void shortenBy(int toTrim) {
//
//    }

    protected void truncateTo(int newLength) {
        for(int i = internalPath.size-1; i >= newLength; i--) {
            try { // TODO: watch here for bad math, idk how shit should work
                internalPath.removeIndex(i);
            } catch (Exception e) {
                Gdx.app.log("GridPath", "truncate: no index " + i);
            }
        }
    }

    public boolean reaches(GridTile tileToReach, GridUnit forUnit) {
        // check if last tile is < forUnit.getReach() distanceFrom tileToReach
        return false; // TODO
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
    public boolean groundIsObstructed(TeamAlignment alignment) {
        for(GridTile tile : internalPath) {
            if(tile.groundIsObstructed(alignment)) return true;
        }
        return false;
    }
    public GridTile lastTile() {
        return internalPath.get(internalPath.size - 1);
    }
    public boolean contains(GridTile tile) {
        return internalPath.contains(tile, true);
    }
}
