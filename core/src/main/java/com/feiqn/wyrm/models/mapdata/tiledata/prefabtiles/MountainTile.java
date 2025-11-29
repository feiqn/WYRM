package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class MountainTile extends LogicalTile {
    public MountainTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.MOUNTAIN;

        isTraversableByCavalry = false;
        isTraversableByWheels = false;

        movementCost.put(MovementType.INFANTRY, 2f);
    }

}
