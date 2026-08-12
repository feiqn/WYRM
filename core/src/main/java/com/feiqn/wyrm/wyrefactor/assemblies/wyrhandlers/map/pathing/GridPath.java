package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;


public class GridPath {

    // still indexing from 0

    private final Array<WyrTile> internalPath = new Array<>();

    public GridPath() {}

    public GridPath(WyrTile startingTile) {
        internalPath.add(startingTile);
    }

    public GridPath(GridPath mirror) {
        mirror(mirror);
    }

    private void mirror(GridPath toMirror) {
        this.internalPath.addAll(toMirror.getTiles());
    }

    public void append(WyrTile tile) {
        internalPath.add(tile);
    }

//    public void incorporateNext(Direction direction) {
//
//    }

    public GridPath realize(WyrActor forUnit) {
        float speed = forUnit.stats().getAvailableSteps();
        int newLength = 0;
        for(WyrTile t : internalPath) {
            if(speed <= 0) break;
            speed -= t.moveCostFor(forUnit.stats().getMovementType());
            newLength++;
        }
        if(newLength != length()) truncateTo(newLength);
        if(lastTile() == forUnit.getOccupiedTile()) return this;
        for(int highestVacantIndex = internalPath.size-1; highestVacantIndex > 0; highestVacantIndex--) {
            if(internalPath.get(highestVacantIndex).groundIsObstructed(forUnit)) continue;
            if(highestVacantIndex == internalPath.size - 1) return this;
            truncateTo(highestVacantIndex + 1);
            return this;
        }
        internalPath.clear();
        internalPath.add(forUnit.getOccupiedTile());
        return this;
    }
    public void trimToObstructions(WyrActor.Unit forUnit) {
        trimToObstructions(forUnit.getTeamAlignment(), forUnit.stats().getMovementType());
    }
    public void trimToObstructions(WyrFrame.TeamAlignment team, WyrFrame.GameKit.RPG.MobilityType moveType) {
        for(int i = 0; i < internalPath.size; i++) {
            if(internalPath.get(i).groundIsObstructed(team, moveType)) {
                truncateTo(i+1);
                break;
            }
        }
    }

    protected GridPath shortenBy(int toTrim) {
        for(int i = 0; i < toTrim; i++) {
            if(internalPath.size > 1) internalPath.removeIndex(internalPath.size-1);
        }
        return this;
    }

    protected void truncateTo(int newLength) {
        internalPath.truncate(newLength);
    }

    public boolean reaches(WyrMap map, WyrTile tileToReach, WyrActor forUnit) {
        // check if any tile is < forUnit.getReach() distanceFrom tileToReach
        for(WyrTile t : internalPath) {
            if(map.distanceBetweenTiles(t, tileToReach) <= forUnit.getReach()) return true;
        }
        return false;
    }
    public Array<WyrTile> getTiles() { return internalPath; }
    public int length() { return internalPath.size; }
    public float costFor(WyrActor findCostFor) {
        return costFor(findCostFor.stats().getMovementType());
    }
    public float costFor(WyrFrame.GameKit.RPG.MobilityType type) {
        float cost = 0;
        for(WyrTile tile : internalPath) {
            cost += tile.moveCostFor(type);
        }
        return cost;
    }
    public WyrTile lastTile() {
        return internalPath.get(internalPath.size - 1);
    }
    public boolean contains(WyrTile tile) {
        return internalPath.contains(tile, true);
    }
}
