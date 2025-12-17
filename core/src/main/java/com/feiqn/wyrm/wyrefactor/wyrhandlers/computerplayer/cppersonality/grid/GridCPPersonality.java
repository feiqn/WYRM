package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.grid;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cppersonality.WyrCPPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridCPPersonality extends WyrCPPersonality {

    private final Array<GridUnit> unitTargets = new Array<>();
    private final Array<GridProp> propTargets = new Array<>();
    private final Array<GridTile> tileTargets = new Array<>();

    public GridCPPersonality(WyrType wyrType, AIPersonality personality) {
        super(wyrType, personality);
    }

    public void prioritize(GridTile tile) { tileTargets.add(tile); }
    public void prioritize(GridUnit unit) { unitTargets.add(unit); }
    public void prioritize(GridProp prop) { propTargets.add(prop); }

    public GridPathfinder.Things priorities() {
        final GridPathfinder.Things returnValue = new GridPathfinder.Things();
        for(GridTile tile : tileTargets) {
            returnValue.add(tile, new GridPath());
        }
        for(GridUnit unit : unitTargets) {
            returnValue.add(unit, new GridPath());
        }
        for(GridProp prop : propTargets) {
            returnValue.add(prop, new GridPath());
        }
        return returnValue;
    }
}
