package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;

public class VictConInfoPanel extends HUDElement {

    // One panel for each objective per stage,
    // Persistent in top corner,
    // Clickable for details about objective
    // has text and image

    private final VictConInfoPanel self = this;
    protected Image hoverHider;

    protected int victConIndex;

    public VictConInfoPanel(WYRMGame game) {
        super(game);

        victConIndex   = 42069;
        hoverHider     = new Image(game.assetHandler.yellowButtonTexture);

        hoverHider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
               expand();
            }
        });

        // TODO: need a place to click for contract

        // TODO: hoverHider needs icon sprite, and should then be added to the abs.uiGroup, rather than as a child of self

    }

    protected void displayMoreInfo() {
        final Image moreInfoBackground = new Image(game.assetHandler.blueButtonTexture);

        moreInfoBackground.setSize(Gdx.graphics.getWidth() * .8f, Gdx.graphics.getHeight() * .8f);

        // TODO

    }

    public void expand() {
        hoverHider.remove();
        ags.hudStage.addActor(self);
        // TODO: click/hover smaller icon to display full panel/full text
    }

    public void contract() {
        self.remove();
        ags.hudStage.addActor(hoverHider);

        // TODO: make smaller on hover exit
    }

    public void update() {
        layout.addAction(Actions.sequence(Actions.fadeOut(1), Actions.fadeIn(1)));
        layout.clearChildren(true);
        for(VictoryCondition vc : game.activeGridScreen.conditionsHandler.getVictoryConditions()) {
            final Label l = new Label(vc.getObjectiveText(), game.assetHandler.menuLabelStyle);
            l.getStyle().font.getData().markupEnabled = true;
            layout.add(l).left().top();
            layout.row();
        }
    }

    // --SETTERS--
    public void setIndex(int index) {
        victConIndex = index;
    }

    // --GET--
    public int getIndex() {
        return victConIndex;
    }
}
