package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.gamescreens.GridScreen_1A;

public class MainMenuScreen extends ScreenAdapter {

    private final WYRMGame game;

    private Stage stage;

    public BitmapFont mainMenuFont;

    public MainMenuScreen(WYRMGame game) { this.game = game; }

    @Override
    public void show() {
        Label titleLabel, newGameLabel, cutsceneTestLabel;

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        mainMenuFont = new BitmapFont();
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/COPPERPLATE.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter mainMenuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        mainMenuFontParameter.size = 60;
        mainMenuFontParameter.color = Color.GOLD;
        mainMenuFontParameter.borderWidth = 2;
        mainMenuFontParameter.borderColor = Color.GOLDENROD;
        mainMenuFont = fontGenerator.generateFont(mainMenuFontParameter);
        fontGenerator.dispose();

        Label.LabelStyle mainMenuLabelStyle = new Label.LabelStyle();
        mainMenuLabelStyle.font = mainMenuFont;

        titleLabel = new Label("WYRM?", mainMenuLabelStyle);
        newGameLabel = new Label("New Game", mainMenuLabelStyle);
        cutsceneTestLabel = new Label("Cutscene Test", mainMenuLabelStyle);

        cutsceneTestLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                Gdx.input.setInputProcessor(null);
                stage.addAction(Actions.sequence(
                    Actions.fadeOut(2),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            GridScreen screen = new DialogueScreen(game);
                            game.activeScreen = screen;
                            game.activeGridScreen = screen;
                            game.transitionScreen(screen);
                        }
                    })
                ));
            }
        });

        newGameLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                Gdx.input.setInputProcessor(null);
                stage.addAction(Actions.sequence(
                    Actions.fadeOut(2),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            GridScreen screen = new GridScreen_1A(game);
                            game.activeScreen = screen;
                            game.activeGridScreen = screen;
                            game.transitionScreen(screen);
                        }
                    })
                ));
            }
        });

//        titleLabel.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                DialogueScreen screen = new DialogueScreen(game);
//                game.setScreen(screen);
//            }
//        });

//        stage.addActor(titleLabel);
//        stage.addActor(newGameLabel);

        final Table menu = new Table();
        menu.setFillParent(true);
//        menu.setDebug(true);
        menu.add(titleLabel);
        menu.row();
        menu.add(newGameLabel);
        menu.row();
        menu.add(cutsceneTestLabel);

        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        if(game.assetHandler.getManager().update()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0,0,0,1);

            stage.act();
            stage.draw();
        } else {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(0,1,1,1);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, true);
        stage.getCamera().update();
    }

}
