package com.feiqn.wyrm.logic.screens.playscreens.stage2;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;

public class GridScreen_2A extends GridScreen {

    public GridScreen_2A(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/walled_city_lower_entrance.tmx");
        final MapProperties properties = tiledMap.getProperties();

        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {

            @Override
            public void setUpUnits() {
                // TODO: add leif, antal, and some patrolling guards
            }

        };

        // setupTiles override here for special tiles
    }

    // buildConversations override

    // setUpVictFailCons override

    // stageClear override


}
