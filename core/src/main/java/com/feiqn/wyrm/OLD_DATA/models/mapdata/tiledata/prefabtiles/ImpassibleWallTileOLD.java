package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.TileType;

public class ImpassibleWallTileOLD extends OLD_LogicalTile {

    public ImpassibleWallTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = TileType.IMPASSIBLE_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }

}
