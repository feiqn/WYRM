package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class TurnOrderPanel extends HUDElement {



    private int rotationLength;

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        background.setDrawable(new TextureRegionDrawable(game.assetHandler.purpleButtonTexture));
    }

    private void layoutPanels() {
        // for battle ticks 1..10
        layout.clearChildren(true);
        final HashMap<Integer, Array<Unit>> h = game.activeGridScreen.conditionsHandler.getTurnOrder();


    }



}
