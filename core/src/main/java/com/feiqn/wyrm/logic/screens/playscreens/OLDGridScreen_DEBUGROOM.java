package com.feiqn.wyrm.logic.screens.playscreens;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnitOLD;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_DEBUGROOM extends OLD_GridScreen {

    public OLDGridScreen_DEBUGROOM(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/debugRoom.tmx");

        final MapProperties properties = tiledMap.getProperties();

        logicalMap = new AutoFillOLDWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {

            @Override
            public void setUpUnits() {
                final LeifUnitOLD testChar = new LeifUnitOLD(game);
                placeUnitAtPositionXY(testChar, 5, 5);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
//                testChar.dismount();

                // add a soldier for anim testing

//                final BallistaObject ballista = new BallistaObject(game);
//                placeMapObjectAtPosition(ballista, 7, 7);
//                ballistaObjects.add(ballista);
//                rootGroup.addActor(ballista);

////
                final SoldierUnitOLD testChar2 = new SoldierUnitOLD(game);
                placeUnitAtPositionXY(testChar2, 3, 4);
                conditionsHandler.addToTurnOrder(testChar2);
                testChar2.setTeamAlignment(TeamAlignment.PLAYER);
                conditionsHandler.teams().getPlayerTeam().add(testChar2);
                rootGroup.addActor(testChar2);
                testChar2.setCannotMove();

//                final AntalUnit antalChar = new AntalUnit(game);
//                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
//                placeUnitAtPositionXY(antalChar, 7, 7);
//                conditionsHandler.addToTurnOrder(antalChar);
//                conditionsHandler.teams().getPlayerTeam().add(antalChar);
//                rootGroup.addActor(antalChar);
//                antalChar.setCannotMove();
//
//                final SoldierUnit testEnemy = new SoldierUnit(game);
//                testEnemy.setColor(Color.RED);
//                testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
//                testEnemy.setAIType(AIType.AGGRESSIVE);
//                testEnemy.name = "Evil Timn";
//                placeUnitAtPositionXY(testEnemy, 10, 10);
//                conditionsHandler.addToTurnOrder(testEnemy);
//                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
//                rootGroup.addActor(testEnemy);
//                testEnemy.setCannotMove();
            }

        };

    }

    @Override
    public void show() {
        super.show();
        endCutscene();
    }




}
