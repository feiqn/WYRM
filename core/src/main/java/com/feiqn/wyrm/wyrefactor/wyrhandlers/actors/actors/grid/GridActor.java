package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.grid.GridAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public abstract class GridActor extends WyrActor<GridAnimator> {

    protected GridScreen grid;
    protected boolean isSolid = false; // solid means absolutely impassible except by flying.
    protected GridTile occupiedTile;

    private int gridX;
    private int gridY;

    protected final GridMetaHandler h;
    protected final GridAnimator gridAnimator;

    public GridActor(GridMetaHandler metaHandler, ActorType actorType) {
        this(metaHandler, actorType, (Drawable)null);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, NinePatch patch) {
        this(metaHandler, actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, TextureRegion region) {
        this(metaHandler, actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, Texture texture) {
        this(metaHandler, actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, Skin skin, String drawableName) {
        this(metaHandler, actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, Drawable drawable) {
        this(metaHandler, actorType, drawable, Scaling.stretch, Align.center);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, Drawable drawable, Scaling scaling) {
        this(metaHandler, actorType,drawable, scaling, Align.center);
    }
    public GridActor(GridMetaHandler metaHandler, ActorType actorType, Drawable drawable, Scaling scaling, int align) {
        super(WyrType.GRIDWORLD, actorType, drawable, scaling, align);
        this.h = metaHandler;
        gridAnimator = new GridAnimator(h, this);
        gridAnimator.setState(WyrAnimator.AnimationState.IDLE);
    }

    @Override
    public void act(float delta) {
        gridAnimator.update();
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // apply shader (?)
        super.draw(batch, parentAlpha);
        // remove shaders
    }

    public void setAnimationState(WyrAnimator.AnimationState state) {
        gridAnimator.setState(state);
    }

    public void faceNorth() {
        gridAnimator.setState(WyrAnimator.AnimationState.FACING_NORTH);
    }

    public void faceSouth() {
        gridAnimator.setState(WyrAnimator.AnimationState.FACING_SOUTH);
    }

    public void faceEast() {
        gridAnimator.setState(WyrAnimator.AnimationState.FACING_EAST);
    }

    public void faceWest() {
        gridAnimator.setState(WyrAnimator.AnimationState.FACING_WEST);
    }

    public void solidify() { isSolid = true; }
    public void unSolidify() { isSolid = false; }

    public abstract void occupy(GridTile tile);
    protected abstract void kill();

    public WyrStats stats() { return stats; }
    public boolean isSolid() { return isSolid; }
    public GridTile getOccupiedTile() { return occupiedTile; }
    public ActorType getActorType() { return actorType; }
    public Vector2 getGridPosition() { return new Vector2(gridX, gridY); }
    public int gridX() { return gridX; }
    public int gridY() { return gridY; }
    public void setPosByGrid(int x, int y) {
        gridX = x;
        gridY = y;
        this.setPosition((x + .5f) - (this.getWidth() * .5f), y);
    }
    public WyrAnimator.AnimationState getAnimationState() { return gridAnimator.getState(); }

}
