package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrHUD extends Table {

    protected final Wyr_DEPRECATED wyr;

    protected WyrHUD(WyrType wyrType) {
        wyr = new Wyr_DEPRECATED(wyrType);

        this.setFillParent(true);
        this.top();
    }

    protected abstract void buildLayout();

    public WyrType wyrType() { return wyr.type(); }

}
