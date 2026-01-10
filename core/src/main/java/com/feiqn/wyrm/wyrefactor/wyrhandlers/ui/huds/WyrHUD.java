package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrHUD extends Table {

    protected final Wyr wyr;

    protected WyrHUD(WyrType wyrType) {
        wyr = new Wyr(wyrType);

        this.setDebug(true);

        this.setFillParent(true);
        this.top();
    }

    protected abstract void buildLayout();

    public WyrType wyrType() { return wyr.type(); }

}
