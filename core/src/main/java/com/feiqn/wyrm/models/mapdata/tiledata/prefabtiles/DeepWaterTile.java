package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles.LogicalTileType;

public class DeepWaterTile extends LogicalTile {

    public DeepWaterTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.DEEP_WATER;

        isTraversableByCavalry = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;
        isTraversableByBoats = true;

    }
}
