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

        impassibleTiles.add(internalLogicalMap[8][20]);
        impassibleTiles.add(internalLogicalMap[8][21]);
        impassibleTiles.add(internalLogicalMap[8][26]);
        impassibleTiles.add(internalLogicalMap[8][27]);
        impassibleTiles.add(internalLogicalMap[8][28]);

        impassibleTiles.add(internalLogicalMap[9][19]);
        impassibleTiles.add(internalLogicalMap[9][22]);
        impassibleTiles.add(internalLogicalMap[9][23]);
        impassibleTiles.add(internalLogicalMap[9][24]);
        impassibleTiles.add(internalLogicalMap[9][25]);
        impassibleTiles.add(internalLogicalMap[9][29]);

        impassibleTiles.add(internalLogicalMap[10][17]);
        impassibleTiles.add(internalLogicalMap[10][18]);
        impassibleTiles.add(internalLogicalMap[10][21]);
        impassibleTiles.add(internalLogicalMap[10][23]);
        impassibleTiles.add(internalLogicalMap[10][24]);
        impassibleTiles.add(internalLogicalMap[10][25]);
        impassibleTiles.add(internalLogicalMap[10][27]);
        impassibleTiles.add(internalLogicalMap[10][30]);

        impassibleTiles.add(internalLogicalMap[11][16]);
        impassibleTiles.add(internalLogicalMap[11][19]);
        impassibleTiles.add(internalLogicalMap[11][20]);
        impassibleTiles.add(internalLogicalMap[11][22]);
        impassibleTiles.add(internalLogicalMap[11][24]);
        impassibleTiles.add(internalLogicalMap[11][25]);
        impassibleTiles.add(internalLogicalMap[11][26]);
        impassibleTiles.add(internalLogicalMap[11][27]);
        impassibleTiles.add(internalLogicalMap[11][29]);
        impassibleTiles.add(internalLogicalMap[11][30]);
        impassibleTiles.add(internalLogicalMap[11][31]);
        impassibleTiles.add(internalLogicalMap[11][32]);

        for(int i = 0; i < 19; i++) {
            impassibleTiles.add(internalLogicalMap[12][i]);
        }

        impassibleTiles.add(internalLogicalMap[12][25]);
        impassibleTiles.add(internalLogicalMap[12][26]);
        impassibleTiles.add(internalLogicalMap[12][28]);

        for(int i = 32; i < 49; i++) {
            impassibleTiles.add(internalLogicalMap[12][i]);
        }

        final Array<LogicalTile> lowWallTiles = new Array<>();

        lowWallTiles.add(internalLogicalMap[13][2]);
        lowWallTiles.add(internalLogicalMap[13][5]);
        lowWallTiles.add(internalLogicalMap[13][13]);
        lowWallTiles.add(internalLogicalMap[13][16]);
        lowWallTiles.add(internalLogicalMap[13][18]);
        lowWallTiles.add(internalLogicalMap[13][23]);
        lowWallTiles.add(internalLogicalMap[13][29]);
        lowWallTiles.add(internalLogicalMap[13][32]);
        lowWallTiles.add(internalLogicalMap[13][34]);
        lowWallTiles.add(internalLogicalMap[13][39]);
        lowWallTiles.add(internalLogicalMap[13][44]);
        lowWallTiles.add(internalLogicalMap[13][49]);

        lowWallTiles.add(internalLogicalMap[14][1]);
        lowWallTiles.add(internalLogicalMap[14][4]);
        lowWallTiles.add(internalLogicalMap[14][6]);
        lowWallTiles.add(internalLogicalMap[14][8]);
        lowWallTiles.add(internalLogicalMap[14][9]);
        lowWallTiles.add(internalLogicalMap[14][10]);
        lowWallTiles.add(internalLogicalMap[14][14]);
        lowWallTiles.add(internalLogicalMap[14][17]);
        lowWallTiles.add(internalLogicalMap[14][19]);
        lowWallTiles.add(internalLogicalMap[14][27]);
        lowWallTiles.add(internalLogicalMap[14][33]);
        lowWallTiles.add(internalLogicalMap[14][36]);
        lowWallTiles.add(internalLogicalMap[14][40]);
        lowWallTiles.add(internalLogicalMap[14][45]);
        lowWallTiles.add(internalLogicalMap[14][48]);

        lowWallTiles.add(internalLogicalMap[15][0]);
        lowWallTiles.add(internalLogicalMap[15][2]);
        lowWallTiles.add(internalLogicalMap[15][7]);
        lowWallTiles.add(internalLogicalMap[15][11]);
        lowWallTiles.add(internalLogicalMap[15][17]);
        lowWallTiles.add(internalLogicalMap[15][20]);
        lowWallTiles.add(internalLogicalMap[15][29]);
        lowWallTiles.add(internalLogicalMap[15][33]);
        lowWallTiles.add(internalLogicalMap[15][41]);
        lowWallTiles.add(internalLogicalMap[15][43]);
        lowWallTiles.add(internalLogicalMap[15][46]);

        lowWallTiles.add(internalLogicalMap[16][12]);
        lowWallTiles.add(internalLogicalMap[16][13]);
        lowWallTiles.add(internalLogicalMap[16][14]);
        lowWallTiles.add(internalLogicalMap[16][15]);
        lowWallTiles.add(internalLogicalMap[16][18]);
        lowWallTiles.add(internalLogicalMap[16][20]);
        lowWallTiles.add(internalLogicalMap[16][21]);
        lowWallTiles.add(internalLogicalMap[16][27]);
        lowWallTiles.add(internalLogicalMap[16][32]);
        lowWallTiles.add(internalLogicalMap[16][35]);
        lowWallTiles.add(internalLogicalMap[16][38]);
        lowWallTiles.add(internalLogicalMap[16][42]);
        lowWallTiles.add(internalLogicalMap[16][48]);

        lowWallTiles.add(internalLogicalMap[17][16]);
        lowWallTiles.add(internalLogicalMap[17][29]);
        lowWallTiles.add(internalLogicalMap[17][34]);
        lowWallTiles.add(internalLogicalMap[17][43]);
        lowWallTiles.add(internalLogicalMap[17][44]);
        lowWallTiles.add(internalLogicalMap[17][47]);

        lowWallTiles.add(internalLogicalMap[18][17]);
        lowWallTiles.add(internalLogicalMap[18][18]);
        lowWallTiles.add(internalLogicalMap[18][21]);
        lowWallTiles.add(internalLogicalMap[18][36]);
        lowWallTiles.add(internalLogicalMap[18][45]);
        lowWallTiles.add(internalLogicalMap[18][46]);
        lowWallTiles.add(internalLogicalMap[18][48]);

        lowWallTiles.add(internalLogicalMap[19][17]);
        lowWallTiles.add(internalLogicalMap[19][19]);
        lowWallTiles.add(internalLogicalMap[19][26]);
        lowWallTiles.add(internalLogicalMap[19][32]);
        lowWallTiles.add(internalLogicalMap[19][35]);
        lowWallTiles.add(internalLogicalMap[19][36]);
        lowWallTiles.add(internalLogicalMap[19][40]);
        lowWallTiles.add(internalLogicalMap[19][49]);

        lowWallTiles.add(internalLogicalMap[20][2]);
        lowWallTiles.add(internalLogicalMap[20][4]);
        lowWallTiles.add(internalLogicalMap[20][14]);
        lowWallTiles.add(internalLogicalMap[20][16]);
        lowWallTiles.add(internalLogicalMap[20][21]);
        lowWallTiles.add(internalLogicalMap[20][30]);
        lowWallTiles.add(internalLogicalMap[20][34]);

        lowWallTiles.add(internalLogicalMap[21][1]);
        lowWallTiles.add(internalLogicalMap[21][11]);
        lowWallTiles.add(internalLogicalMap[21][14]);
        lowWallTiles.add(internalLogicalMap[21][15]);
        lowWallTiles.add(internalLogicalMap[21][17]);
        lowWallTiles.add(internalLogicalMap[21][36]);
        lowWallTiles.add(internalLogicalMap[21][37]);
        lowWallTiles.add(internalLogicalMap[21][42]);
        lowWallTiles.add(internalLogicalMap[21][46]);

        lowWallTiles.add(internalLogicalMap[22][0]);
        lowWallTiles.add(internalLogicalMap[22][2]);
        lowWallTiles.add(internalLogicalMap[22][8]);
        lowWallTiles.add(internalLogicalMap[22][10]);
        lowWallTiles.add(internalLogicalMap[22][12]);
        lowWallTiles.add(internalLogicalMap[22][13]);
        lowWallTiles.add(internalLogicalMap[22][14]);
        lowWallTiles.add(internalLogicalMap[22][15]);
        lowWallTiles.add(internalLogicalMap[22][17]);
        lowWallTiles.add(internalLogicalMap[22][33]);
        lowWallTiles.add(internalLogicalMap[22][35]);
        lowWallTiles.add(internalLogicalMap[22][36]);
        lowWallTiles.add(internalLogicalMap[22][37]);
        lowWallTiles.add(internalLogicalMap[22][40]);
        lowWallTiles.add(internalLogicalMap[22][48]);

        lowWallTiles.add(internalLogicalMap[23][3]);
        lowWallTiles.add(internalLogicalMap[23][6]);
        lowWallTiles.add(internalLogicalMap[23][7]);
        lowWallTiles.add(internalLogicalMap[23][9]);
        lowWallTiles.add(internalLogicalMap[23][20]);
        lowWallTiles.add(internalLogicalMap[23][28]);
        lowWallTiles.add(internalLogicalMap[23][33]);
        lowWallTiles.add(internalLogicalMap[23][44]);
        lowWallTiles.add(internalLogicalMap[23][46]);

        lowWallTiles.add(internalLogicalMap[24][1]);
        lowWallTiles.add(internalLogicalMap[24][4]);
        lowWallTiles.add(internalLogicalMap[24][5]);
        lowWallTiles.add(internalLogicalMap[24][11]);
        lowWallTiles.add(internalLogicalMap[24][13]);
        lowWallTiles.add(internalLogicalMap[24][17]);
        lowWallTiles.add(internalLogicalMap[24][25]);
        lowWallTiles.add(internalLogicalMap[24][38]);
        lowWallTiles.add(internalLogicalMap[24][42]);
        lowWallTiles.add(internalLogicalMap[24][49]);

        lowWallTiles.add(internalLogicalMap[25][7]);
        lowWallTiles.add(internalLogicalMap[25][8]);
        lowWallTiles.add(internalLogicalMap[25][14]);
        lowWallTiles.add(internalLogicalMap[25][29]);
        lowWallTiles.add(internalLogicalMap[25][45]);
        lowWallTiles.add(internalLogicalMap[25][48]);

        lowWallTiles.add(internalLogicalMap[26][1]);
        lowWallTiles.add(internalLogicalMap[26][3]);
        lowWallTiles.add(internalLogicalMap[26][10]);
        lowWallTiles.add(internalLogicalMap[26][20]);
        lowWallTiles.add(internalLogicalMap[26][37]);
        lowWallTiles.add(internalLogicalMap[26][39]);
        lowWallTiles.add(internalLogicalMap[26][43]);
        lowWallTiles.add(internalLogicalMap[26][47]);

        // FOREST TILES
        // ROAD TILES
        // OBJECTIVE TILES

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);
        setLogicalTilesToType(lowWallTiles, LogicalTileType.LOW_WALL);

    }
}
