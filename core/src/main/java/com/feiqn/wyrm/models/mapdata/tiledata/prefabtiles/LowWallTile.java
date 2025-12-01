package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class LowWallTile extends LogicalTile {
    public LowWallTile(WYRMGame game, float column, float row) {
        super(game, column, row);

        tileType = LogicalTileType.LOW_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = true;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }
}
