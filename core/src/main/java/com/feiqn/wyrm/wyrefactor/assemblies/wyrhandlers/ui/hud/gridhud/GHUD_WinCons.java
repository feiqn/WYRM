package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.gridhud;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCondition;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.handlers;

public class GHUD_WinCons extends Table {

    public GHUD_WinCons(Skin skin) {
        super(skin);
    }

    public void refresh() {

        // todo: sort order w / o / l

        for(WyrWinCondition c : handlers.register().revealedVictoryConditions()) {

            final Label panelLabel = new Label(c.getShortDescription(), this.getSkin());

            add(panelLabel).fill().pad(5).left();
            row();

        }

    }
}

