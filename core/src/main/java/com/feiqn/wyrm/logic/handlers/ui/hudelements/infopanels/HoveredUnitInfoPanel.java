package com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
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
//        subTable.setDebug(true);
        subTable.add(nameLabel).left();
        subTable.row();
        subTable.add(hpLabel).left();
        layout.add(subTable).fill();
        setColor(1,1,1,0);

    }

    public void setUnit(SimpleUnit unit) {
        if(!init) addAction(Actions.fadeIn(1));
        thumbnail.setDrawable(new TextureRegionDrawable(unit.getThumbnail()));
        hpLabel.setText("HP: " + unit.getRollingHP() + "/" + unit.modifiedSimpleHealth());
        nameLabel.setText(unit.name);
    }

//    @Override
//    public void resized(int width, int height) {
//        super.resized(width, height);
//
//        layout.clear();
//        layout.add(thumbnail).size(height * 0.1f); // Dynamically size the thumbnail
//        layout.row();
//
//        Table subTable = new Table();
//        subTable.add(nameLabel).left();
//        subTable.row();
//        subTable.add(hpLabel).left();
//        layout.add(subTable).fill();
//    }

    public void clear() {

    }

}
