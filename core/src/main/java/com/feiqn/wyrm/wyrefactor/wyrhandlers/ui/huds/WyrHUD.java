package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

        this.setFillParent(true);

        addActor(background);
        addActor(layout);
    }

    protected abstract void build();

    protected void setBackground(Drawable drawable) { background.setDrawable(drawable); }

    public WyrType wyrType() { return wyr.type(); }

}
