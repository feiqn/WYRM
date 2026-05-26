package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;


public class GridPath {

    // still indexing from 0

    private final Array<RPGridTile> internalPath = new Array<>();

    public GridPath() {}

    public GridPath(RPGridTile startingTile) {
        internalPath.add(startingTile);
    }

    public GridPath(GridPath mirror) {
        mirror(mirror);
    }

    private void mirror(GridPath toMirror) {
        this.internalPath.addAll(toMirror.getTiles());
    }

    public void append(RPGridTile tile) {
        internalPath.add(tile);
    }

//    public void incorporateNext(Direction direction) {
//
//    }

    public GridPath realize(WyrActor forUnit) {
        //
//        Gdx.app.log("path","unrealized length: " + length());
//        if(length() > 1 && internalPath.contains(forUnit.getOccupiedTile(), true)) {
//            Gdx.app.log("path","removed starting tile");
//            internalPath.removeValue(forUnit.getOccupiedTile(), true);
//        }
        float speed = forUnit.moveSpeed();
//        Gdx.app.log("path","speed: " + speed);
        int newLength = 0;
        for(RPGridTile t : internalPath) {
            if(speed <= 0) break;
            speed -= t.moveCostFor(forUnit.getMobilityType());
            newLength++;
        }
//        Gdx.app.log("path","new length: " + newLength);
        if(newLength != length()) truncateTo(newLength);
//        Gdx.app.log("path","truncated: " + length());
//        if(length() < 1) throw new GdxRuntimeException("Bad path");
        if(lastTile() == forUnit.getOccupiedTile()) return this;
        for(int highestVacantIndex = internalPath.size-1; highestVacantIndex > 0; highestVacantIndex--) {
            if(internalPath.get(highestVacantIndex).isOccupied()) continue;
//            Gdx.app.log("path", "highestVacantIndex: " + highestVacantIndex);
            if(highestVacantIndex == internalPath.size - 1) return this;
            truncateTo(highestVacantIndex + 1);
//            Gdx.app.log("path","final length: " + length());
            return this;
        }
        internalPath.clear();
        internalPath.add(forUnit.getOccupiedTile());
        return this;
    }
    public void trimToObstructions(WyrActor.Unit forUnit) {
        trimToObstructions(forUnit.getTeamAlignment(), forUnit.getMobilityType());
    }
    public void trimToObstructions(Wyr.TeamAlignment team, Wyr.GameKit.RPG.MobilityType moveType) {
        for(int i = 0; i < internalPath.size; i++) {
            if(internalPath.get(i).groundIsObstructed(team, moveType)) {
                truncateTo(i+1);
                break;
            }
        }
    }

    protected void shortenBy(int toTrim) {
        for(int i = 0; i < toTrim; i++) {
            internalPath.removeIndex(internalPath.size-1);
        }
    }

    protected void truncateTo(int newLength) {
        internalPath.truncate(newLength);
    }

    public boolean reaches(WyrMap map, RPGridTile tileToReach, WyrActor forUnit) {
        // check if any tile is < forUnit.getReach() distanceFrom tileToReach
        for(RPGridTile t : internalPath) {
            if(map.distanceBetweenTiles(t, tileToReach) <= forUnit.getReach()) return true;
        }
        return false;
    }
    public Array<RPGridTile> getTiles() { return internalPath; }
    public int length() { return internalPath.size; }
    public float costFor(WyrActor findCostFor) {
        return costFor(findCostFor.getMobilityType());
    }
    public float costFor(Wyr.GameKit.RPG.MobilityType type) {
        float cost = 0;
        for(RPGridTile tile : internalPath) {
            cost += tile.moveCostFor(type);
        }
        return cost;
    }
    public RPGridTile lastTile() {
        return internalPath.get(internalPath.size - 1);
    }
    public boolean contains(RPGridTile tile) {
        return internalPath.contains(tile, true);
    }
}
