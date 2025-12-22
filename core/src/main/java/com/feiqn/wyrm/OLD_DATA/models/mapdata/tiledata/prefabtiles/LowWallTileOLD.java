package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class LowWallTileOLD extends OLD_LogicalTile {
    public LowWallTileOLD(WYRMGame game, float column, float row) {
        super(game, column, row);

        tileType = LogicalTileType.LOW_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = true;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }
}
