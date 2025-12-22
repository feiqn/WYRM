package com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.OLD_HUDElement;
import com.feiqn.wyrm.OLD_DATA.models.battleconditionsdata.victoryconditions.VictoryCondition;

public class VictConInfoPanel extends OLD_HUDElement {

    // One panel per stage,
    // Persistent in top corner,
    // Clickable for details about each objective
    // has text and image

    public VictConInfoPanel(WYRMGame game) {
        super(game);

    }

    public void update() {
        layout.addAction(Actions.sequence(Actions.fadeOut(1), Actions.fadeIn(1)));
        layout.clearChildren(true);
        for(VictoryCondition vc : game.activeOLDGridScreen.conditions().getVictoryConditions()) {
            if(!vc.isHidden()) {
                final Label l = new Label(vc.getObjectiveText(), WYRMGame.assets().menuLabelStyle);
                l.getStyle().font.getData().markupEnabled = true;
                layout.add(l).left().top();
                layout.row();
            }
        }
    }

}
