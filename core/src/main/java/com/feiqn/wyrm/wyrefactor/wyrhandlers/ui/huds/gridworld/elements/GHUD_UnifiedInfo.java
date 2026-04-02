package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public class GHUD_UnifiedInfo extends Window {

    private final GridMetaHandler h; // It's fun to just type "h".

    private final VerticalGroup verticalGroup = new VerticalGroup();

    private final Array<Label> victoryConditionLabels = new Array<>();
    private final Array<Label> failureConditionLabels = new Array<>();

    private final Table winConTable   = new Table();
    private final Table tileInfoTable = new Table();
    private final Table unitInfoTable = new Table();
    private final Table propInfoTable = new Table();

    private Image unitPreviewThumbnail;
    private Image propPreviewThumbnail;

    private HealthBar unitHealthBar;
    private HealthBar propHealthBar;

    private Label tileTypeLabel;
    private Label tileStatsLabel;

    private Label unitNameLabel;
    private Label unitHealthLabel;
    private Label unitMoreInfoLabel;

    private Label propTitleLabel;
    private Label propHealthLabel;
    private Label propMoreInfoLabel;

    private final Skin skin; // TODO: static asset ref


    public GHUD_UnifiedInfo(Skin skin, GridMetaHandler metaHandler) {
        super("", skin);
        this.h = metaHandler;
        this.skin = skin;

        this.setModal(false);
        this.setMovable(false);
        this.setResizable(false);

        tileTypeLabel  = new Label("Tile: ", skin);
        tileStatsLabel = new Label("Bonuses: ", skin);

        unitNameLabel     = new Label("", skin);
        unitHealthLabel   = new Label("HP:", skin);
        unitMoreInfoLabel = new Label("More Info: (X)", skin);

        propTitleLabel    = new Label("", skin);
        propHealthLabel   = new Label("HP:", skin);
        propMoreInfoLabel = new Label("More Info: (Z)", skin);

        buildSubTable();
    }

    public void buildSubTable() {
        verticalGroup.clearChildren();
        winConTable.clearChildren();
        tileInfoTable.clearChildren();
        unitInfoTable.clearChildren();
        propInfoTable.clearChildren();

        winConTable.add(new Label("Victory:", skin));
        winConTable.row();
        // for con in cons... add to
        winConTable.add(new Label("Failure:", skin));
        winConTable.row();
        // for cons...

        tileInfoTable.add(tileTypeLabel);
        tileInfoTable.row();
        tileInfoTable.add(tileStatsLabel);
        tileInfoTable.row();

        unitInfoTable.add(unitNameLabel);
        unitInfoTable.row();
        unitInfoTable.add(unitHealthLabel);
        unitInfoTable.add(unitHealthBar);
        unitInfoTable.row();
        unitInfoTable.add(unitMoreInfoLabel);

        propInfoTable.add(propTitleLabel);
        propInfoTable.row();
        propInfoTable.add(propHealthLabel);
        propInfoTable.add(propHealthBar);
        propInfoTable.row();
        propInfoTable.add(propMoreInfoLabel);

        this.add(winConTable).fill();
        this.row();
        this.add(new Divider(skin)).fill();
        this.row();
        this.add(tileInfoTable).fill();
        this.row();
        this.add(new Divider(skin)).fill();
        this.row();
        this.add(unitInfoTable).fill();
        this.row();
        this.add(new Divider(skin)).fill();
        this.row();
        this.add(propInfoTable).fill();
        this.row();
    }

    // TODO:
    //  - add win cons
    //  - add fail cons

    public void updateUnitContext(GridUnit unit) {
        unitNameLabel.setText(unit.getName());
        unitHealthBar = new HealthBar(skin, unit);
    }

    public void updateTileContext(GridTile tile) {
        tileTypeLabel.setText("Tile: " + tileTypeLabel);
        tileStatsLabel.setText("Bonus Defense: " + tile.getDefenseValue());
    }

    public void updatePropContext(GridProp prop) { }



    private final static class HealthBar extends ProgressBar {

        private GridActor tracking;

        public HealthBar(Skin skin, GridActor actor) {
            super(0, actor.stats().getMaxHP(), 1, false, skin);
            this.setHeight(0.5f);
            this.setDisabled(true);
            this.setValue(actor.stats().getRollingHP());
            this.tracking = actor;
        }

        public void update() {
            this.setValue(tracking.stats().getRollingHP());
        }

    }

    private final static class Divider extends ProgressBar {

        public Divider(Skin skin) {
            super(0, 1, 1, false, skin);
            this.clamp(1);
            this.setHeight(0.2f);
            this.setDisabled(true);
        }

    }

}
