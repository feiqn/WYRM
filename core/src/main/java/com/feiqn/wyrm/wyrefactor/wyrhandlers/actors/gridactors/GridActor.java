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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public abstract class GridActor extends WyrActor {

    public enum ActorType {
        UNIT,
        PROP,
    }

    public enum ShaderState {
        DIM,
        HIGHLIGHT,
        TEAM_ENEMY,
        TEAM_OTHER,
        TEAM_ALLY,
    }

    protected GridScreen grid;
    protected boolean isSolid = false;
    protected GridTile occupiedTile;

    private int gridX;
    private int gridY;

    protected int maxHP;
    protected int rollingHP = maxHP;

    protected final ActorType actorType;
    protected final Array<ShaderState> shaderStates = new Array<>();

    public GridActor(WYRMGame root, ActorType actorType) {
        this(root, actorType, (Drawable)null);
    }
    public GridActor(WYRMGame root, ActorType actorType, NinePatch patch) {
        this(root, actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridActor(WYRMGame root, ActorType actorType, TextureRegion region) {
        this(root, actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridActor(WYRMGame root, ActorType actorType, Texture texture) {
        this(root, actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridActor(WYRMGame root, ActorType actorType, Skin skin, String drawableName) {
        this(root, actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridActor(WYRMGame root, ActorType actorType, Drawable drawable) {
        this(root, actorType, drawable, Scaling.stretch, Align.center);
    }
    public GridActor(WYRMGame root, ActorType actorType, Drawable drawable, Scaling scaling) {
        this(root, actorType,drawable, scaling, Align.center);
    }
    public GridActor(WYRMGame root, ActorType actorType, Drawable drawable, Scaling scaling, int align) {
        super(root, WyrType.GRIDWORLD, drawable, scaling, align);
        assert root.activeScreen() instanceof GridScreen;
        this.grid = (GridScreen) root.activeScreen();
        this.actorType = actorType;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // apply shader (?)
        super.draw(batch, parentAlpha);
        // remove shaders
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
}
