package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.graphics.Color;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class GridScreen_DEBUGROOM extends GridScreen {
    public GridScreen_DEBUGROOM(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        logicalMap = new WyrMap(game, 20) {

            @Override public void setUpUnits() {
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 5, 5);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

                final SoldierUnit testEnemy = new SoldierUnit(game);
                testEnemy.setColor(Color.RED);
                testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy.setAIType(AIType.AGGRESSIVE);
                testEnemy.name = "Evil Timn";
                placeUnitAtPositionXY(testEnemy, 10, 10);
                conditionsHandler.addToTurnOrder(testEnemy);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
                rootGroup.addActor(testEnemy);
                testEnemy.setCannotMove();
            }

        };


    }

}
