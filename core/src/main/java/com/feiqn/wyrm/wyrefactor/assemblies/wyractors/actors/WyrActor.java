package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.WyrPersonality;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Character.PersonalityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.PropType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.RPGClassID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.AnimationState.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatType.*;

/** Top-level for any actor in the WyrFrame system.
 */
public class WyrActor extends Image implements Wyr, Examinable {

    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;
    protected ActorType actorType = null;
    protected WyrAnimator animator = null;
    protected WyrStats stats = null;
    protected WyrInventory inventory = null;
    protected WyrPersonality personality = null;

    protected final Array<WyrInteraction> staticInteractions = new Array<>();
    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader = null;

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    protected boolean isSolid = false; // solid means absolutely impassible on grid pathing except by flying.
    protected RPGridTile occupiedTile;

    private int gridX;
    private int gridY;

    public WyrActor() {
        this((Drawable) null);
    }

    public WyrActor(@Null NinePatch patch) {
        this(new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }

    public WyrActor(@Null TextureRegion region) {
        this(new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }

    public WyrActor(Texture texture) {
        this(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public WyrActor(Skin skin, String drawableName) {
        this(skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }

    public WyrActor(@Null Drawable drawable) {
        this(drawable, Scaling.stretch, Align.center);
    }

    public WyrActor(@Null Drawable drawable, Scaling scaling) {
        this(drawable, scaling, Align.center);
    }

    public WyrActor(@Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.setSize(1, 1); // just a little square

        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoveredOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hoveredOver = false;
            }
        });
    }

    @Override
    public void act(float delta) {
        if (!hoveredOver && hoverTime > 0) { // tick down
            hoverTime -= delta;
            if (hoverTime <= 0) {
                hoverTime = 0;
                unHover();
            }
        } else if (!hoverActivated && hoveredOver && hoverTime < .1f) { // tick up
            hoverTime += delta;
            if (hoverTime >= .1f) {
                hoverOver();
            }
        }

        getAnimator().update();
        super.act(delta);
    }

    public void resetForNextTurn() {
        stats.tickDownConditions(true);
        stats.restoreAP();
    }

    public WyrActor setTeamAlignment(TeamAlignment alignment) {
        teamAlignment = alignment;
        applyShader(ShaderState.STANDARD);
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    protected void hoverOver() { hoverActivated = true; }

    protected void unHover() { hoverActivated = false; }

    public void applyShader(ShaderState state) {
        if (this.shaderState == state) return;
        this.shaderState = state;
        switch (shaderState) {
            case DIM:
                this.shader = WyrShaders.Player.dim();
                break;
            case HIGHLIGHT:
                this.shader = WyrShaders.Player.highlight();
                break;
            case STANDARD:
                this.shader = WyrShaders.Player.standard();
                break;
            default:
                break;
        }
    }

    public void addEphemeralInteraction(WyrInteraction interaction) {
        ephemeralInteractions.add(interaction);
    }

    public void clearEphemeralInteractions() {
        ephemeralInteractions.clear();
    }

    public Array<WyrInteraction> getInteractions() {
        final Array<WyrInteraction> rV = new Array<>();
        rV.addAll(ephemeralInteractions);
        rV.addAll(staticInteractions);
        return rV;
    }

    public @Null WyrAnimator getAnimator() { return animator; }
    public @Null WyrInventory getInventory() { return inventory; }

    public int getMaxHP() { return stats.getMaxHP(); }
    public int getRollingHP() { return stats.getRollingHP(); }
    public int getRollingAP() { return stats.getRollingAP(); }
    public ActorType getActorType() { return actorType; }
    public WyrStats stats() { return stats; }

    public void faceNorth() { getAnimator().setState(FACING_NORTH); }
    public void faceSouth() { getAnimator().setState(FACING_SOUTH); }
    public void faceEast() { getAnimator().setState(FACING_EAST); }
    public void faceWest() { getAnimator().setState(FACING_WEST); }
    public void idle() { getAnimator().setState(IDLE); }
    public void flourish() { getAnimator().setState(FLOURISH); }

    public void solidify() { isSolid = true; }
    public void unSolidify() { isSolid = false; }

    public void setPosByGrid(int x, int y) {
        gridX = x;
        gridY = y;
        this.setPosition((x + .5f) - (this.getWidth() * .5f), y);
    }

    public void standardize() {
        clearEphemeralInteractions();
        if(getRollingAP() > 0) {
            applyShader(ShaderState.STANDARD);
        } else {
            applyShader(ShaderState.DIM);
        }
        setAnimationState(AnimationState.IDLE);
    }

    public void setAnimationState(AnimationState state) { animator.setState(state); }

    public int getReach() {
        return 1;
    } // todo, stats.weapon.reach
    public int moveSpeed() { return stats().getModifiedStatValue(SPEED); }
    public int getModifiedStatValue(StatType stat) { return stats().getModifiedStatValue(stat); }
    public @Null WyrPersonality getPersonality() { return personality; }
    public RPGClassID getRPGClassID() { return stats().getRPGClassID(); }
    public MobilityType getMobilityType() { return stats().getMovementType(); }
    public AnimationState getAnimationState() { return animator.getState(); }
    public boolean isSolid() { return isSolid; }
    public RPGridTile getOccupiedTile() { return occupiedTile; }
    public Vector2 getGridPosition() { return new Vector2(gridX, gridY); }
    public int gridX() { return gridX; }
    public int gridY() { return gridY; }

    /**
     * Props and tiles make up the world.
     */
    public static class Prop extends WyrActor {

        protected PropType propType;
        protected boolean aerial = false;

        public Prop(PropType type, TextureRegion region) {
            super(region);
            propType = type;
            actorType = ActorType.PROP;
            animator = new WyrAnimator(this);
            stats = new WyrStats(this);
            // todo: prop inventory
        }

        public void occupyTile(RPGridTile tile) {
            if (occupiedTile == tile) return;
            occupiedTile = tile;
            occupiedTile.setProp(this);
        }

        public void occupyAirspace(RPGridTile tile) {
            if (occupiedTile == tile) return;
            occupiedTile = tile;
//            occupiedTile.setAerialProp(this);
        }

        public boolean isAerial() {
            return aerial;
        }

        public PropType getPropType() {
            return propType;
        }

    }

    /**
     * Units live in the world.
     */
    public static class Unit extends WyrActor {

        protected final Character.Name charID;

        public Unit(Character.Name id, TextureRegion textureRegion) {
            super(textureRegion);
            actorType = ActorType.ENTITY;
            charID = id;
            stats = new WyrStats(this);
            animator = new WyrAnimator(this);
            animator.generateAnimations();
            idle();
            inventory = new WyrInventory();
            personality = new WyrPersonality(PersonalityType.STILL);
        }

        public WyrActor.Unit setPersonality(WyrPersonality personality) {
            this.personality = personality;
            return this;
        }

        public WyrActor.Unit setPersonalityType(PersonalityType type) {
            personality.setPersonalityType(type);
            return this;
        }

        public void kill() {
            handlers.register().removeFromTurnOrder(this);
            occupiedTile.vacate();
            this.remove();
        }

        public Character.Name getCharacterID() { return charID; }

        public TeamAlignment getTeamAlignment() { return teamAlignment; }

        @Override
        public WyrActor.Unit setTeamAlignment(TeamAlignment alignment) {
            switch (alignment) {
                case PLAYER:
                    this.addListener(WyrInputHandler.Listeners.UNIT_playerLeftClick(this));
            }
            return (Unit)super.setTeamAlignment(alignment);
        }

        public void occupyTile(RPGridTile tile) {
            if (occupiedTile == tile) return;
            if (occupiedTile != null) occupiedTile.vacate();
            occupiedTile = tile;
            occupiedTile.occupy(this);
        }

        @Override
        public void applyShader(ShaderState state) {
        if(shaderState != state) shaderState = state;
        switch(teamAlignment) {
            case PLAYER:
                switch(shaderState) {
                    case DIM:
                        shader = WyrShaders.Player.dim();
                        break;
                    case HIGHLIGHT:
                        shader = WyrShaders.Player.highlight();
                        break;
                    case STANDARD:
                        shader = WyrShaders.Player.standard();
                        break;
                }
                break;
            case ALLY:
                switch(shaderState) {
                    case DIM:
                        shader = WyrShaders.Ally.dim();
                        break;
                    case HIGHLIGHT:
                        shader = WyrShaders.Ally.highlight();
                        break;
                    case STANDARD:
                        shader = WyrShaders.Ally.standard();
                        break;
                }
                break;
            case ENEMY:
                switch(shaderState) {
                    case DIM:
                        shader = WyrShaders.Enemy.dim();
                        break;
                    case HIGHLIGHT:
                        shader = WyrShaders.Enemy.highlight();
                        break;
                    case STANDARD:
                        shader = WyrShaders.Enemy.standard();
                        break;
                }
                break;
            case STRANGER:
                switch(shaderState) {
                    case DIM:
                        shader = WyrShaders.Stranger.dim();
                        break;
                    case HIGHLIGHT:
                        shader = WyrShaders.Stranger.highlight();
                        break;
                    case STANDARD:
                        shader = WyrShaders.Stranger.standard();
                        break;
                }
                break;
            default:
                break;
        }
    }

        public boolean canMove() {
            return stats.getRollingAP() > 0;
        }

        public WyrPersonality getPersonality() {
            return personality;
        }

    }

    /**
     * Bullets hurt.
     */
//    public static class Bullet extends WyrActor {
//
//    }
}
