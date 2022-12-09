package com.feiqn.wyrm.models.mapdata.prefabLogicalMaps;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class stage_debug extends WyrMap {

    public stage_debug(WYRMGame game) {
        super(game, 10);
        setUpLogicalTiles();
    }

    private void setUpLogicalTiles() {
        final Array<LogicalTile> roadTiles = new Array<>();

        roadTiles.add(internalLogicalMap[1][4]);
        roadTiles.add(internalLogicalMap[1][5]);
        roadTiles.add(internalLogicalMap[1][6]);
        roadTiles.add(internalLogicalMap[1][7]);
        roadTiles.add(internalLogicalMap[1][8]);

        roadTiles.add(internalLogicalMap[2][4]);
        roadTiles.add(internalLogicalMap[2][5]);
        roadTiles.add(internalLogicalMap[2][6]);
        roadTiles.add(internalLogicalMap[2][7]);
        roadTiles.add(internalLogicalMap[2][8]);

        roadTiles.add(internalLogicalMap[3][7]);
        roadTiles.add(internalLogicalMap[3][8]);

        roadTiles.add(internalLogicalMap[4][7]);
        roadTiles.add(internalLogicalMap[4][8]);

        roadTiles.add(internalLogicalMap[5][7]);
        roadTiles.add(internalLogicalMap[5][8]);

        setLogicalTilesToType(roadTiles, LogicalTileType.ROAD);

        final Array<LogicalTile> roughHillTiles = new Array<>();

        roughHillTiles.add(internalLogicalMap[5][0]);
        roughHillTiles.add(internalLogicalMap[5][1]);
        roughHillTiles.add(internalLogicalMap[5][2]);
        roughHillTiles.add(internalLogicalMap[5][3]);
        roughHillTiles.add(internalLogicalMap[5][4]);
        roughHillTiles.add(internalLogicalMap[5][5]);

        roughHillTiles.add(internalLogicalMap[6][0]);
        roughHillTiles.add(internalLogicalMap[6][1]);
        roughHillTiles.add(internalLogicalMap[6][2]);
        roughHillTiles.add(internalLogicalMap[6][3]);
        roughHillTiles.add(internalLogicalMap[6][4]);
        roughHillTiles.add(internalLogicalMap[6][5]);

        setLogicalTilesToType(roughHillTiles, LogicalTileType.ROUGH_HILLS);

        setLogicalTileToType(8, 1, LogicalTileType.MOUNTAIN);

        setLogicalTileToType(8, 3, LogicalTileType.FOREST);

        setLogicalTileToType(8, 5, LogicalTileType.FORTRESS);

        final Array<LogicalTile> impassibleTiles = new Array<>();

        impassibleTiles.add(internalLogicalMap[8][8]);
        impassibleTiles.add(internalLogicalMap[7][8]);

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

    }
}
