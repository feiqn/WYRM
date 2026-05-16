package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.TileType;

public class DeepWaterTileOLD extends OLD_LogicalTile {

    public DeepWaterTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = TileType.DEEP_WATER;

        isTraversableByCavalry = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;
        isTraversableByBoats = true;

    }
}
