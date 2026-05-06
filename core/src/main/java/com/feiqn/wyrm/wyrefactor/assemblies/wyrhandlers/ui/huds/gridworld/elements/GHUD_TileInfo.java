package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import static com.feiqn.wyrm.wyrefactor.helpers.Wyr.FONT_SCALE;

public class GHUD_TileInfo extends Window {

    private boolean isVisible = false;

    private final Table tileInfoTable = new Table();
    private final Label tileTypeLabel;
    private final Label tileInfoLabel;

    private final Skin skin;

    public GHUD_TileInfo(Skin skin) {
        super("", skin);
        this.skin = skin;

        super.setModal(false);
        super.setMovable(false);
        super.setResizable(false);

        tileTypeLabel = new Label("", skin);
        tileTypeLabel.setFontScale(FONT_SCALE);

        tileInfoLabel = new Label("", skin);
        tileInfoLabel.setFontScale(FONT_SCALE);

        this.add(tileInfoTable).fill();

        this.setVisible(false);

        build();
    }

    private void build() {
        tileInfoTable.clearChildren();
        tileInfoTable.add(tileTypeLabel).right().expandX();
        tileInfoTable.row();
        tileInfoTable.add(tileInfoLabel).right();
        tileInfoTable.row();
    }

    public void setContext(GridTile tile) {
        tileTypeLabel.setText("" + tile.getTileType());
        if(isVisible) return;
        isVisible = true;
        addAction(Actions.fadeIn(.3f));
    }


}
