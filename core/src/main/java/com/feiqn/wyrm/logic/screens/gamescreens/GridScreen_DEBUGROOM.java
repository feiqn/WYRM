package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class GridScreen_DEBUGROOM extends GridScreen {

    boolean setup = false;

    public GridScreen_DEBUGROOM(WYRMGame game) {
        super(game);
    }

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setInputMode(InputMode.STANDARD);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/0A_v0.tmx");

        final MapProperties properties = tiledMap.getProperties();

        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {

            @Override
            public void setUpUnits() {
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 5, 5);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
//                testChar.setCannotMove();
                testChar.setCanMove();
                testChar.dismount();

                final AntalUnit antalChar = new AntalUnit(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 15, 15);
                conditionsHandler.addToTurnOrder(antalChar);
                conditionsHandler.teams().getPlayerTeam().add(antalChar);
                rootGroup.addActor(antalChar);
//                antalChar.setCannotMove();

                final SoldierUnit testEnemy = new SoldierUnit(game);
                testEnemy.setColor(Color.RED);
                testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy.setAIType(AIType.AGGRESSIVE);
                testEnemy.name = "Evil Timn";
                placeUnitAtPositionXY(testEnemy, 10, 10);
                conditionsHandler.addToTurnOrder(testEnemy);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
                rootGroup.addActor(testEnemy);
//                testEnemy.setCannotMove();
            }

        };


    }




}
