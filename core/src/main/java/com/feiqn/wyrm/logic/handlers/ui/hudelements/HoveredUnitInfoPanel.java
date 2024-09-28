package com.feiqn.wyrm.logic.handlers.ui.hudelements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HoveredUnitInfoPanel extends Group {

    // top corner or hover,
    // unit thumbnail,
    // unit hp,
    // unit name,
    // background image

    private final WYRMGame game;

    private final HoveredUnitInfoPanel self = this;

    private Image background;

    protected Image thumbnail;

    protected Label hpLabel;
    protected Label nameLabel;


    private ClickListener mouseListener;

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

        background.setSize(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .2f);

        setPosition(Gdx.graphics.getWidth() - background.getWidth(), Gdx.graphics.getHeight() - background.getHeight());
    }

    public void setUnit(Unit unit) {
        thumbnail = unit.getThumbnail();
        hpLabel.setText("HP: " + unit.getModifiedMaxHP() + "/" + unit.getCurrentHP());
        nameLabel.setText(unit.getName());
    }

}
