package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrHUD extends Table implements Wyr {

    protected boolean isBusy = false;

    protected WyrHUD() {
        this.setFillParent(true);
        this.top();
    }

    protected abstract void buildLayout();

    public boolean isBusy() { return isBusy; }
}
