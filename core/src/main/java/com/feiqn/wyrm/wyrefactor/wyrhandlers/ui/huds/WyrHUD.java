package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;

public abstract class WyrHUD extends Stack {

    protected final WYRMGame root;

    protected final Image background;

    protected final Table layout;

    protected WyrHUD(WYRMGame root) {
        this.root = root;
        background = new Image();
        layout = new Table();

        this.setFillParent(true);

        addActor(background);
        addActor(layout);
    }

    protected abstract void build();



    protected void setBackground(Drawable drawable) { background.setDrawable(drawable); }


}
