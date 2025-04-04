package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

public class AutoFillGridScreen extends GridScreen {

    // Template / example

    public AutoFillGridScreen(WYRMGame game) {
        super(game, StageList.STAGE_1A);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/1A_v0.tmx");

        final MapProperties properties = tiledMap.getProperties();

        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                // TODO: load this via tiled object layer
                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPosition(testChar, 15, 3);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
            }
        };
    }

}
