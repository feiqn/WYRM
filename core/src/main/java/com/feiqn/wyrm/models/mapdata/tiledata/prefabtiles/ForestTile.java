package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class ForestTile extends LogicalTile {
    public ForestTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.FOREST;

        visionReduction = 1;
        movementCost.put(MovementType.INFANTRY, 1.5f);
        movementCost.put(MovementType.CAVALRY, 2f);

    }

}
