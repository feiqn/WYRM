package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class MountainTileOLD extends OLD_LogicalTile {
    public MountainTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.MOUNTAIN;

        isTraversableByCavalry = false;
        isTraversableByWheels = false;

        movementCost.put(MovementType.INFANTRY, 2f);
    }

}
