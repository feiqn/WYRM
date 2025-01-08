package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

    private boolean init;

    public HoveredUnitInfoPanel(WYRMGame game) {
        super(game);
        init = false;

        thumbnail  = new Image();
        hpLabel    = new Label("HP: ", game.assetHandler.menuLabelStyle);
        nameLabel  = new Label("", game.assetHandler.menuLabelStyle);

        layout.add(thumbnail);

        final Table subTable = new Table(); // TODO: cleanup visually
        subTable.add(nameLabel);
        subTable.row();
        subTable.add(hpLabel);
        layout.add(subTable).fill();
        setColor(1,1,1,0);

    }

    public void setUnit(Unit unit) {
        if(!init) addAction(Actions.fadeIn(1));
        thumbnail.setDrawable(new TextureRegionDrawable(unit.getThumbnail()));
//        thumbnail.setSize(layout.getHeight(), layout.getHeight());
        hpLabel.setText("HP: " + unit.getRollingHP() + "/" + unit.getModifiedMaxHP());
        nameLabel.setText(unit.name);
    }

    public void clear() {

    }

}
