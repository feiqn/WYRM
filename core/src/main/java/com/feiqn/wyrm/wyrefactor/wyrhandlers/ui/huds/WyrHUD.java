package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrHUD extends Stack {

    protected final Wyr wyr;

    protected final Image background;

    protected final Table layout;

    protected WyrHUD(WyrType wyrType) {
        wyr = new Wyr(wyrType);
        background = new Image();
        layout = new Table();

        // Children should automatically fill to size due to Stack behavior, I think.
        this.setFillParent(true);

        this.setDebug(true);
        layout.setFillParent(true);

        buildMain();
    }

    protected void buildMain() {
        this.clearChildren();
        addActor(background);
        addActor(layout);
    }

    protected abstract void buildLayout();



//    protected void setBackground(Drawable drawable) { background.setDrawable(drawable); }

    public WyrType wyrType() { return wyr.type(); }

}
