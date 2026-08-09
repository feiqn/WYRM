package com.feiqn.wyrm.wyrefactor.assemblies.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageRoll;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ai.WyrPersonality;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.PropType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.CompassDirection;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.NaturalElement;
import org.jetbrains.annotations.NotNull;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;

/** Top-level for any actor in the WyrFrame system.
 */
public class WyrActor extends Image implements WyrFrame, Examinable {

    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;
    protected ActorType actorType = ActorType.ENTITY;
    protected WyrAnimator animator = null;
    protected WyrStats stats = null;
    protected WyrInventory inventory = new WyrInventory();
    protected WyrPersonality personality = null;

    protected Utilities.Size relativeSize = Utilities.Size.AVERAGE;

    protected final Array<WyrInteraction> staticInteractions = new Array<>();
    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader = null;

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    protected boolean isSolid = false; // solid means impassible by friendly teams.
    protected RPGridTile occupiedTile;

    private int gridX;
    private int gridY;

    public WyrActor() { this((Drawable) null); }
    public WyrActor(@Null NinePatch patch) { this(new NinePatchDrawable(patch), Scaling.stretch, Align.center); }
    public WyrActor(@Null TextureRegion region) { this(new TextureRegionDrawable(region), Scaling.stretch, Align.center); }
    public WyrActor(Texture texture) { this(new TextureRegionDrawable(new TextureRegion(texture))); }
    public WyrActor(Skin skin, String drawableName) { this(skin.getDrawable(drawableName), Scaling.stretch, Align.center);}
    public WyrActor(@Null Drawable drawable) { this(drawable, Scaling.stretch, Align.center); }
    public WyrActor(@Null Drawable drawable, Scaling scaling) { this(drawable, scaling, Align.center); }
    public WyrActor(@Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.setSize(1, 1); // just a little square

        setName("What is this?");

//        addStaticInteraction(Interactions.Examine(this));

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

    protected void setup() {}

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
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
    public void resetForNextTurn() {
        stats.tickDownConditions(true);
        stats.restoreAP();
        stats.resetSteps();
    }

    public WyrActor setTeamAlignment(TeamAlignment alignment) {
        teamAlignment = alignment;
        applyShader(ShaderState.STANDARD);
        return this;
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

    public void parseDamageRoll(DamageRoll dmg) {
        // apply damage to self,
        // apply effects to self,
        // throw labels for damage / effects to hud
        final Label damageLabel = new Label("" + dmg.getRawDamage(), WYRMGame.assets().menuLabelStyle);
        damageLabel.setFontScale(4);
        if (dmg.isNearMiss()) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg.getRawDamage());
        } else if (dmg.isCrit()) {
            damageLabel.setColor(Color.GOLD);
            damageLabel.setText("Critical Hit! " + dmg.getRawDamage());
        }

        stats.applyDamage(dmg.getRawDamage());

        handlers.hud().addActor(damageLabel);
        damageLabel.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .55f);

        // TODO: apply affects here from damage roll

        damageLabel.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3.5f),
                Actions.fadeOut(4)
            ),
            Actions.removeActor()
        ));

    }

    public boolean hasEffect(GameKit.RPG.StatusConditionID effectID) {
        return false;
    }

    protected void addStaticInteraction(WyrInteraction interaction) { staticInteractions.add(interaction); }
    public void addEphemeralInteraction(WyrInteraction interaction) { ephemeralInteractions.add(interaction); }
    public void deriveInteractions(WyrActor.Unit actingUponMe) {}
    public void deriveInteractions(WyrActor.Unit actingUponMe, GridPath pathToMe) {}
    public void clearEphemeralInteractions() { ephemeralInteractions.clear(); }

    public void face(CompassDirection direction) {
        switch(direction) {
            case N:
            case NE:
            case NW:
            case NNE:
            case NNW:
                faceNorth();
                return;
            case E:
            case ENE:
            case ESE:
                faceEast();
                return;
            case S:
            case SE:
            case SSE:
            case SW:
            case SSW:
                faceSouth();
                return;
            case W:
            case WNW:
            case WSW:
                faceWest();
                return;
        }
    }
    public void faceNorth() { getAnimator().setState(FACING_NORTH); }
    public void faceSouth() { getAnimator().setState(FACING_SOUTH); }
    public void faceEast() { getAnimator().setState(FACING_EAST); }
    public void faceWest() { getAnimator().setState(FACING_WEST); }
    public void idle() { getAnimator().setState(IDLE); }
    public void flourish() { getAnimator().setState(FLOURISH); }

    public void solidify() { isSolid = true; }
    public void unSolidify() { isSolid = false; }

    public void setAnimationState(AnimationState state) { animator.setState(state); }
    public void setPosByGrid(int x, int y) {
        gridX = x;
        gridY = y;
        this.setPosition((x + .5f) - (this.getWidth() * .5f), y);
    }

    public Array<WyrInteraction> getInteractions() {
        final Array<WyrInteraction> rV = new Array<>();
        rV.addAll(ephemeralInteractions);
        rV.addAll(staticInteractions);
        return rV;
    }
    public @Null WyrAnimator getAnimator() { return animator; }
    public @Null WyrInventory getInventory() { return inventory; }
    public ActorType getActorType() { return actorType; }

    public int getReach() {
        return 1;// todo, stats.weapon.reach
    }
    public int getMaxHP() { return stats.getMaxHP(); }
    public int getRollingHP() { return stats.getRollingHP(); }
    public int getRollingAP() { return stats.getRollingAP(); }
    public @NotNull WyrStats stats() { return getStats(); }
    public @NotNull WyrStats getStats() { return (stats == null ? new WyrStats(this, RPGClassID.OBJECT) : stats); }

    public @Null WyrPersonality getPersonality() { return personality; }
    public boolean isSolid() { return isSolid; }
    public RPGridTile getOccupiedTile() { return occupiedTile; }
    public Vector2 getGridPosition() { return new Vector2(gridX, gridY); }
    public int gridX() { return gridX; }
    public int gridY() { return gridY; }

    /**
     * Props and tiles make up the world.
     */
    public static class Prop extends WyrActor {

        protected final PropType propType;
        protected boolean aerial = false;

        protected Array<NaturalElement> reactiveTo = new Array<>();

        protected Material material = null;

        public Prop(PropType type, TextureRegion region) {
            super(region);
            propType = type;
            actorType = ActorType.PROP;
            animator = new WyrAnimator(this);
            stats = new WyrStats(this, RPGClassID.OBJECT);
            inventory = new WyrInventory();
            setup();
        }

        public void occupyTile(RPGridTile tile) {
            if (occupiedTile == tile) return;
            occupiedTile = tile;
            occupiedTile.setProp(this);
        }

        public void occupyAirspace(RPGridTile tile) {
            if (occupiedTile == tile) return;
            occupiedTile = tile;
            occupiedTile.setAerialProp(this);
        }

        public boolean isAerial() {
            return aerial;
        }

        public PropType getPropType() {
            return propType;
        }

        public void reactTo(NaturalElement element) {}

        public static class LockableProp extends WyrActor {

            protected boolean isLocked = false;
            protected boolean isOpen = false;
            protected final String keyID;

            public LockableProp(PropType type, TextureRegion region, String keyCode) {
                super(region);
                keyID = keyCode;
            }

            public void lock() {
                isLocked = true;
                isOpen = false;
                // todo: visually update prop and set obstructions
            }

            public void unlock() { isLocked = false; }

            public boolean tryToOpen() {
                if(isOpen) return true;
                if(isLocked) return false;
                isOpen = true;
                return true;
            }

            public boolean tryKey(String keyCode) {
                if(keyID.equals(keyCode)) {
                    unlock();
                    return true;
                }
                return false;
            }

        }

    }

    /**
     * Units live in the world.
     */
    public static class Unit extends WyrActor {

        protected final Character.Name charID;

        private String examineText = "A stranger.";

        public Unit(Character.Name id, RPGClassID classID) {
            super(handlers.assets().soldierTexture);
            actorType = ActorType.ENTITY;
            charID = id;
            stats = new WyrStats(this, classID);
            animator = new WyrAnimator(this);
            animator.generateAnimations();
            idle();
            personality = new WyrPersonality(PersonalityType.STILL);
            setName(id.toString());
            setup();
        }

        @Override
        public String getExamine() {
            return examineText;
        }

        @Override
        public boolean hasEffect(GameKit.RPG.StatusConditionID effectID) {
            for(WyrStatusCondition c : inventory.getAllGearEffects()) {
                if(c.getConditionID() == effectID) return true;
            }
            return super.hasEffect(effectID);
        }

        public WyrActor.Unit setExamine(String examineText) {
            this.examineText = examineText;
            return this;
        }

        public WyrActor.Unit setPersonality(WyrPersonality personality) {
            this.personality = personality;
            return this;
        }

        public WyrActor.Unit ai(PersonalityType type) {
            return setPersonalityType(type);
        }

        public WyrActor.Unit setPersonalityType(PersonalityType type) {
            personality.setPersonalityType(type);
            return this;
        }

        public void kill() {
            handlers.register().removeFromTurnOrder(this);
            occupiedTile.vacate();
            Campaign.killCharacter(charID);
            handlers.cutscenes().checkDeathTriggers(charID);
            addAction(Actions.sequence(
                Actions.fadeOut(1),
                Actions.removeActor()
            ));
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

        public boolean canMoveOrAct() {
            return stats.canAct() || stats.canStep();
        }

        public WyrPersonality getPersonality() {
            return personality;
        }

    }

}
