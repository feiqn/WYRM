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

        impassibleTiles.add(internalLogicalMap[13][2]);
        impassibleTiles.add(internalLogicalMap[13][5]);
        impassibleTiles.add(internalLogicalMap[13][13]);
        impassibleTiles.add(internalLogicalMap[13][16]);
        impassibleTiles.add(internalLogicalMap[13][18]);
        impassibleTiles.add(internalLogicalMap[13][23]);
        impassibleTiles.add(internalLogicalMap[13][29]);
        impassibleTiles.add(internalLogicalMap[13][32]);
        impassibleTiles.add(internalLogicalMap[13][34]);
        impassibleTiles.add(internalLogicalMap[13][39]);
        impassibleTiles.add(internalLogicalMap[13][44]);
        impassibleTiles.add(internalLogicalMap[13][49]);

        impassibleTiles.add(internalLogicalMap[14][1]);
        impassibleTiles.add(internalLogicalMap[14][4]);
        impassibleTiles.add(internalLogicalMap[14][6]);
        impassibleTiles.add(internalLogicalMap[14][8]);
        impassibleTiles.add(internalLogicalMap[14][9]);
        impassibleTiles.add(internalLogicalMap[14][10]);
        impassibleTiles.add(internalLogicalMap[14][14]);
        impassibleTiles.add(internalLogicalMap[14][17]);
        impassibleTiles.add(internalLogicalMap[14][19]);
        impassibleTiles.add(internalLogicalMap[14][27]);
        impassibleTiles.add(internalLogicalMap[14][33]);
        impassibleTiles.add(internalLogicalMap[14][36]);
        impassibleTiles.add(internalLogicalMap[14][40]);
        impassibleTiles.add(internalLogicalMap[14][45]);
        impassibleTiles.add(internalLogicalMap[14][48]);

        impassibleTiles.add(internalLogicalMap[15][0]);
        impassibleTiles.add(internalLogicalMap[15][2]);
        impassibleTiles.add(internalLogicalMap[15][7]);
        impassibleTiles.add(internalLogicalMap[15][11]);
        impassibleTiles.add(internalLogicalMap[15][17]);
        impassibleTiles.add(internalLogicalMap[15][20]);
        impassibleTiles.add(internalLogicalMap[15][29]);
        impassibleTiles.add(internalLogicalMap[15][33]);
        impassibleTiles.add(internalLogicalMap[15][41]);
        impassibleTiles.add(internalLogicalMap[15][43]);
        impassibleTiles.add(internalLogicalMap[15][46]);

        impassibleTiles.add(internalLogicalMap[16][12]);
        impassibleTiles.add(internalLogicalMap[16][13]);
        impassibleTiles.add(internalLogicalMap[16][14]);
        impassibleTiles.add(internalLogicalMap[16][15]);
        impassibleTiles.add(internalLogicalMap[16][18]);
        impassibleTiles.add(internalLogicalMap[16][20]);
        impassibleTiles.add(internalLogicalMap[16][21]);
        impassibleTiles.add(internalLogicalMap[16][27]);
        impassibleTiles.add(internalLogicalMap[16][32]);
        impassibleTiles.add(internalLogicalMap[16][35]);
        impassibleTiles.add(internalLogicalMap[16][38]);
        impassibleTiles.add(internalLogicalMap[16][42]);
        impassibleTiles.add(internalLogicalMap[16][48]);

        impassibleTiles.add(internalLogicalMap[17][16]);
        impassibleTiles.add(internalLogicalMap[17][29]);
        impassibleTiles.add(internalLogicalMap[17][34]);
        impassibleTiles.add(internalLogicalMap[17][43]);
        impassibleTiles.add(internalLogicalMap[17][44]);
        impassibleTiles.add(internalLogicalMap[17][47]);

        impassibleTiles.add(internalLogicalMap[18][17]);
        impassibleTiles.add(internalLogicalMap[18][18]);
        impassibleTiles.add(internalLogicalMap[18][21]);
        impassibleTiles.add(internalLogicalMap[18][36]);
        impassibleTiles.add(internalLogicalMap[18][45]);
        impassibleTiles.add(internalLogicalMap[18][46]);
        impassibleTiles.add(internalLogicalMap[18][48]);

        impassibleTiles.add(internalLogicalMap[19][17]);
        impassibleTiles.add(internalLogicalMap[19][19]);
        impassibleTiles.add(internalLogicalMap[19][26]);
        impassibleTiles.add(internalLogicalMap[19][32]);
        impassibleTiles.add(internalLogicalMap[19][35]);
        impassibleTiles.add(internalLogicalMap[19][36]);
        impassibleTiles.add(internalLogicalMap[19][40]);
        impassibleTiles.add(internalLogicalMap[19][49]);

        impassibleTiles.add(internalLogicalMap[20][2]);
        impassibleTiles.add(internalLogicalMap[20][4]);
        impassibleTiles.add(internalLogicalMap[20][14]);
        impassibleTiles.add(internalLogicalMap[20][16]);
        impassibleTiles.add(internalLogicalMap[20][21]);
        impassibleTiles.add(internalLogicalMap[20][30]);
        impassibleTiles.add(internalLogicalMap[20][34]);

        impassibleTiles.add(internalLogicalMap[21][1]);
        impassibleTiles.add(internalLogicalMap[21][11]);
        impassibleTiles.add(internalLogicalMap[21][14]);
        impassibleTiles.add(internalLogicalMap[21][15]);
        impassibleTiles.add(internalLogicalMap[21][17]);
        impassibleTiles.add(internalLogicalMap[21][36]);
        impassibleTiles.add(internalLogicalMap[21][37]);
        impassibleTiles.add(internalLogicalMap[21][42]);
        impassibleTiles.add(internalLogicalMap[21][46]);

        impassibleTiles.add(internalLogicalMap[22][0]);
        impassibleTiles.add(internalLogicalMap[22][2]);
        impassibleTiles.add(internalLogicalMap[22][8]);
        impassibleTiles.add(internalLogicalMap[22][10]);
        impassibleTiles.add(internalLogicalMap[22][12]);
        impassibleTiles.add(internalLogicalMap[22][13]);
        impassibleTiles.add(internalLogicalMap[22][14]);
        impassibleTiles.add(internalLogicalMap[22][15]);
        impassibleTiles.add(internalLogicalMap[22][17]);
        impassibleTiles.add(internalLogicalMap[22][33]);
        impassibleTiles.add(internalLogicalMap[22][35]);
        impassibleTiles.add(internalLogicalMap[22][36]);
        impassibleTiles.add(internalLogicalMap[22][37]);
        impassibleTiles.add(internalLogicalMap[22][40]);
        impassibleTiles.add(internalLogicalMap[22][48]);

        impassibleTiles.add(internalLogicalMap[23][3]);
        impassibleTiles.add(internalLogicalMap[23][6]);
        impassibleTiles.add(internalLogicalMap[23][7]);
        impassibleTiles.add(internalLogicalMap[23][9]);
        impassibleTiles.add(internalLogicalMap[23][20]);
        impassibleTiles.add(internalLogicalMap[23][28]);
        impassibleTiles.add(internalLogicalMap[23][33]);
        impassibleTiles.add(internalLogicalMap[23][44]);
        impassibleTiles.add(internalLogicalMap[23][46]);

        impassibleTiles.add(internalLogicalMap[24][1]);
        impassibleTiles.add(internalLogicalMap[24][4]);
        impassibleTiles.add(internalLogicalMap[24][5]);
        impassibleTiles.add(internalLogicalMap[24][11]);
        impassibleTiles.add(internalLogicalMap[24][13]);
        impassibleTiles.add(internalLogicalMap[24][17]);
        impassibleTiles.add(internalLogicalMap[24][25]);
        impassibleTiles.add(internalLogicalMap[24][38]);
        impassibleTiles.add(internalLogicalMap[24][42]);
        impassibleTiles.add(internalLogicalMap[24][49]);

        impassibleTiles.add(internalLogicalMap[25][7]);
        impassibleTiles.add(internalLogicalMap[25][8]);
        impassibleTiles.add(internalLogicalMap[25][14]);
        impassibleTiles.add(internalLogicalMap[25][29]);
        impassibleTiles.add(internalLogicalMap[25][45]);
        impassibleTiles.add(internalLogicalMap[25][48]);

        impassibleTiles.add(internalLogicalMap[26][1]);
        impassibleTiles.add(internalLogicalMap[26][3]);
        impassibleTiles.add(internalLogicalMap[26][10]);
        impassibleTiles.add(internalLogicalMap[26][20]);
        impassibleTiles.add(internalLogicalMap[26][37]);
        impassibleTiles.add(internalLogicalMap[26][39]);
        impassibleTiles.add(internalLogicalMap[26][43]);
        impassibleTiles.add(internalLogicalMap[26][47]);


        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);

        debugShowAllTilesOfType(LogicalTileType.IMPASSIBLE_WALL);
    }
}
