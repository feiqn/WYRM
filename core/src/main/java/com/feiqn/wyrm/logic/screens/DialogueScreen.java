package com.feiqn.wyrm.logic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.WYRMGame;

public class DialogueScreen extends ScreenAdapter {

    private final WYRMGame game;

    private final Stage stage = new Stage();

    public DialogueScreen(WYRMGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f, 1, 0.5f, 1);

        stage.act();
        stage.draw();
    }

}
