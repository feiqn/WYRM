package com.feiqn.wyrm.wyrefactor.wyrscreen;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrScreen<Handler extends MetaHandler<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> extends ScreenAdapter implements Wyr {

    protected Handler h;

    public WyrScreen() {}

    public abstract Handler handlers();

}
