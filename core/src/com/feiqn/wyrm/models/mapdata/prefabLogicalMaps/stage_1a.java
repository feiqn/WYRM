package com.feiqn.wyrm.models.mapdata.prefabLogicalMaps;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class stage_1a extends WyrMap {

    public stage_1a(WYRMGame game) {
        super(game, 50);
//        setUpLogicalTiles();
    }

    private void setUpLogicalTiles() {
        final Array<LogicalTile> impassibleTiles = new Array<>();

        for (int i = 0; i < 18; i++) {
            impassibleTiles.add(logicalMap[12][i]);
        }

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

        debugShowAllTilesOfType(LogicalTileType.IMPASSIBLE_WALL);
    }
}
