package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.RPGridMapHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;


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

    public GridPath realize(RPGridUnit forUnit) {
        trimToObstructions(forUnit.getTeamAlignment());
        float speed = forUnit.moveSpeed();
        int newLength = 0;
        for(GridTile t : internalPath) {
            if(speed <= 0) break;
            speed -= t.moveCostFor(forUnit.getMovementType());
            newLength++;
        }
        truncateTo(newLength);
        return this;
    }

    public void trimToObstructions(TeamAlignment alignment) {
        for(int i = 0; i < internalPath.size; i++) {
            if(internalPath.get(i).groundIsObstructed(alignment)) {
                truncateTo(i+1);
                break;
            }
        }
    }

    protected void shortenBy(int toTrim) {

    }

    protected void truncateTo(int newLength) {
        for(int i = internalPath.size-1; i >= newLength; i--) {
            internalPath.removeIndex(i);
        }
    }

    public boolean reaches(RPGridMapHandler map, GridTile tileToReach, RPGridUnit forUnit) {
        // check if any tile is < forUnit.getReach() distanceFrom tileToReach
        for(GridTile t : internalPath) {
            if(map.distanceBetweenTiles(t, tileToReach) <= forUnit.getReach()) return true;
        }
        return false;
    }
    public Array<GridTile> getPath() { return internalPath; }
    public int length() { return internalPath.size; }
    public float costFor(RPGridUnit findCostFor) {
        return costFor(findCostFor.getMovementType());
    }
    public float costFor(RPGridMovementType type) {
        float cost = 0;
        for(GridTile tile : internalPath) {
            cost += tile.moveCostFor(type);
        }
        return cost;
    }
    public GridTile lastTile() {
        return internalPath.get(internalPath.size - 1);
    }
    public boolean contains(GridTile tile) {
        return internalPath.contains(tile, true);
    }
}
