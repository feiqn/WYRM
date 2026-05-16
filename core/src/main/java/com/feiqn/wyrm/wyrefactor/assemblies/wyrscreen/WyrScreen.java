package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrScreen extends ScreenAdapter implements Wyr {

    protected Stage gameStage;
    protected Stage hudStage;

    private final MetaHandler h;

    protected WyrScreen() {
        this(null);
    }

    public WyrScreen(MetaHandler metaHandler) {
        this.h = metaHandler;

    }

    @Override
    public void show() {
        super.show();


    }

    public Stage getGameStage() { return gameStage; }
    public Stage getHudStage() { return hudStage; }

    public MetaHandler h() {
        return h;
    }
}
