package com.feiqn.wyrm.logic.handlers.ui.hudelements;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.feiqn.wyrm.WYRMGame;

public class UnitInfoPanel extends Group {

    // This panel pops up over hovered unit

    final WYRMGame game;

    /*
    Label nameLabel
    Label hpLabel
    Image hpBar
    Image unitFace
    Image background
     */

    public UnitInfoPanel(WYRMGame game) {
        this.game = game;

    }
}
