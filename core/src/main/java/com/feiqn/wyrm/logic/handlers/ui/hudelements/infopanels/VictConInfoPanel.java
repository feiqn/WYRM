package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;

public class VictConInfoPanel extends HUDElement {

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
        for(VictoryCondition vc : game.activeGridScreen.conditions().getVictoryConditions()) {
            if(!vc.isHidden()) {
                final Label l = new Label(vc.getObjectiveText(), game.assetHandler.menuLabelStyle);
                l.getStyle().font.getData().markupEnabled = true;
                layout.add(l).left().top();
                layout.row();
            }
        }
    }

}
