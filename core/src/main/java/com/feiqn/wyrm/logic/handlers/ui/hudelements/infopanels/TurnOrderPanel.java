package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class TurnOrderPanel extends HUDElement {

    private Array<panel> panels;

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        this.clear();
        this.add(layout);
        layout.clear();
        layout.center();

        panels = new Array<>();

//        backgroundImage.setDrawable(new TextureRegionDrawable(game.assetHandler.purpleButtonTexture));
    }

    public void layoutPanels() {
        layout.clearChildren(true);
        panels.clear();
        final HashMap<Integer, Array<Unit>> h = game.activeGridScreen.conditionsHandler.getTurnOrder();

        boolean needsASpacer = false;

        for(int i = 1; i < 40; i++) {
            for(Unit u : h.get(i)) {
                if(!needsASpacer) needsASpacer = true;
                final Stack s = new Stack();
                s.add(new Image(game.assetHandler.solidBlueTexture));
                s.add(new Image(u.getDrawable()));
                layout.add(s).padRight(2).uniform();
//                panels.add(s);
            }
            if(needsASpacer && i < 39) {
                needsASpacer = false;
                layout.add().uniform();
            }
        }
    }

    private void highlightTickUnits(int currentTick) {

    }

    private static class panel extends Stack {

    }

}
