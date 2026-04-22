package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.grid;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.Personality;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.WyrPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public final class GridPersonality extends WyrPersonality {

    private final Array<RPGridUnit> unitTargets = new Array<>();
    private final Array<RPGridProp> propTargets = new Array<>();
    private final Array<GridTile> tileTargets = new Array<>();

    public GridPersonality(Personality personality) {
        super(personality);
    }

    public void prioritize(GridTile tile) { tileTargets.add(tile); }
    public void prioritize(RPGridUnit unit) { unitTargets.add(unit); }
    public void prioritize(RPGridProp prop) { propTargets.add(prop); }

    public GridPathfinder.Things priorities() {
        final GridPathfinder.Things returnValue = new GridPathfinder.Things();
        for(GridTile tile : tileTargets) {
            returnValue.tiles().put(tile, new GridPath());
        }
        for(RPGridUnit unit : unitTargets) {
            returnValue.add(unit, new GridPath());
        }
        for(RPGridProp prop : propTargets) {
            returnValue.add(prop, new GridPath());
        }
        return returnValue;
    }
}
