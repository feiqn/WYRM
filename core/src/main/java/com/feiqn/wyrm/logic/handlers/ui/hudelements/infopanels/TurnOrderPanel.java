package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class TurnOrderPanel extends HUDElement {

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        layout.clear();

//        backgroundImage.setDrawable(new TextureRegionDrawable(game.assetHandler.purpleButtonTexture));
    }

    private void layoutPanels() {
        layout.clearChildren(true);
        final HashMap<Integer, Array<Unit>> h = game.activeGridScreen.conditionsHandler.getTurnOrder();

        for(int i = 1; i < 40; i++) {

            for(Unit u : h.get(i)) {
                final Stack s = new Stack();
                s.add(new Image(game.assetHandler.solidBlueTexture));
                s.add(new Image(u.getDrawable()));
                layout.add(s).pad(2);
            }
        }
    }

    private void highlightTickUnits(int currentTick) {

    }



}
