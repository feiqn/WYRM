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

        roadTiles.add(logicalMap[1][4]);
        roadTiles.add(logicalMap[1][5]);
        roadTiles.add(logicalMap[1][6]);
        roadTiles.add(logicalMap[1][7]);
        roadTiles.add(logicalMap[1][8]);

        roadTiles.add(logicalMap[2][4]);
        roadTiles.add(logicalMap[2][5]);
        roadTiles.add(logicalMap[2][6]);
        roadTiles.add(logicalMap[2][7]);
        roadTiles.add(logicalMap[2][8]);

        roadTiles.add(logicalMap[3][7]);
        roadTiles.add(logicalMap[3][8]);

        roadTiles.add(logicalMap[4][7]);
        roadTiles.add(logicalMap[4][8]);

        roadTiles.add(logicalMap[5][7]);
        roadTiles.add(logicalMap[5][8]);

        setLogicalTilesToType(roadTiles, LogicalTileType.ROAD);

        final Array<LogicalTile> roughHillTiles = new Array<>();

        roughHillTiles.add(logicalMap[5][0]);
        roughHillTiles.add(logicalMap[5][1]);
        roughHillTiles.add(logicalMap[5][2]);
        roughHillTiles.add(logicalMap[5][3]);
        roughHillTiles.add(logicalMap[5][4]);
        roughHillTiles.add(logicalMap[5][5]);

        roughHillTiles.add(logicalMap[6][0]);
        roughHillTiles.add(logicalMap[6][1]);
        roughHillTiles.add(logicalMap[6][2]);
        roughHillTiles.add(logicalMap[6][3]);
        roughHillTiles.add(logicalMap[6][4]);
        roughHillTiles.add(logicalMap[6][5]);

        setLogicalTilesToType(roughHillTiles, LogicalTileType.ROUGH_HILLS);

        setLogicalTileToType(8, 1, LogicalTileType.MOUNTAIN);

        setLogicalTileToType(8, 3, LogicalTileType.FOREST);

        setLogicalTileToType(8, 5, LogicalTileType.FORTRESS);

        final Array<LogicalTile> impassibleTiles = new Array<>();

        impassibleTiles.add(logicalMap[8][8]);
        impassibleTiles.add(logicalMap[7][8]);

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

    }
}
