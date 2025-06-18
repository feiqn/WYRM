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

    protected boolean detailed;

    protected Image thumbnail;

    protected Label hpLabel;
    protected Label nameLabel;
    protected Label strLabel;
    protected Label defLabel;
    protected Label magLabel;
    protected Label resLabel;
    protected Label spdLabel;
    protected Label detailToggleLabel;

    private boolean init;

    final Table subTable;

    public HoveredUnitInfoPanel(WYRMGame game) {
        super(game);
        init = false;
        detailed = false;

        thumbnail         = new Image();
        hpLabel           = new Label("HP: ", game.assetHandler.menuLabelStyle);
        nameLabel         = new Label("", game.assetHandler.menuLabelStyle);
        strLabel          = new Label("Str: ", game.assetHandler.menuLabelStyle);
        defLabel          = new Label("Def: ", game.assetHandler.menuLabelStyle);
        magLabel          = new Label("Mag: ", game.assetHandler.menuLabelStyle);
        resLabel          = new Label("Res: ", game.assetHandler.menuLabelStyle);
        spdLabel          = new Label("Spd: ", game.assetHandler.menuLabelStyle);
        detailToggleLabel = new Label("X : More info", game.assetHandler.menuLabelStyle);

        layout.add(thumbnail);

        subTable = new Table();

        buildSimple();

        layout.add(subTable).fill();
        setColor(1,1,1,0);

    }

    private void buildSimple() {
        detailed = false;
        subTable.clearChildren();

        subTable.add(nameLabel).left();
        subTable.row();
        subTable.add(hpLabel).left();
        subTable.row();
        subTable.add(detailToggleLabel).left().pad(3);

        detailToggleLabel.setText("X : More info");
    }

    private void buildDetailed() {
        buildSimple();

        detailed = true;
        detailToggleLabel.setText("X : Less info");

        subTable.row();
        subTable.add(strLabel).left();
        subTable.row();
        subTable.add(defLabel).left();
        subTable.row();
        subTable.add(magLabel).left();
        subTable.row();
        subTable.add(resLabel).left();
        subTable.row();
        subTable.add(spdLabel).left();
    }

    public void setUnit(SimpleUnit unit) {
        if(!init) addAction(Actions.fadeIn(1));
        thumbnail.setDrawable(new TextureRegionDrawable(unit.getThumbnail()));
        hpLabel.setText("HP: " + unit.getRollingHP() + "/" + unit.modifiedSimpleHealth());
        nameLabel.setText(unit.name);
        strLabel.setText("Str: " + unit.modifiedSimpleStrength());
        defLabel.setText("Def: " + unit.modifiedSimpleDefense());
        magLabel.setText("Mag: " + unit.modifiedSimpleMagic());
        resLabel.setText("Res: " + unit.modifiedSimpleResistance());
        spdLabel.setText("Spd: " + unit.modifiedSimpleSpeed());
    }

    public void toggleDetailed() {
        if(detailed) {
            buildSimple();
        } else {
            buildDetailed();
        }
    }

    public void clear() {

    }

}
