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
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.SimpleUnit;

import java.util.HashMap;

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
//        final HashMap<Integer, Array<SimpleUnit>> h = game.activeGridScreen.conditionsHandler.getTurnOrder();

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

//        for(int i = 1; i < 40; i++) {
//
//            final Array<SimpleUnit> segregatedPlayer = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.PLAYER);
//            needsASpacer = populateFromSegregated(segregatedPlayer);
//            if(needsASpacer) layout.add().width(2);
//
//            final Array<SimpleUnit> segregatedEnemy = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.ENEMY);
//            needsASpacer = populateFromSegregated(segregatedEnemy);
//            if(needsASpacer) layout.add().width(2);
//
//            final Array<SimpleUnit> segregatedAlly = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.ALLY);
//            needsASpacer = populateFromSegregated(segregatedAlly);
//            if(needsASpacer) layout.add().width(2);
//
//            final Array<SimpleUnit> segregatedOther = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.OTHER);
//            populateFromSegregated(segregatedOther);
//
//        }
    }

//    private boolean populateFromSegregated(Array<SimpleUnit> seg) {
//        boolean needsASpacer = false;
//        for(SimpleUnit u : seg) {
//
//            final Panel panel = new Panel(u);
//            switch(u.getTeamAlignment()) {
//                case ENEMY:
//                    panel.getChild(1).setColor(Color.RED);
//                    break;
//                case ALLY:
//                    panel.getChild(1).setColor(Color.GREEN);
//                    break;
//                case OTHER:
//                    panel.getChild(1).setColor(Color.GRAY);
//            }
//
//            layout.add(panel).padRight(2).uniform();
//            panels.add(panel);
//            if(!needsASpacer) needsASpacer = true;
//
//        }
//        updateDim();
//        return needsASpacer;
//    }

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
