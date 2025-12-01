package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class RoadTile extends LogicalTile {

    public RoadTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        this.tileType = LogicalTileType.ROAD;

        movementCost.put(MovementType.CAVALRY, .5f);
        movementCost.put(MovementType.INFANTRY, .5f);
        movementCost.put(MovementType.WHEELS, 1f);

    }
}
