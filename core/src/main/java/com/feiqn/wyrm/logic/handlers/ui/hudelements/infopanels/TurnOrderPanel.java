package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class TurnOrderPanel extends HUDElement {

    private Array<Panel> panels;

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        this.clear();
        this.add(layout);
        layout.clear();
        layout.center();

        panels = new Array<>();
    }

    public void layoutPanels() {
        layout.clearChildren(true);
        panels.clear();

        int tick = 1;

        for(SimpleUnit unit : ags.conditionsHandler.unifiedTurnOrder()) {
            if(tick > unit.modifiedSimpleSpeed()) {
                tick = unit.modifiedSimpleSpeed();
                layout.add().uniform();
            }
            final Panel panel = new Panel(unit);
            switch(unit.getTeamAlignment()) {
                case ENEMY:
                    panel.getChild(1).setColor(Color.RED);
                    break;
                case ALLY:
                    panel.getChild(1).setColor(Color.GREEN);
                    break;
                case OTHER:
                    panel.getChild(1).setColor(Color.GRAY);
            }

            layout.add(panel).padRight(2).uniform();
            panels.add(panel);
        }
    }

    private void highlightTickUnits(int currentTick) {

    }

    public void updateDim() {
        for(Panel panel : panels) {
            panel.update();
        }
    }

    private static class Panel extends Stack {

        private final SimpleUnit unit;

        public Panel(SimpleUnit unit) {
            super();
            this.unit = unit;
            this.add(new Image(game.assetHandler.solidBlueTexture));
            this.add(new Image(unit.getDrawable()));
            update();

            this.addListener(new ClickListener() {

                // TODO: on click, center camera on unit

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    game.activeGridScreen.hud().updateHoveredUnitInfoPanel(unit);
                }
            });
        }

        public void update() {
            if(unit.canMove()) {
                this.getChild(0).setColor(1,1,1,1);
            } else {
                this.getChild(0).setColor(.2f,.2f,.2f, 1);
            }
        }

    }

}
