package com.feiqn.wyrm.wyr2;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.WYRMGame;

public class WyrScreen extends ScreenAdapter {

    public enum Type {
        MENU,
        GRID
    }

    protected final Type type;

    protected final MetaHandler metaHandler;

    protected final WYRMGame game;

    // ---END VARIABLES---

    public WyrScreen(WYRMGame game, Type type) {
        this.game = game;
        this.type = type;
        this.metaHandler = new MetaHandler();
    }

}
