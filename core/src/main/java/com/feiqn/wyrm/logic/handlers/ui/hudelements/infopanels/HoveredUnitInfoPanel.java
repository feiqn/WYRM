package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HoveredUnitInfoPanel extends HUDElement {

    // top corner or hover

    private final HoveredUnitInfoPanel self = this;

    protected Image thumbnail;

    protected Label hpLabel;
    protected Label nameLabel;

    public HoveredUnitInfoPanel(WYRMGame game) {
        super(game);

        thumbnail  = new Image();
        hpLabel    = new Label("HP: ", game.assetHandler.menuLabelStyle);
        nameLabel  = new Label("", game.assetHandler.menuLabelStyle);

        layout.add(thumbnail);

        final Table subTable = new Table(); // TODO: cleanup visually
        subTable.add(nameLabel);
        subTable.row();
        subTable.add(hpLabel);
        layout.add(subTable).fill();
    }

    public void setUnit(Unit unit) {
        thumbnail.setDrawable(new TextureRegionDrawable(unit.getThumbnail()));
        hpLabel.setText("HP: " + unit.getRollingHP() + "/" + unit.getModifiedMaxHP());
        nameLabel.setText(unit.name);
    }

    public void clear() {

    }

}
