package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class ImpassibleWallTileOLD extends OLD_LogicalTile {

    public ImpassibleWallTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = LogicalTileType.IMPASSIBLE_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }

}
