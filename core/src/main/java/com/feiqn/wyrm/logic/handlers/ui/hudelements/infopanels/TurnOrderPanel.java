package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;

public class TurnOrderPanel extends HUDElement {

    private Array<Image> unitPanels;

    public TurnOrderPanel(WYRMGame game) {
        super(game);


    }

    public void addToTurnOrder(Unit unit) {
        // get thumbnail, add to array, layout
    }

}
