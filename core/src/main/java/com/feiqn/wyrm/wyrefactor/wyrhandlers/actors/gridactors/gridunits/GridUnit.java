package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public abstract class GridUnit extends GridActor {


    public GridUnit(WYRMGame root) {
        super(root);
    }

    public GridUnit(WYRMGame root, NinePatch patch) {
        super(root, patch);
    }

    public GridUnit(WYRMGame root, TextureRegion region) {
        super(root, region);
    }

    public GridUnit(WYRMGame root, Texture texture) {
        super(root, texture);
    }

    public GridUnit(WYRMGame root, Skin skin, String drawableName) {
        super(root, skin, drawableName);
    }

    public GridUnit(WYRMGame root, Drawable drawable) {
        super(root, drawable);
    }

    public GridUnit(WYRMGame root, Drawable drawable, Scaling scaling) {
        super(root, drawable, scaling);
    }

    public GridUnit(WYRMGame root, Drawable drawable, Scaling scaling, int align) {
        super(root, drawable, scaling, align);
    }

    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }


}
