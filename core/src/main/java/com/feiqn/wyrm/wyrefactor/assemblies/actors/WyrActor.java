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
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.PropType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.CompassDirection;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.NaturalElement;
import org.jetbrains.annotations.NotNull;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;

/** Top-level for any actor in the WyrFrame system.
 */
public class WyrActor extends Image implements WyrFrame, Examinable {

    protected WyrPersonality personality = new WyrPersonality(PersonalityType.STILL);
    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;
    protected ActorType actorType = ActorType.ENTITY;
    protected WyrInventory inventory = new WyrInventory();
    protected WyrAnimator animator = new WyrAnimator(this);
    protected WyrStats stats = null;

    protected Utilities.Size relativeSize = Utilities.Size.AVERAGE;

    protected final Array<InteractionType> staticDerivableInteractions = new Array<>();
    protected final Array<InteractionType> ephemeralDerivableInteractions = new Array<>();

    protected final Array<WyrInteraction> staticInteractions = new Array<>();
    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader = null;

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;
    protected boolean blocksOwnTeam = false; // solid means impassible by friendly teams.

    private float hoverTime = 0;

    protected RPGridTile occupiedTile;

    private int gridX;
    private int gridY;

    private String examineText = "A person, place, or thing.";

    public WyrActor() { this((Drawable) null); }
    public WyrActor(@Null NinePatch patch) { this(new NinePatchDrawable(patch), Scaling.stretch, Align.center); }
    public WyrActor(Texture texture) { this(new TextureRegionDrawable(new TextureRegion(texture))); }
    public WyrActor(Skin skin, String drawableName) { this(skin.getDrawable(drawableName), Scaling.stretch, Align.center);}
    public WyrActor(@Null Drawable drawable) { this(drawable, Scaling.stretch, Align.center); }
    public WyrActor(@Null Drawable drawable, Scaling scaling) { this(drawable, scaling, Align.center); }
    public WyrActor(@Null TextureRegion region) { this(new TextureRegionDrawable(region), Scaling.stretch, Align.center); }
    public WyrActor(@Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
    }

    public WyrActor(String uniqueID, RPGClassID rpgClass) {
        this(handlers.assets().mercenaryTexture);

        stats = new WyrStats(this, rpgClass);

        this.setSize(1, 1); // just a little square

        setName(uniqueID);

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
        // todo: stats, etc.
        for(WyrStatusCondition c : inventory.getAllGearEffects()) {
            if(c.getConditionID() == effectID) return true;
        }
        return false;
    }

    public void placeOnGroundAt(RPGridTile tile) {
        if(occupiedTile == tile) return;
        if(occupiedTile.groundIsOccupied() && this.blocksOwnTeam) throw new GdxRuntimeException("2 solid 2 kk");
        occupiedTile = tile;
        occupiedTile.placeOnGround(this);
    }

    public WyrActor setTeamAlignment(TeamAlignment alignment) {
        teamAlignment = alignment;
        applyShader(ShaderState.STANDARD);

        if(actorType != ActorType.PROP) {
            switch(alignment) {
                case PLAYER:
                    this.addListener(WyrInputHandler.Listeners.UNIT_playerLeftClick(this));
            }
        }

        return this;

    }

    public void deriveInteractions(WyrActor actingUponMe) {}

    public void clearEphemeralInteractions() { ephemeralInteractions.clear(); }

    protected void hoverOver() { hoverActivated = true; }
    protected void unHover() { hoverActivated = false; }

    protected void addStaticInteraction(WyrInteraction interaction) { staticInteractions.add(interaction); }
    public void addEphemeralInteraction(WyrInteraction interaction) { ephemeralInteractions.add(interaction); }

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

    public void solidify() { blocksOwnTeam = true; }
    public void unSolidify() { blocksOwnTeam = false; }

    public void setAnimationState(AnimationState state) { animator.setState(state); }
    public void setPosByGrid(int x, int y) {
        gridX = x;
        gridY = y;
        this.setPosition((x + .5f) - (this.getWidth() * .5f), y);
    }
    public WyrActor setExamine(String examineText) {
        this.examineText = examineText;
        return this;
    }
    public WyrActor ai(PersonalityType type) {
        return setPersonalityType(type);
    }
    public WyrActor setPersonality(WyrPersonality personality) {
        this.personality = personality;
        return this;
    }
    public WyrActor setPersonalityType(PersonalityType type) {
        personality.setPersonalityType(type);
        return this;
    }

    public int getReach() {
        return 1;// todo, stats.weapon.reach
    }

    public Array<WyrInteraction> getInteractions() {
        final Array<WyrInteraction> rV = new Array<>();
        rV.addAll(ephemeralInteractions);
        rV.addAll(staticInteractions);
        return rV;
    }

    public boolean canMoveOrAct() { return stats.canAct() || stats.canStep(); }
    public @Null WyrAnimator getAnimator() { return animator; }
    public @Null WyrInventory getInventory() { return inventory; }
    public ActorType getActorType() { return actorType; }
    public int getMaxHP() { return stats.getMaxHP(); }
    public int getRollingHP() { return stats.getRollingHP(); }
    public int getRollingAP() { return stats.getRollingAP(); }
    public @NotNull WyrStats stats() { return getStats(); }
    public @NotNull WyrStats getStats() { return (stats == null ? new WyrStats(this, RPGClassID.OBJECT) : stats); }
    public @Null WyrPersonality getPersonality() { return personality; }
    public boolean blocksOwnTeam() { return blocksOwnTeam; }
    public RPGridTile getOccupiedTile() { return occupiedTile; }
    public Vector2 getGridPosition() { return new Vector2(gridX, gridY); }
    public int gridX() { return gridX; }
    public int gridY() { return gridY; }
    @Override
    public String getExamine() { return examineText; }
    public TeamAlignment getTeamAlignment() { return teamAlignment; }


    /**
     * Props and tiles make up the world.
     */
    public static class Prop extends WyrActor {

        protected final PropType propType;
        protected Array<NaturalElement> reactiveTo = new Array<>();
        protected Material material = null;

        public Prop(PropType type, @Null TextureRegion region) {
            super(type.toString(), RPGClassID.OBJECT);
            this.setDrawable(new TextureRegionDrawable(region));

            propType = type;
            actorType = ActorType.PROP;
            blocksOwnTeam = true;

            setName(type.toString());
            setup();
        }

        public PropType getPropType() {
            return propType;
        }

        public void reactTo(NaturalElement element) {}

        public static class LockableProp extends Prop {

            protected boolean isLocked = false;
            protected boolean isOpen = false;
            protected final String keyID;

            public LockableProp(PropType type, TextureRegion region, String keyCode) {
                super(type, region);
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

        public Unit(Character.Name id, RPGClassID classID) {
            super(id.toString(), classID);
            charID = id;

            animator.generateAnimations();
            idle();

            setup();
        }

        public void kill() {
            handlers.register().removeFromTurnOrder(this);
            occupiedTile.vacateGround(this);
            Campaign.killCharacter(charID);
            handlers.cutscenes().checkDeathTriggers(charID);
            addAction(Actions.sequence(
                Actions.fadeOut(1),
                Actions.removeActor()
            ));
        }

        public Character.Name getCharacterID() { return charID; }

    }

}
