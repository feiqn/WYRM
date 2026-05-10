package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.elements;

import com.badlogic.gdx.Gdx;
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

    private final Label longestLabel;

    public GHUD_TileInfo(Skin skin) {
        super("", skin);
        this.skin = skin;

        super.setModal(false);
        super.setMovable(false);
        super.setResizable(false);

        // TODO: look in old_data cutscene player for
        //  setting fixed window width

//        tileInfoTable.setDebug(true);

        tileTypeLabel = new Label("", skin);
        tileTypeLabel.setFontScale(FONT_SCALE);

        tileInfoLabel = new Label("", skin);
        tileInfoLabel.setFontScale(FONT_SCALE);

        longestLabel = new Label("impassible wall", skin);

        this.add(tileInfoTable).expandX().right();

        this.setVisible(false);

        build();
    }

    private void build() {
        tileInfoTable.clearChildren();
        tileInfoTable.add(tileTypeLabel).right();
        tileInfoTable.row();
        tileInfoTable.add(tileInfoLabel).right();
        tileInfoTable.row();
    }

    public void setContext(GridTile tile) {
        if(tile == null) return;
        tileTypeLabel.setText(" " + tile.getTileType() + " ");
        if(isVisible) return;
        isVisible = true;
        setVisible(true);
        addAction(Actions.fadeIn(.3f));
    }

    public float longestWidth() { return longestLabel.getWidth(); }
}
