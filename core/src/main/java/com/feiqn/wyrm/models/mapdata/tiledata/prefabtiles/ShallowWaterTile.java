package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class ShallowWaterTile extends LogicalTile {

    public ShallowWaterTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.SHALLOW_WATER;

        movementCost.put(MovementType.INFANTRY, 2f);
        movementCost.put(MovementType.CAVALRY, 2.5f);

        isTraversableByWheels = false;
    }

}
