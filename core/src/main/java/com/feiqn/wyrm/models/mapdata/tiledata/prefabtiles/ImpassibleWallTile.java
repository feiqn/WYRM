package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles.LogicalTileType;

public class ImpassibleWallTile extends LogicalTile {

    public ImpassibleWallTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = LogicalTileType.IMPASSIBLE_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }

}
