package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
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

//        boolean needsASpacer = false;

        for(int i = 1; i < 40; i++) {

            final Array<Unit> segregatedPlayer = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.PLAYER);
            final Array<Unit> segregatedEnemy = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.ENEMY);
            final Array<Unit> segregatedAlly = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.ALLY);
            final Array<Unit> segregatedOther = game.activeGridScreen.conditionsHandler.segregatedTickOrder(i).get(TeamAlignment.OTHER);

            populateFromSegregated(segregatedPlayer);
            populateFromSegregated(segregatedEnemy);
            populateFromSegregated(segregatedAlly);
            populateFromSegregated(segregatedOther);

//            for(Unit u : h.get(i)) {
//
//                if(!needsASpacer) needsASpacer = true;
//                final Stack s = new Stack();
//                s.add(new Image(game.assetHandler.solidBlueTexture));
//                s.add(new Image(u.getDrawable()));
//
//                switch(u.getTeamAlignment()) {
//                    case ENEMY:
//                        s.getChild(0).setColor(Color.RED);
//                        s.getChild(1).setColor(Color.RED);
//                        break;
//                    case ALLY:
//                        s.getChild(0).setColor(Color.GREEN);
//                        s.getChild(1).setColor(Color.GREEN);
//                        break;
//                    case OTHER:
//                        s.setColor(Color.GRAY);
//                }
//
//                layout.add(s).padRight(2).uniform();
////                panels.add(s);
//            }

//            if(needsASpacer && i < 39) {
//                needsASpacer = false;
//                layout.add().uniform();
//            }

        }
    }

    private void populateFromSegregated(Array<Unit> seg) {
        for(Unit u : seg) {

//            if(!needsASpacer) needsASpacer = true;
            final Stack s = new Stack();
            s.add(new Image(game.assetHandler.solidBlueTexture));
            s.add(new Image(u.getDrawable()));

            switch(u.getTeamAlignment()) {
                case ENEMY:
                    s.getChild(0).setColor(Color.RED);
                    s.getChild(1).setColor(Color.RED);
                    break;
                case ALLY:
                    s.getChild(0).setColor(Color.GREEN);
                    s.getChild(1).setColor(Color.GREEN);
                    break;
                case OTHER:
                    s.setColor(Color.GRAY);
            }

            layout.add(s).padRight(2).uniform();
//                panels.add(s);
        }
    }

    private void highlightTickUnits(int currentTick) {

    }

    private static class panel extends Stack {

    }

}
