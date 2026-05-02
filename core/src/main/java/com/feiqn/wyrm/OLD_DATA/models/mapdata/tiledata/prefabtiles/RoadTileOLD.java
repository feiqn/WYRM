package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridMovementType;

public class RoadTileOLD extends OLD_LogicalTile {

    public RoadTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        this.tileType = LogicalTileType.ROAD;

        movementCost.put(RPGridMovementType.CAVALRY, .5f);
        movementCost.put(RPGridMovementType.INFANTRY, .5f);
        movementCost.put(RPGridMovementType.WHEELS, 1f);

    }
}
