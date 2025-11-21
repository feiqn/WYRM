package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.models.unitdata.TeamAlignment.ALLY;

public class TurnOrderPanel extends HUDElement {

    private Array<Panel> panels;

    public TurnOrderPanel(WYRMGame game) {
        super(game);

        this.clear(); // Don't want a solid background for this compound element.
        this.add(layout);
        layout.clear(); // It's already empty and filling the parent, but why not.
        layout.center();

        panels = new Array<>();
    }

    public void layoutPanels() {
        layout.clearChildren(true);
        panels.clear();

        int tick = 1;

        for(SimpleUnit unit : ags.conditions().unifiedTurnOrder()) {
            if(tick > unit.modifiedSimpleSpeed()) {
                tick = unit.modifiedSimpleSpeed();
                layout.add().uniform();
            }
            final Panel panel = new Panel(unit);
            switch(unit.getTeamAlignment()) {
                case ENEMY:
                    panel.getChild(1).setColor(Color.RED); // TODO: shaders instead
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
        // probably just fold this into update()
    }

    public void update() { // TODO: call on checkLineOrder (?)
        boolean shouldRebuild = false;

        if(ags.conditions().unifiedTurnOrder().size > panels.size) {
            layoutPanels();
        }

        for(Panel panel : panels) {
            if(!shouldRebuild) {
                shouldRebuild = panel.update();
            } else {
                panel.update();
            }
        }
        if(shouldRebuild) {
            layoutPanels();
            update();
        }

    }

    private static class Panel extends Stack {

        private final SimpleUnit unit;
        private final Image background;
        private boolean hovered;

        public Panel(SimpleUnit unit) {
            super();
            this.unit = unit;

            this.background = (new Image(game.assetHandler.solidBlueTexture) {
                @Override
                public void draw(Batch batch, float parentAlpha) {
                    if(hovered) {
//                         TODO: apply shader
                    }
                    // shader for:
                    // - activeUnit (moving, attacking, selected) (team color)
                    // - hovered but not selected
                    super.draw(batch, parentAlpha);
                    if(hovered) {
//                         remove shader
                    }
                }
            });

            this.add(background);
            this.add(new Image(unit.getDrawable())); // TODO: pull from idle anim frame 0

            this.addListener(new ClickListener() {

                private boolean clicked = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    clicked = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(clicked) {
                        clicked = false;
                        hovered = false;
                        game.activeGridScreen.centerCameraOnLocation(unit.getColumnX(), unit.getRowY());
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    game.activeGridScreen.hud().updateHoveredUnitInfoPanel(unit);
                    hovered = true;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    hovered = false;
                }
            });

            update();
        }

        public boolean update() {
            boolean shouldUpdateParent = false;

            switch(unit.getTeamAlignment()) { // TODO: better wrapper methods in WyRefactor
                case PLAYER:
                    if(!game.activeGridScreen.conditions().teams().getPlayerTeam().contains(unit, true)) {
                        shouldUpdateParent = true;
                    }
                    break;

                case ALLY:
                    if(!game.activeGridScreen.conditions().teams().getAllyTeam().contains(unit, true)) {
                        shouldUpdateParent = true;
                    }
                    break;

                case ENEMY:
                    if(!game.activeGridScreen.conditions().teams().getEnemyTeam().contains(unit, true)) {
                        shouldUpdateParent = true;
                    }
                    break;

                case OTHER:
                    if(!game.activeGridScreen.conditions().teams().getOtherTeam().contains(unit, true)) {
                        shouldUpdateParent = true;
                    }
                    break;

                default:
                    break;
            }

            if(unit.canMove()) {
                this.getChild(0).setColor(1,1,1,1);
                this.getChild(1).setColor(1,1,1,1);
            } else {
                this.getChild(0).setColor(1,1,1, .25f);
                this.getChild(1).setColor(1,1,1, .25f);
            }

            return shouldUpdateParent;
        }

    }

}
