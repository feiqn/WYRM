package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public abstract class WyrHUD extends Table implements Wyr {

    protected boolean isBusy = false;

    protected WyrHUD() {
        this.setFillParent(true);
        this.top();
    }

    protected abstract void buildStandard();

    public boolean isBusy() { return isBusy; }
}
