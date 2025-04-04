package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.mapdata.WyrMap;

public class DialogueScreen extends GridScreen {

    private final WYRMGame game;

    private final Stage stage = new Stage();

    public DialogueScreen(WYRMGame game, StageList cutsceneID) {
        super(game, cutsceneID);
        this.game = game;
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/roadside_campfire.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap);
    }

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setInputMode(InputMode.CUTSCENE);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

}
