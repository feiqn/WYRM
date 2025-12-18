package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

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
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.grid.GridAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public abstract class GridActor extends WyrActor {

    public enum ActorType {
        UNIT,
        PROP,
    }

    protected GridScreen grid;
    protected boolean isSolid = false; // solid means absolutely impassible except by flying.
    protected GridTile occupiedTile;

    private int gridX;
    private int gridY;

    protected int maxHP;
    protected int rollingHP = maxHP;

    protected final ActorType actorType;

    protected final GridMetaHandler h;
    protected final GridAnimator animator;

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
        super(WyrType.GRIDWORLD, drawable, scaling, align);
        this.h = metaHandler;
        this.actorType = actorType;
        animator = new GridAnimator(h, this);
    }

    @Override
    public void act(float delta) {
        animator.update();
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // apply shader (?)
        super.draw(batch, parentAlpha);
        // remove shaders
    }

    public void setAnimationState(WyrAnimator.AnimationState state) {
        animator.setState(state);
    }

    public void applyDamage(int damage) {
        rollingHP -= damage;
        if(rollingHP > maxHP) rollingHP = maxHP; // negative damage can heal
        if(rollingHP <= 0) kill();
    }

    public void solidify() { isSolid = true; }
    public void unSolidify() { isSolid = false; }

    public abstract void occupy(GridTile tile);
    protected abstract void kill();

    public void applyShader(ShaderState shaderState) {
        if(!shaderStates.contains(shaderState, true)) shaderStates.add(shaderState);
    }
    public void removeShader(ShaderState shaderState) {
        if(shaderStates.contains(shaderState, true)) shaderStates.removeValue(shaderState, true);
    }

    public int getMaxHP() { return maxHP; }
    public int getRollingHP() { return rollingHP; }
    public boolean isSolid() { return isSolid; }
    public GridTile occupyingTile() { return occupiedTile; }
    public ActorType getActorType() { return actorType; }
    public Vector2 gridPosition() { return new Vector2(gridX, gridY); }
    public void setPosByGrid(int x, int y) {
        super.setPosition((x + .5f) - (this.getWidth() * .5f), y);
        gridX = x;
        gridY = y;
    }
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.gridY = (int) y; // TODO: watch for aerial values
        this.gridX = (int)((x + (this.getWidth()*.5f)) - .5f);
    }
    public WyrAnimator.AnimationState getAnimationState() { return animator.getState(); }

}
