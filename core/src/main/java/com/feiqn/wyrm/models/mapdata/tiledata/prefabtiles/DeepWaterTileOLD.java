package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class DeepWaterTileOLD extends OLD_LogicalTile {

    public DeepWaterTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.DEEP_WATER;

        isTraversableByCavalry = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;
        isTraversableByBoats = true;

    }
}
