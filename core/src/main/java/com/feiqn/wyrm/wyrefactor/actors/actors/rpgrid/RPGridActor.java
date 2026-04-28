package com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid;

import com.badlogic.gdx.graphics.Texture;
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
import com.feiqn.wyrm.wyrefactor.actors.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.RPGridPersonalityType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.personality.grid.RPGGridPersonality;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import static com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator.RPGridAnimState.*;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid.RPGridStats.RPGStatType.*;

public abstract class RPGridActor extends WyrActor<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    protected boolean isSolid = false; // solid means absolutely impassible except by flying.
    protected GridTile occupiedTile;

    private int gridX;
    private int gridY;

    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType) {
        this(metaHandler, actorType, (Drawable)null);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, NinePatch patch) {
        this(metaHandler, actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, TextureRegion region) {
        this(metaHandler, actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, Texture texture) {
        this(metaHandler, actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, Skin skin, String drawableName) {
        this(metaHandler, actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, Drawable drawable) {
        this(metaHandler, actorType, drawable, Scaling.stretch, Align.center);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, Drawable drawable, Scaling scaling) {
        this(metaHandler, actorType,drawable, scaling, Align.center);
    }
    public RPGridActor(RPGridMetaHandler metaHandler, ActorType actorType, Drawable drawable, Scaling scaling, int align) {
        super(actorType, drawable, scaling, align);
        this.h = metaHandler;
        animator = new RPGridAnimator(h, this);
        animator.setState(IDLE);
    }

    @Override
    public void act(float delta) {
        animator.update();
        super.act(delta);
    }

    public void faceNorth() { animator.setState(FACING_NORTH); }
    public void faceSouth() { animator.setState(FACING_SOUTH); }
    public void faceEast()  { animator.setState(FACING_EAST);  }
    public void faceWest()  { animator.setState(FACING_WEST);  }

    public void solidify() { isSolid = true; }
    public void unSolidify() { isSolid = false; }

    public abstract void occupyTile(GridTile tile);

    public void setPosByGrid(int x, int y) {
        gridX = x;
        gridY = y;
        this.setPosition((x + .5f) - (this.getWidth() * .5f), y);
    }

    public RPGridActor setPersonality(RPGGridPersonality personality) {
        stats.setPersonality(personality);
        return this;
    }

    public RPGridActor setPersonalityType(RPGridPersonalityType type) {
        stats.getPersonality().setPersonalityType(type);
        return this;
    }

    public void setAnimationState(RPGridAnimator.RPGridAnimState state) { animator.setState(state);}

    public boolean canMove()   { return stats.getRollingAP() > 0; }
    public int     getReach()  { return 1; } // todo, stats.weapon.reach
    public int     moveSpeed() { return stats.getModifiedStatValue(SPEED); }
    public int     getModifiedStatValue(RPGridStats.RPGStatType stat) { return stats.getModifiedStatValue(stat); }

    public RPGGridPersonality              getPersonality()    { return (stats.getPersonality()); }
    public RPGridStats.RPGClass.RPGClassID getRPGClassID()     { return stats.getRPGClassID();    }
    public RPGridMovementType              getMovementType()   { return stats.getMovementType();  }
    public RPGridAnimator.RPGridAnimState  getAnimationState() { return animator.getState();      }

    public boolean  isSolid()         { return isSolid;                   }
    public GridTile getOccupiedTile() { return occupiedTile;              }
    public Vector2  getGridPosition() { return new Vector2(gridX, gridY); }
    public int      gridX()           { return gridX;                     }
    public int      gridY()           { return gridY;                     }

    @Override
    public RPGridStats stats() { return stats; }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRIDWORLD;
    }

}
