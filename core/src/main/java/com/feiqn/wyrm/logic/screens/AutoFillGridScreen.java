package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.mapdata.StageList;

public class AutoFillGridScreen extends GridScreen {

    // Template / example

    public AutoFillGridScreen(WYRMGame game) {
        super(game, StageList.STAGE_1A);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/1A_v0.tmx");
        logicalMap = new AutoFillWyrMap(game, tiledMap) {
            @Override
            public void setUpUnits() {
                // TODO: load this via tiled object layer
            }
        };
    }

}
