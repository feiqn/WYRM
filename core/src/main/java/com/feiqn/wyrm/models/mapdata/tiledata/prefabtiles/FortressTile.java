package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class FortressTile extends LogicalTile {
    public FortressTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.FORTRESS;

        defenseValue = 2;

        movementCost.put(MovementType.WHEELS, 1f);
    }


}
