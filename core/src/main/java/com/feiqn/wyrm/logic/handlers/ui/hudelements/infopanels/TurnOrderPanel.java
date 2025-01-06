package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class TurnOrderPanel extends HUDElement {

    private Array<Unit> roster;

    private int rotationLength;

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        roster = new Array<>();

        background.setDrawable(new TextureRegionDrawable(game.assetHandler.purpleButtonTexture));
    }

    private void layoutPanels() {
        // for turnlength,
    }

    public void clearTurnOrder() {
        roster = new Array<>();
        rotationLength = 1;
    }

    public void addToTurnOrder(Unit unit) {
        if(!roster.contains(unit, true)) {
            roster.add(unit);
            rotationLength++;
        }
    }

    private void calculateTurnOrder() {
        // TODO: let unit move multiple times per rotation

        /* ROTATION LOGIC:
         * ---------------
         * All units' simpleSpeed() will be a value 1..10.
         * take abs value of speed - 10
         * i.e., a speed of 8 will = 2
         * every x battle ticks, the unit may move
         * units with same speed may move within battle tick in whatever order they want,
         * with default priority to player -> ally -> enemy -> other
         */

    }

}
