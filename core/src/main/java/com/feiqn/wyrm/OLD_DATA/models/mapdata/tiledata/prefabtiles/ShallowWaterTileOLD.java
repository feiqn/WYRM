package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;

public class ShallowWaterTileOLD extends OLD_LogicalTile {

    public ShallowWaterTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.SHALLOW_WATER;

        movementCost.put(RPGridMovementType.INFANTRY, 2f);
        movementCost.put(RPGridMovementType.CAVALRY, 2.5f);

        isTraversableByWheels = false;
    }

}
