package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.shaders.WyrShaders;
import com.feiqn.wyrm.wyrefactor.helpers.ShaderState;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.RPGridPersonalityType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.grid.RPGGridPersonality;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.grid.RPGridAnimator.RPGridAnimState.IDLE;
import static com.feiqn.wyrm.wyrefactor.helpers.ActorType.UNIT;
import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.RPGridPersonalityType.PLAYER;

public abstract class RPGridUnit extends RPGridActor {

    protected final UnitIDRoster rosterID;
    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;

    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID) {
        this(metaHandler, rosterID, (Drawable)null);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, NinePatch patch) {
        this(metaHandler, rosterID, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, TextureRegion region) {
        this(metaHandler, rosterID, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, Texture texture) {
        this(metaHandler, rosterID, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, Skin skin, String drawableName) {
        this(metaHandler, rosterID, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable) {
        this(metaHandler, rosterID, drawable, Scaling.stretch, Align.center);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling) {
        this(metaHandler, rosterID, drawable, scaling, Align.center);
    }
    public RPGridUnit(RPGridMetaHandler metaHandler, UnitIDRoster rosterID, Drawable drawable, Scaling scaling, int align) {
        super(metaHandler, UNIT, drawable, scaling, align);
        this.rosterID = rosterID;
        stats = new RPGridStats(this);
        stats.setPersonality(new RPGGridPersonality(PLAYER));
        animator = new RPGridAnimator(h, this);
        animator.generateAnimations();
        animator.setState(IDLE);
    }

    public void resetForNextTurn() {
        stats.tickDownConditions(true);
        stats.restoreAP();
    }

    public void occupyTile(GridTile tile) {
        if(occupiedTile == tile) return;
        if(occupiedTile != null) occupiedTile.vacate();
        occupiedTile = tile;
        occupiedTile.occupy(this);
    }


    public RPGridUnit setTeamAlignment(TeamAlignment alignment) {
        this.teamAlignment = alignment;
        this.applyShader(super.shaderState);
        return this;
    }

    @Override
    public void applyShader(ShaderState state) {
        if(super.shaderState != state) super.shaderState = state;
        switch(teamAlignment) {
            case PLAYER:
                switch(shaderState) {
                    case DIM:
                        super.shader = WyrShaders.Player.dim();
                        break;
                    case HIGHLIGHT:
                        super.shader = WyrShaders.Player.highlight();
                        break;
                    case STANDARD:
                        super.shader = WyrShaders.Player.standard();
                        break;
                }
                break;
            case ALLY:
                switch(shaderState) {
                    case DIM:
                        super.shader = WyrShaders.Ally.dim();
                        break;
                    case HIGHLIGHT:
                        super.shader = WyrShaders.Ally.highlight();
                        break;
                    case STANDARD:
                        super.shader = WyrShaders.Ally.standard();
                        break;
                    }
                break;
            case ENEMY:
                switch(shaderState) {
                    case DIM:
                        super.shader = WyrShaders.Enemy.dim();
                        break;
                    case HIGHLIGHT:
                        super.shader = WyrShaders.Enemy.highlight();
                        break;
                    case STANDARD:
                        super.shader = WyrShaders.Enemy.standard();
                        break;
                }
                break;
            case STRANGER:
                switch(shaderState) {
                    case DIM:
                        super.shader = WyrShaders.Stranger.dim();
                        break;
                    case HIGHLIGHT:
                        super.shader = WyrShaders.Stranger.highlight();
                        break;
                    case STANDARD:
                        super.shader = WyrShaders.Stranger.standard();
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void kill() {
        // remove from game logic, etc...
        h.register().removeFromTurnOrder(this);
        occupiedTile.vacate();
        super.kill();
    }

    @Override
    public RPGridUnit setPersonality(RPGGridPersonality personality) {
        super.setPersonality(personality);
        return this;
    }

    @Override
    public RPGridUnit setPersonalityType(RPGridPersonalityType type) {
        super.setPersonalityType(type);
        return this;
    }

    public UnitIDRoster getRosterID() { return rosterID; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }

}
