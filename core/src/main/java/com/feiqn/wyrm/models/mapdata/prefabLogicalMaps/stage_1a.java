package com.feiqn.wyrm.models.mapdata.prefabLogicalMaps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses.SoldierClass;
import com.feiqn.wyrm.models.unitdata.units.player.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class stage_1a extends WyrMap {

    public stage_1a(WYRMGame game) {
        super(game, 50);
        setUpLogicalTiles();
    }

    public void setUpUnits() {

        final Ballista ballista = new Ballista(game);

        ballista.setSize(1,1.5f);

        placeMapObjectAtPosition(ballista, 19, 10);

        game.activeBattleScreen.ballistaObjects.add(ballista);
        game.activeBattleScreen.rootGroup.addActor(ballista);

        final Unit testEnemy = new Unit(game, game.assetHandler.soldierTexture);
        testEnemy.setSize(1,1);
        testEnemy.setColor(Color.RED);
        testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
        testEnemy.setAIType(AIType.AGGRESSIVE);
        testEnemy.name = "Evil Timn";
        testEnemy.setUnitClass(new SoldierClass(game));

        placeUnitAtPosition(testEnemy, 15, 36);

        game.activeBattleScreen.enemyTeam.add(testEnemy);
        Gdx.app.log("stage", "et size: " + game.activeBattleScreen.enemyTeam.size);
        game.activeBattleScreen.rootGroup.addActor(testEnemy);

        final LeifUnit testChar = new LeifUnit(game);
        testChar.setSize(1, 1.5f);

        placeUnitAtPosition(testChar, 15, 3);

        game.activeBattleScreen.playerTeam.add(testChar);
        game.activeBattleScreen.rootGroup.addActor(testChar);

        final AntalUnit antalChar = new AntalUnit(game);
        antalChar.setSize(1,1);
        antalChar.setTeamAlignment(TeamAlignment.ALLY);
        antalChar.setColor(Color.GREEN);

        placeUnitAtPosition(antalChar, 15, 23);

        game.activeBattleScreen.allyTeam.add(antalChar);
        game.activeBattleScreen.rootGroup.addActor(antalChar);

        // game.activebattlemap. add enemies for stage
        // place player and other units for stage

    }

    private void setUpLogicalTiles() {
        // TODO: do better

        setLogicalTileToType(18,0, LogicalTileType.OBJECTIVE_ESCAPE);
        internalLogicalMap[18][0].setObjectiveUnit(UnitRoster.LEIF);
        internalLogicalMap[18][0].highlightCanSupport();

        setLogicalTileToType(19, 25, LogicalTileType.OBJECTIVE_ESCAPE);
        internalLogicalMap[19][25].setObjectiveUnit(UnitRoster.ANTAL);
        internalLogicalMap[19][25].highlightCanSupport();

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

        for(int i = 0; i <= 19; i++) {
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

        lowWallTiles.add(internalLogicalMap[27][3]);
        lowWallTiles.add(internalLogicalMap[27][4]);
        lowWallTiles.add(internalLogicalMap[27][8]);
        lowWallTiles.add(internalLogicalMap[27][12]);
        lowWallTiles.add(internalLogicalMap[27][32]);

        lowWallTiles.add(internalLogicalMap[28][1]);
        lowWallTiles.add(internalLogicalMap[28][14]);
        lowWallTiles.add(internalLogicalMap[28][18]);
        lowWallTiles.add(internalLogicalMap[28][32]);
        lowWallTiles.add(internalLogicalMap[28][44]);
        lowWallTiles.add(internalLogicalMap[28][47]);

        lowWallTiles.add(internalLogicalMap[29][2]);
        lowWallTiles.add(internalLogicalMap[29][7]);
        lowWallTiles.add(internalLogicalMap[29][26]);
        lowWallTiles.add(internalLogicalMap[29][36]);
        lowWallTiles.add(internalLogicalMap[29][37]);
        lowWallTiles.add(internalLogicalMap[29][38]);
        lowWallTiles.add(internalLogicalMap[29][41]);

        lowWallTiles.add(internalLogicalMap[30][0]);
        lowWallTiles.add(internalLogicalMap[30][5]);
        lowWallTiles.add(internalLogicalMap[30][10]);
        lowWallTiles.add(internalLogicalMap[30][44]);
        lowWallTiles.add(internalLogicalMap[30][48]);

        lowWallTiles.add(internalLogicalMap[31][2]);
        lowWallTiles.add(internalLogicalMap[31][21]);
        lowWallTiles.add(internalLogicalMap[31][29]);

        lowWallTiles.add(internalLogicalMap[32][7]);

        lowWallTiles.add(internalLogicalMap[33][47]);

        lowWallTiles.add(internalLogicalMap[34][1]);

        lowWallTiles.add(internalLogicalMap[35][18]);

        lowWallTiles.add(internalLogicalMap[36][32]);
        lowWallTiles.add(internalLogicalMap[36][45]);

        lowWallTiles.add(internalLogicalMap[38][7]);

        lowWallTiles.add(internalLogicalMap[43][29]);

        lowWallTiles.add(internalLogicalMap[45][20]);


        // FOREST TILES
        final Array<LogicalTile> forestTiles = new Array<>();

        forestTiles.add(internalLogicalMap[19][47]);

        forestTiles.add(internalLogicalMap[22][45]);

        forestTiles.add(internalLogicalMap[23][39]);
        forestTiles.add(internalLogicalMap[23][47]);
        forestTiles.add(internalLogicalMap[23][48]);

        forestTiles.add(internalLogicalMap[24][43]);

        forestTiles.add(internalLogicalMap[25][40]);
        forestTiles.add(internalLogicalMap[25][41]);

        forestTiles.add(internalLogicalMap[27][36]);

        forestTiles.add(internalLogicalMap[28][19]);
        forestTiles.add(internalLogicalMap[28][33]);
        forestTiles.add(internalLogicalMap[28][35]);
        forestTiles.add(internalLogicalMap[29][16]);
        forestTiles.add(internalLogicalMap[29][17]);
        forestTiles.add(internalLogicalMap[29][20]);
        forestTiles.add(internalLogicalMap[29][25]);
        forestTiles.add(internalLogicalMap[29][30]);
        forestTiles.add(internalLogicalMap[29][31]);
        forestTiles.add(internalLogicalMap[29][32]);

        forestTiles.add(internalLogicalMap[30][15]);
        forestTiles.add(internalLogicalMap[30][25]);
        forestTiles.add(internalLogicalMap[30][29]);
        forestTiles.add(internalLogicalMap[30][34]);
        forestTiles.add(internalLogicalMap[30][35]);
        forestTiles.add(internalLogicalMap[30][38]);

        forestTiles.add(internalLogicalMap[31][14]);
        forestTiles.add(internalLogicalMap[31][18]);
        forestTiles.add(internalLogicalMap[31][25]);
        forestTiles.add(internalLogicalMap[31][31]);
        forestTiles.add(internalLogicalMap[31][32]);
        forestTiles.add(internalLogicalMap[31][38]);
        forestTiles.add(internalLogicalMap[31][39]);
        forestTiles.add(internalLogicalMap[31][40]);
        forestTiles.add(internalLogicalMap[31][47]);

        forestTiles.add(internalLogicalMap[32][12]);
        forestTiles.add(internalLogicalMap[32][13]);
        forestTiles.add(internalLogicalMap[32][16]);
        forestTiles.add(internalLogicalMap[32][19]);
        forestTiles.add(internalLogicalMap[32][21]);
        forestTiles.add(internalLogicalMap[32][25]);
        forestTiles.add(internalLogicalMap[32][28]);
        forestTiles.add(internalLogicalMap[32][34]);
        forestTiles.add(internalLogicalMap[32][44]);
        forestTiles.add(internalLogicalMap[32][45]);
        forestTiles.add(internalLogicalMap[32][46]);
        forestTiles.add(internalLogicalMap[32][49]);

        forestTiles.add(internalLogicalMap[33][17]);
        forestTiles.add(internalLogicalMap[33][20]);
        forestTiles.add(internalLogicalMap[33][25]);
        forestTiles.add(internalLogicalMap[33][27]);
        forestTiles.add(internalLogicalMap[33][32]);
        forestTiles.add(internalLogicalMap[33][33]);
        forestTiles.add(internalLogicalMap[33][35]);
        forestTiles.add(internalLogicalMap[33][40]);
        forestTiles.add(internalLogicalMap[33][41]);
        forestTiles.add(internalLogicalMap[33][42]);
        forestTiles.add(internalLogicalMap[33][48]);
        forestTiles.add(internalLogicalMap[33][49]);

        forestTiles.add(internalLogicalMap[34][5]);
        forestTiles.add(internalLogicalMap[34][6]);
        forestTiles.add(internalLogicalMap[34][7]);
        forestTiles.add(internalLogicalMap[34][9]);
        forestTiles.add(internalLogicalMap[34][13]);
        forestTiles.add(internalLogicalMap[34][15]);
        forestTiles.add(internalLogicalMap[34][16]);
        forestTiles.add(internalLogicalMap[34][25]);
        forestTiles.add(internalLogicalMap[34][29]);
        forestTiles.add(internalLogicalMap[34][37]);
        forestTiles.add(internalLogicalMap[34][38]);
        forestTiles.add(internalLogicalMap[34][39]);
        forestTiles.add(internalLogicalMap[34][40]);
        forestTiles.add(internalLogicalMap[34][44]);
        forestTiles.add(internalLogicalMap[34][45]);
        forestTiles.add(internalLogicalMap[34][46]);
        forestTiles.add(internalLogicalMap[34][47]);
        forestTiles.add(internalLogicalMap[34][48]);

        forestTiles.add(internalLogicalMap[35][0]);
        forestTiles.add(internalLogicalMap[35][1]);
        forestTiles.add(internalLogicalMap[35][2]);
        forestTiles.add(internalLogicalMap[35][4]);
        forestTiles.add(internalLogicalMap[35][5]);
        forestTiles.add(internalLogicalMap[35][6]);
        forestTiles.add(internalLogicalMap[35][21]);
        forestTiles.add(internalLogicalMap[35][28]);
        forestTiles.add(internalLogicalMap[35][30]);
        forestTiles.add(internalLogicalMap[35][34]);
        forestTiles.add(internalLogicalMap[35][41]);
        forestTiles.add(internalLogicalMap[35][44]);
        forestTiles.add(internalLogicalMap[35][45]);
        forestTiles.add(internalLogicalMap[35][46]);
        forestTiles.add(internalLogicalMap[35][47]);
        forestTiles.add(internalLogicalMap[35][49]);

        forestTiles.add(internalLogicalMap[36][0]);
        forestTiles.add(internalLogicalMap[36][2]);
        forestTiles.add(internalLogicalMap[36][3]);
        forestTiles.add(internalLogicalMap[36][10]);
        forestTiles.add(internalLogicalMap[36][11]);
        forestTiles.add(internalLogicalMap[36][14]);
        forestTiles.add(internalLogicalMap[36][17]);
        forestTiles.add(internalLogicalMap[36][20]);
        forestTiles.add(internalLogicalMap[36][22]);
        forestTiles.add(internalLogicalMap[36][26]);
        forestTiles.add(internalLogicalMap[36][31]);
        forestTiles.add(internalLogicalMap[36][36]);
        forestTiles.add(internalLogicalMap[36][40]);
        forestTiles.add(internalLogicalMap[36][41]);
        forestTiles.add(internalLogicalMap[36][44]);
        forestTiles.add(internalLogicalMap[36][46]);
        forestTiles.add(internalLogicalMap[36][47]);
        forestTiles.add(internalLogicalMap[36][48]);
        forestTiles.add(internalLogicalMap[36][49]);

        forestTiles.add(internalLogicalMap[37][0]);
        forestTiles.add(internalLogicalMap[37][3]);
        forestTiles.add(internalLogicalMap[37][7]);
        forestTiles.add(internalLogicalMap[37][12]);
        forestTiles.add(internalLogicalMap[37][17]);
        forestTiles.add(internalLogicalMap[37][19]);
        forestTiles.add(internalLogicalMap[37][22]);
        forestTiles.add(internalLogicalMap[37][27]);
        forestTiles.add(internalLogicalMap[37][48]);

        forestTiles.add(internalLogicalMap[38][0]);
        forestTiles.add(internalLogicalMap[38][1]);
        forestTiles.add(internalLogicalMap[38][4]);
        forestTiles.add(internalLogicalMap[38][8]);
        forestTiles.add(internalLogicalMap[38][11]);
        forestTiles.add(internalLogicalMap[38][13]);
        forestTiles.add(internalLogicalMap[38][15]);
        forestTiles.add(internalLogicalMap[38][18]);
        forestTiles.add(internalLogicalMap[38][19]);
        forestTiles.add(internalLogicalMap[38][22]);
        forestTiles.add(internalLogicalMap[38][30]);
        forestTiles.add(internalLogicalMap[38][34]);
        forestTiles.add(internalLogicalMap[38][37]);
        forestTiles.add(internalLogicalMap[38][38]);
        forestTiles.add(internalLogicalMap[38][39]);
        forestTiles.add(internalLogicalMap[38][40]);
        forestTiles.add(internalLogicalMap[38][43]);
        forestTiles.add(internalLogicalMap[38][44]);
        forestTiles.add(internalLogicalMap[38][46]);
        forestTiles.add(internalLogicalMap[38][47]);

        forestTiles.add(internalLogicalMap[39][2]);
        forestTiles.add(internalLogicalMap[39][3]);
        forestTiles.add(internalLogicalMap[39][5]);
        forestTiles.add(internalLogicalMap[39][14]);
        forestTiles.add(internalLogicalMap[39][18]);
        forestTiles.add(internalLogicalMap[39][22]);
        forestTiles.add(internalLogicalMap[39][25]);
        forestTiles.add(internalLogicalMap[39][28]);
        forestTiles.add(internalLogicalMap[39][31]);
        forestTiles.add(internalLogicalMap[39][36]);
        forestTiles.add(internalLogicalMap[39][38]);
        forestTiles.add(internalLogicalMap[39][41]);
        forestTiles.add(internalLogicalMap[39][42]);
        forestTiles.add(internalLogicalMap[39][44]);
        forestTiles.add(internalLogicalMap[39][45]);
        forestTiles.add(internalLogicalMap[39][49]);

        forestTiles.add(internalLogicalMap[40][0]);
        forestTiles.add(internalLogicalMap[40][2]);
        forestTiles.add(internalLogicalMap[40][4]);
        forestTiles.add(internalLogicalMap[40][7]);
        forestTiles.add(internalLogicalMap[40][13]);
        forestTiles.add(internalLogicalMap[40][16]);
        forestTiles.add(internalLogicalMap[40][20]);
        forestTiles.add(internalLogicalMap[40][22]);
        forestTiles.add(internalLogicalMap[40][27]);
        forestTiles.add(internalLogicalMap[40][28]);
        forestTiles.add(internalLogicalMap[40][32]);
        forestTiles.add(internalLogicalMap[40][37]);
        forestTiles.add(internalLogicalMap[40][38]);
        forestTiles.add(internalLogicalMap[40][42]);
        forestTiles.add(internalLogicalMap[40][48]);
        forestTiles.add(internalLogicalMap[40][49]);

        forestTiles.add(internalLogicalMap[41][0]);
        forestTiles.add(internalLogicalMap[41][1]);
        forestTiles.add(internalLogicalMap[41][2]);
        forestTiles.add(internalLogicalMap[41][3]);
        forestTiles.add(internalLogicalMap[41][10]);
        forestTiles.add(internalLogicalMap[41][19]);
        forestTiles.add(internalLogicalMap[41][21]);
        forestTiles.add(internalLogicalMap[41][29]);
        forestTiles.add(internalLogicalMap[41][34]);
        forestTiles.add(internalLogicalMap[41][36]);
        forestTiles.add(internalLogicalMap[41][37]);
        forestTiles.add(internalLogicalMap[41][38]);
        forestTiles.add(internalLogicalMap[41][41]);
        forestTiles.add(internalLogicalMap[41][43]);
        forestTiles.add(internalLogicalMap[41][44]);
        forestTiles.add(internalLogicalMap[41][48]);

        forestTiles.add(internalLogicalMap[42][4]);
        forestTiles.add(internalLogicalMap[42][5]);
        forestTiles.add(internalLogicalMap[42][8]);
        forestTiles.add(internalLogicalMap[42][12]);
        forestTiles.add(internalLogicalMap[42][14]);
        forestTiles.add(internalLogicalMap[42][22]);
        forestTiles.add(internalLogicalMap[42][23]);
        forestTiles.add(internalLogicalMap[42][27]);
        forestTiles.add(internalLogicalMap[42][30]);
        forestTiles.add(internalLogicalMap[42][36]);
        forestTiles.add(internalLogicalMap[42][38]);
        forestTiles.add(internalLogicalMap[42][40]);
        forestTiles.add(internalLogicalMap[42][43]);
        forestTiles.add(internalLogicalMap[42][44]);
        forestTiles.add(internalLogicalMap[42][46]);
        forestTiles.add(internalLogicalMap[42][48]);
        forestTiles.add(internalLogicalMap[42][49]);

        // ROAD TILES
        // OBJECTIVE TILES

        setLogicalTilesToType(impassibleTiles, LogicalTileType.IMPASSIBLE_WALL);
        setLogicalTilesToType(lowWallTiles, LogicalTileType.LOW_WALL);
        setLogicalTilesToType(forestTiles, LogicalTileType.FOREST);

    }
}
