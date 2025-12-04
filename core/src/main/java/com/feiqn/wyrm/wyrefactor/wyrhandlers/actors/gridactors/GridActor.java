package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

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
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.WyrGridScreen;

public abstract class GridActor extends WyrActor {

    public enum ActorType {
        UNIT,
        PROP,
    }

    // TODO:
    //  states to track which shaders should apply,
    //  dim, highlight, team override,
    //  oh boy,,,

    protected WyrGridScreen grid;
    protected boolean isSolid = false;
    protected GridTile occupiedTile;

    protected int maxHP;
    protected int rollingHP = maxHP;

    protected final ActorType actorType;

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
        assert root.getActiveScreen() instanceof WyrGridScreen;
        this.grid = (WyrGridScreen) root.getActiveScreen();
        this.actorType = actorType;
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

    public int getMaxHP() { return maxHP; }
    public int getRollingHP() { return rollingHP; }
    public boolean isSolid() { return isSolid; }
    public GridTile occupyingTile() { return occupiedTile; }
    public ActorType getActorType() { return actorType; }
}
