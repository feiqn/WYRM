package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.WyrGridScreen;

public abstract class GridActor extends WyrActor {

    protected WyrGridScreen grid;
    protected boolean isSolid = false;
    protected GridTile occupiedTile;

    protected int maxHP;
    protected int rollingHP = maxHP;

    public GridActor(WYRMGame root) {
        super(root);
    }

    public GridActor(WYRMGame root, NinePatch patch) {
        super(root, patch);
    }

    public GridActor(WYRMGame root, TextureRegion region) {
        super(root, region);
    }

    public GridActor(WYRMGame root, Texture texture) {
        super(root, texture);
    }

    public GridActor(WYRMGame root, Skin skin, String drawableName) {
        super(root, skin, drawableName);
    }

    public GridActor(WYRMGame root, Drawable drawable) {
        super(root, drawable);
    }

    public GridActor(WYRMGame root, Drawable drawable, Scaling scaling) {
        super(root, drawable, scaling);
    }

    public GridActor(WYRMGame root, Drawable drawable, Scaling scaling, int align) {
        super(root, drawable, scaling, align);
        assert root.getActiveScreen() instanceof WyrGridScreen;
        this.grid = (WyrGridScreen) root.getActiveScreen();
//        idle();
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
}
