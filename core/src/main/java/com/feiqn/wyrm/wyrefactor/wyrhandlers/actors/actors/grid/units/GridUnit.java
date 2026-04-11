package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.Personality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.grid.GridPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

public abstract class GridUnit extends GridActor {

    protected final UnitIDRoster rosterID;
    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;
    protected GridPersonality personality;

    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID) {
        this(metaHandler, rosterID, (Drawable)null);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, NinePatch patch) {
        this(metaHandler, rosterID, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, TextureRegion region) {
        this(metaHandler, rosterID, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Texture texture) {
        this(metaHandler, rosterID, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Skin skin, String drawableName) {
        this(metaHandler, rosterID, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable) {
        this(metaHandler, rosterID, drawable, Scaling.stretch, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling) {
        this(metaHandler, rosterID, drawable, scaling, Align.center);
    }
    public GridUnit(GridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling, int align) {
        super(metaHandler, ActorType.UNIT, drawable, scaling, align);
        this.rosterID = rosterID;
        this.personality = new GridPersonality(Personality.PLAYER);
        gridAnimator.generateAnimations();
    }

    public void resetForNextTurn() {
        stats.tickDownConditions(true);
        stats.restoreAP();
        // TODO: shader
    }


    public void occupy(GridTile tile) {
        if(occupiedTile == tile) return;
        if(occupiedTile != null) occupiedTile.vacate();
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }

    @Override
    public void hoverOver() {
        super.hoverOver();
//
//        // TODO:
//        //  - update hud with unit's info
//        //  - highlight units and props too
//
//        if(h.conditions().unitsHoldingPriority().contains(this, true)) return;
//        if(h.input().getInputMode() == GridInputHandler.InputMode.MENU_FOCUSED) return; // TODO: appropriate behavior
//
//        h.map().clearAllHighlights();
//        h.map().highlightTiles(
//            GridPathfinder.currentlyAccessibleTo(h.map(), this).tiles().keySet()
//        );
//        this.occupiedTile.unhighlight(); // TODO: a click listener on the unit should
//                                         //  handle the job of a tile highlighter for
//                                         //  the tile the unit is occupying.
//                                         // Oh worm, good call.
    }

    @Override
    public void unHover() {
        super.unHover();

//        h.map().clearAllHighlights();
//        h.conditions().parsePriority();
    }

    @Override
    public void kill() {

    }

    public GridUnit setPersonality(GridPersonality personality) {
        this.personality = personality;
        return this;
    }
    public GridUnit setTeamAlignment(TeamAlignment alignment) {
        this.teamAlignment = alignment;
        switch(alignment){
            case ENEMY:
                // TODO: shaders are cool.
                applyShader(ShaderState.TEAM_ENEMY);
                break;
            case ALLY:
                applyShader(ShaderState.TEAM_ALLY);
                break;
            case OTHER:
                applyShader(ShaderState.TEAM_OTHER);
                break;
        }
        return this;
    }

    public int modifiedStatValue(StatType stat) {
        return stats.modifiedStatValue(stat);
    }
    public boolean canMove() { return stats.getActionPoints() > 0; }
    public UnitIDRoster getRosterID() { return rosterID; }
    public MovementType getMovementType() { return stats.movementType(); }
    public WyrStats.RPGClass.RPGClassID classID() { return stats.classID(); }
    public int getReach() { return 1; } // todo, stats.weapon.reach
    public GridPersonality personality() { return personality; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }

}
