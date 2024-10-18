package com.feiqn.wyrm.logic.handlers.ui.hudelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HoveredUnitInfoPanel extends HUDElement {

    // top corner or hover

    private final WYRMGame game;

    private final HoveredUnitInfoPanel self = this;

    private Image background;

    protected Image thumbnail;

    protected Label hpLabel;
    protected Label nameLabel;

    public HoveredUnitInfoPanel(WYRMGame game) {
        this.game = game;

        background = new Image(game.assetHandler.blueButtonTexture);
        thumbnail  = new Image();
        hpLabel    = new Label("HP: ", game.assetHandler.menuLabelStyle);
        nameLabel  = new Label("", game.assetHandler.menuLabelStyle);

        addActor(background);
        addActor(thumbnail);
        addActor(hpLabel);
        addActor(nameLabel);

        final float width = Gdx.graphics.getWidth() * .3f;

        background.setSize(width, width * .4f);

        setPosition(Gdx.graphics.getWidth() - background.getWidth(), Gdx.graphics.getHeight() - background.getHeight());

//        thumbnail.setPosition(background.getWidth() * .2f, background.getHeight() * .8f);

        nameLabel.setPosition(background.getWidth() * .25f, background.getHeight() * .65f);

        hpLabel.setPosition(background.getWidth() * .1f, background.getHeight() * .25f);
    }

    public void setUnit(Unit unit) {
        thumbnail = new Image(unit.getThumbnail());
        hpLabel.setText("HP: " + unit.getCurrentHP() + "/" + unit.getModifiedMaxHP());
        nameLabel.setText(unit.name);
    }

}
