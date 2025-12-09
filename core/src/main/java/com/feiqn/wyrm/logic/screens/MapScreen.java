package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.storyA.stage1.OLDGridScreen_1A;

public class MapScreen extends ScreenAdapter {

    private final WYRMGame game;

    private Stage stage;

    public MapScreen(WYRMGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        populateMap();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 1, 1);

        stage.act();
        stage.draw();
    }

    public void populateMap() {
        final Label firstLabel = new Label("1A", game.assetHandler.menuLabelStyle);
        firstLabel.setPosition(Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() - firstLabel.getHeight());
        stage.addActor(firstLabel);

        firstLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                OLD_GridScreen screen = new OLDGridScreen_1A(game);
                game.activeScreenAdapter = screen;
                game.activeOLDGridScreen = screen;
                game.setScreen(screen);
            }
        });

        for(StageList stageID : game.campaignHandler.unlockedStages()) {
            switch(stageID) {
                case STAGE_1A:
                    break;
                case STAGE_2A:
                    final Label secondLabel = new Label("2A", game.assetHandler.menuLabelStyle);
                    secondLabel.setPosition(firstLabel.getX(), firstLabel.getY() - secondLabel.getHeight() * 1.5f);
                    stage.addActor(secondLabel);

                    // TODO: click listener once 2A exists
            }
        }

    }

}
