package com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GHUD_UnifiedInfo extends Window {

    private final GridMetaHandler h; // It's fun to just type "h".

    private VerticalGroup verticalGroup;

    private final Array<Label> victoryConditionLabels = new Array<>();
    private final Array<Label> failureConditionLabels = new Array<>();

    private final Table winConTable   = new Table();
    private final Table tileInfoTable = new Table();
    private final Table unitInfoTable = new Table();
    private final Table propInfoTable = new Table();

    private Image unitPreviewThumbnail;
    private Image propPreviewThumbnail;

    private ProgressBar unitHealthBar;
    private ProgressBar propHealthBar;

    private Label tileTypeLabel;
    private Label tileStatsLabel;

    private Label unitNameLabel;
    private Label unitHealthLabel;
    private Label unitMoreInfoLabel;

    private Label propTitleLabel;
    private Label propHealthLabel;
    private Label propMoreInfoLabel;


    public GHUD_UnifiedInfo(Skin skin, GridMetaHandler metaHandler) {
        super("", skin);
        this.h = metaHandler;

        this.setModal(false);
        this.setMovable(false);
        this.setResizable(false);

        buildSubTable();
    }

    public void buildSubTable() {
        verticalGroup.clearChildren();
        winConTable.clearChildren();
        tileInfoTable.clearChildren();
        unitInfoTable.clearChildren();
        propInfoTable.clearChildren();


        // TODO:
        //  - victory / failure conditions
        //  - tile info
        //  - unit info
        //  - prop info
        //  - etc...

    }

    // TODO:
    //  - add win cons
    //  - add fail cons

    public void addTileInteraction(GridInteraction interaction) { }

    public void updateUnitContext(GridUnit unit) { }

    public void updateTileContext(GridTile tile) { }

    public void updatePropContext(GridProp prop) { }




    private final static class Divider extends ProgressBar {

        public Divider(Skin skin) {
            super(0, 1, 0, false, skin);
            this.clamp(1);
            this.setDisabled(true);
        }
    }

}
