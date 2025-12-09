package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class RoadTileOLD extends OLD_LogicalTile {

    public RoadTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        this.tileType = LogicalTileType.ROAD;

        movementCost.put(MovementType.CAVALRY, .5f);
        movementCost.put(MovementType.INFANTRY, .5f);
        movementCost.put(MovementType.WHEELS, 1f);

    }
}
