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
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
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
    protected boolean aerial = false;

    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange) {
        this(metaHandler, propType, interactRange, (Drawable)null);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, NinePatch patch) {
        this(metaHandler, propType, interactRange, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, TextureRegion region) {
        this(metaHandler, propType, interactRange, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, Texture texture) {
        this(metaHandler, propType, interactRange, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, Skin skin, String drawableName) {
        this(metaHandler, propType, interactRange, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, Drawable drawable) {
        this(metaHandler, propType, interactRange, drawable, Scaling.stretch, Align.center);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, Drawable drawable, Scaling scaling) {
        this(metaHandler, propType, interactRange, drawable, scaling, Align.center);
    }
    public GridProp(GridMetaHandler metaHandler, PropType propType, int interactRange, Drawable drawable, Scaling scaling, int align) {
        super(metaHandler, ActorType.PROP, drawable, scaling, align);
        this.propType = propType;
    }

    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.setProp(this);
    }

    public void occupyAirspace(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.setAerialProp(this);
    }

    public PropType getPropType() { return propType; }
    public boolean isAerial() { return aerial; }
}
