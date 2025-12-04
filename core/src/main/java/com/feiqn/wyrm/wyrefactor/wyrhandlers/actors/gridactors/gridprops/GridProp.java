package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public abstract class GridProp extends GridActor {

    public enum PropType {
        DOOR,
        CHEST,
        TORCH,
        BREAKABLE_WALL,
        BALLISTA,
        FLAMETHROWER,
        TREE,

        OBJECTIVE_SEIZE,
        OBJECTIVE_ESCAPE,
        OBJECTIVE_PROTECT,
    }

    protected final PropType propType;

    public GridProp(WYRMGame root, PropType propType) {
        this(root, propType, (Drawable)null);
    }
    public GridProp(WYRMGame root, PropType propType, NinePatch patch) {
        this(root, propType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridProp(WYRMGame root, PropType propType, TextureRegion region) {
        this(root, propType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridProp(WYRMGame root, PropType propType, Texture texture) {
        this(root, propType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridProp(WYRMGame root, PropType propType, Skin skin, String drawableName) {
        this(root, propType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridProp(WYRMGame root, PropType propType, Drawable drawable) {
        this(root, propType, drawable, Scaling.stretch, Align.center);
    }
    public GridProp(WYRMGame root, PropType propType, Drawable drawable, Scaling scaling) {
        this(root, propType, drawable, scaling, Align.center);
    }
    public GridProp(WYRMGame root, PropType propType, Drawable drawable, Scaling scaling, int align) {
        super(root, ActorType.PROP, drawable, scaling, align);
        this.propType = propType;
    }

    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.setProp(this);
    }

    public PropType getPropType() { return propType; }
}
