package com.feiqn.wyrm.models.mapdata.prefabLogicalMaps;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class stage_1a extends WyrMap {

    public stage_1a(WYRMGame game) {
        super(game, 50);
        setUpLogicalTiles();
    }

    private void setUpLogicalTiles() {
        final Array<LogicalTile> impassibleTiles = new Array<>();

        for(int i = 0; i < 19; i++) {
            impassibleTiles.add(internalLogicalMap[12][i]);
        }

        impassibleTiles.add(internalLogicalMap[12][25]);
        impassibleTiles.add(internalLogicalMap[12][26]);
        impassibleTiles.add(internalLogicalMap[12][28]);

        for(int i = 32; i < 49; i++) {
            impassibleTiles.add(internalLogicalMap[12][i]);
        }

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

        debugShowAllTilesOfType(LogicalTileType.IMPASSIBLE_WALL);
    }
}
