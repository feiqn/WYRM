package com.feiqn.wyrm.logic.handlers.ui.hudelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;

public class VictConInfoPanel extends Group {

    // One panel for each objective per stage,
    // Persistent in top corner,
    // Clickable for details about objective
    // has text and image

    private final WYRMGame game;

    protected Label objectiveLabel;
    protected Label moreInfoLabel;

    protected Image objectiveImage;
    protected Image background;

    protected int victConIndex;

    protected ClickListener listener;

    protected boolean cleared;
    private boolean initialized;

    public VictConInfoPanel(WYRMGame game) {
        this.game      = game;
        objectiveImage = new Image();
        victConIndex   = -1;
        listener       = new ClickListener(); // TODO
        cleared        = false;
        objectiveLabel = new Label("Objective Unknown", game.assetHandler.menuLabelStyle);
        moreInfoLabel  = new Label("More info", game.assetHandler.menuLabelStyle);
        background     = new Image(game.assetHandler.blueButtonTexture);
        initialized    = false;

        background.setSize(Gdx.graphics.getWidth() * .35f, Gdx.graphics.getHeight() * .15f);

        objectiveLabel.setPosition(background.getWidth() * .9f, background.getHeight() * .5f);

        addActor(background);
        addActor(objectiveLabel);

    }

    protected void displayMoreInfo() {
        final Image moreInfoBackground = new Image(game.assetHandler.blueButtonTexture);

        moreInfoBackground.setSize(Gdx.graphics.getWidth() * .8f, Gdx.graphics.getHeight() * .8f);

        // TODO

    }

    // --SETTERS--
    public void setObjectiveLabelText(CharSequence newText) {
        objectiveLabel.setText(newText);
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

}
