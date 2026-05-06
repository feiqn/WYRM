package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public class GHUD_WinCons extends VerticalGroup {

    private final Skin skin;

    private final RPGridMetaHandler h;

    public GHUD_WinCons(RPGridMetaHandler metaHandler, Skin skin) {
        super();
        this.skin = skin;
        h = metaHandler;

    }

    public void refresh() {

    }
}
