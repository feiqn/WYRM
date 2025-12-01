package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.WYRMGame;

public abstract class WyrScreen extends ScreenAdapter {

    public enum Type {
        MENU,
        GRID,
        OVERWORLD,
        NARRATIVE
    }

    protected final Type type;

    protected final WYRMGame game;

    // ---END VARIABLES---

    public WyrScreen(WYRMGame game, Type type) {
        this.game = game;
        this.type = type;
    }

    public Type getType() { return type; }

}
