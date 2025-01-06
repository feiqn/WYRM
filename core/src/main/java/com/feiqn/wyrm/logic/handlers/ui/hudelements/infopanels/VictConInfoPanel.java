package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;

public class VictConInfoPanel extends HUDElement {

    // One panel for each objective per stage,
    // Persistent in top corner,
    // Clickable for details about objective
    // has text and image

    private final VictConInfoPanel self = this;

    protected Label objectiveLabel;
    protected Label moreInfoLabel;

    protected Image objectiveImage;
    protected Image background;
    protected Image hoverHider;

    protected int victConIndex;

    protected ClickListener listener;

    protected boolean cleared;
    private boolean initialized;

    public VictConInfoPanel(WYRMGame game) {
        super(game);

        objectiveImage = new Image();
        victConIndex   = 42069;
        listener       = new ClickListener(); // TODO
        cleared        = false;
        objectiveLabel = new Label("Objective Unknown", game.assetHandler.menuLabelStyle);
        moreInfoLabel  = new Label("More info", game.assetHandler.menuLabelStyle);
        background     = new Image(game.assetHandler.blueButtonTexture);
        initialized    = false;

        hoverHider     = new Image(game.assetHandler.yellowButtonTexture);
        hoverHider.setSize(background.getHeight() * .35f,background.getHeight() * .35f);

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

        final float width = Gdx.graphics.getWidth() * .85f;

        background.setSize(width, width * .1f);

        addActor(background);
        addActor(objectiveLabel);

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

    // --SETTERS--
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x,y);
        hoverHider.setPosition(x,y);
    }
    public void setObjectiveLabelText(CharSequence newText) {
        objectiveLabel.setText(newText);
        background.setSize(objectiveLabel.getWidth() * 3f,objectiveLabel.getHeight() * 1.35f);
        objectiveLabel.setPosition(background.getX()  + background.getWidth() * .025f, background.getY() + background.getHeight() * .035f);

        initialized = true;
    }
    public void setMoreInfoLabelText(CharSequence newText) {
        moreInfoLabel.setText(newText);
        initialized = true;
    }
    public void setImage(Image img) {
        objectiveImage = img;
        initialized = true;
    }
    public void setImage(TextureRegion rgn) {
        objectiveImage = new Image(rgn);
        initialized = true;
    }
    public void setImage(Texture texture) {
        objectiveImage = new Image(texture);
        initialized = true;
    }
    public void setIndex(int index) {
        victConIndex = index;
    }
    public void clear() {
        cleared = true;
        // TODO: turn green and put a checkmark or something
    }

    // --GET--
    public int getIndex() {
        return victConIndex;
    }
    @Override
    public float getHeight() {
        return background.getHeight();
    }
}
