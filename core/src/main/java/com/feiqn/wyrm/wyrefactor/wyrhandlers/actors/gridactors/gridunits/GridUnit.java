package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits;

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
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.SimpleStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public abstract class GridUnit extends GridActor {

    protected final SimpleStats stats;
    protected final UnitRoster rosterID;

    public GridUnit(WYRMGame root, UnitRoster rosterID) {
        this(root, rosterID, (Drawable)null);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, NinePatch patch) {
        this(root, rosterID, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, TextureRegion region) {
        this(root, rosterID, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Texture texture) {
        this(root, rosterID, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Skin skin, String drawableName) {
        this(root, rosterID, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable) {
        this(root, rosterID, drawable, Scaling.stretch, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable, Scaling scaling) {
        this(root, rosterID, drawable, scaling, Align.center);
    }
    public GridUnit(WYRMGame root, UnitRoster rosterID, Drawable drawable, Scaling scaling, int align) {
        super(root, ActorType.UNIT, drawable, scaling, align);
        stats = new SimpleStats();
        this.rosterID = rosterID;
    }


    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }

    public boolean canMove() { return stats.getActionPoints() > 0; }
    public UnitRoster getRosterID() { return rosterID; }
}
