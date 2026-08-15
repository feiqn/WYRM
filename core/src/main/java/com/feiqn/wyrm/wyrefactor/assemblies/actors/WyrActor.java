package com.feiqn.wyrm.wyrefactor.assemblies.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageRoll;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WyrShaders;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ai.WyrPersonality;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrInventory;
import com.feiqn.wyrm.wyrefactor.helpers.Material;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Examinable;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.PropType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.RPGClass.RPGClassID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatusConditionID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.CompassDirection;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.NaturalElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder.teamsAreAllied;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.*;

/** Top-level for any actor in the WyrFrame system.
 */
public class WyrActor extends Image implements WyrFrame, Examinable {

    protected WyrPersonality personality = new WyrPersonality(PersonalityType.STILL);
    protected TeamAlignment teamAlignment = TeamAlignment.PLAYER;
    protected ActorType actorType = ActorType.ENTITY;
    protected WyrInventory inventory = new WyrInventory();
    protected WyrAnimator animator = new WyrAnimator(this);
    protected final WyrStats stats;

    protected Utilities.Size relativeSize = Utilities.Size.AVERAGE;

    protected final Array<InteractionType> staticDerivableInteractions = new Array<>();
    protected final Array<InteractionType> ephemeralDerivableInteractions = new Array<>();

//    protected final Array<WyrInteraction> staticInteractions = new Array<>();
//    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();
    protected final Array<WyrInteraction> stateActions = new Array<>();

    protected final HashMap<WyrActor, Array<WyrInteraction>> stateMap = new HashMap<>();

    protected ShaderState shaderState = ShaderState.STANDARD;
    protected ShaderProgram shader = null;

//    private boolean internalStateIsValid = false;
    private boolean hoveredOver = false;
    private boolean hoverActivated = false;
    protected boolean isCorporeal = true; // non-corporal actors can be stepped on or over regardless of team alignment, like objective prop tiles.
    protected boolean blocksOwnTeam = false;
    protected boolean spotlighting = false;

    private float hoverTime = 0;

    protected WyrTile occupiedTile;

    private int gridX;
    private int gridY;

    private String examineText = "A person, place, or thing.";

    public WyrActor(String uniqueID, RPGClassID rpgClass) {
        super(handlers.assets().mercenaryTexture);
        this.setSize(1, 1); // just a little square

        stats = new WyrStats(this, rpgClass);

        setName(uniqueID);

        staticDerivableInteractions.add(InteractionType.WAIT);
        staticDerivableInteractions.add(InteractionType.EXAMINE);
        staticDerivableInteractions.add(InteractionType.ATTACK);

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
        if(!hoveredOver && hoverTime > 0) { // tick down
            hoverTime -= delta;
            if(hoverTime <= 0) {
                hoverTime = 0;
                unHover();
            }
        } else if(!hoverActivated && hoveredOver && hoverTime < .1f) { // tick up
            hoverTime += delta;
            if(hoverTime >= .1f) {
                hoverOver();
            }
        }

        if(stats.getRPGClassID() != RPGClassID.OBJECT) getAnimator().update();
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(shader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    public void standardize() {
        clearState();
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
        standardize();
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

    public boolean hasEffect(StatusConditionID effectID) {
        // todo: stats, etc.
        for(WyrStatusCondition c : inventory.getAllGearEffects()) {
            if(c.getConditionID() == effectID) return true;
        }
        return false;
    }

    public void placeOnGroundAt(@Null WyrTile tile) {
        if(tile == null) return;
        if(occupiedTile != null) {
            if(occupiedTile == tile) return;
            if(occupiedTile.groundIsOccupied() && this.isCorporeal && occupiedTile.getCorporealActor() != this) throw new GdxRuntimeException("2 solid 2 kk");
        }
        this.occupiedTile = tile;
        occupiedTile.placeOnGround(this);
    }

    public void kill() {
        occupiedTile.vacateFromGround(this);
        addAction(Actions.sequence(
            Actions.fadeOut(1),
            Actions.removeActor()
        ));
    }

    public WyrActor setTeamAlignment(TeamAlignment alignment) {
        teamAlignment = alignment;
        applyShader(ShaderState.STANDARD);

        if(actorType == ActorType.ENTITY) {
            switch(alignment) {
                case PLAYER:
                    this.addListener(WyrInputHandler.Listeners.UNIT_playerLeftClick(this));
            }
        }

        return this;
    }

//    public void clearEphemeralInteractions() { ephemeralInteractions.clear(); clearDerivableInteractions(); }

    protected void hoverOver() {
        if(hoverActivated) return;
        hoverActivated = true;
        Gdx.app.log("actor", "hover");
    }
    protected void unHover() {
        if(!hoverActivated) return;
        hoverActivated = false;
        Gdx.app.log("actor", "unHover");
    }

//    public void addEphemeralInteraction(WyrInteraction interaction) { ephemeralInteractions.add(interaction); }

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

//    public Array<WyrInteraction> getInteractions() {
//        final Array<WyrInteraction> rV = new Array<>();
//        rV.addAll(ephemeralInteractions);
//        rV.addAll(staticInteractions);
//        return rV;
//    }

    public void clearState() {
//        internalStateIsValid = false;
        ephemeralDerivableInteractions.clear();
        stateMap.clear();
        stateActions.clear();
    }

    public void addDerivableInteraction(InteractionType interactionType) {
        if(ephemeralDerivableInteractions.contains(interactionType, true)) return;
        ephemeralDerivableInteractions.add(interactionType);
        clearState();
    }

//    public Array<InteractionType> derivableInteractionTypes(WyrActor forActor) {
//        final Array<InteractionType> allTypes = new Array<>();
//        allTypes.addAll(staticDerivableInteractions);
//        allTypes.addAll(ephemeralDerivableInteractions);
//
//        final Array<InteractionType> rV = new Array<>();
//
//        for(InteractionType type : allTypes) {
//            switch(type) {
//
//                case WAIT:
//                    rV.add(InteractionType.WAIT);
//                    break;
//
//                case TALK:
//                    // add if this actor has cutscene loaded in handler
//                    break;
//
//                case ATTACK:
//                    if(teamsAreAllied(forActor.getTeamAlignment(), teamAlignment)) break;
//                    if(!forActor.stats.canAct()) break;
//                    rV.add(type);
//                    break;
//
//                case EXAMINE:
//                    rV.add(type);
//                    break;
//
//                case MOUNT:
//                    if(!forActor.stats.canAct()) break;
//                    // compare sizes, teams, and strength if not allied
//
//                case CALL_MOUNT:
//                    // check if unit owns mount
//
//                case ABILITY_USE:
//                    // check known abilities
//
////                case PROP_AIM:
////                case PROP_UNLOCK:
////                case PROP_ESCAPE:
////                case PROP_SEIZE:
////                case PROP_OPEN:
////                case PROP_CLOSE:
////                case PROP_LOCK:
////                case PROP_LOOT:
////                case PROP_PILOT:
//
//                default:
//                    break;
//            }
//        }
//        return rV;
//    }

    public Array<WyrInteraction> deriveInteractions(WyrActor actingOnMe, WyrTile fromTile, @Null GridPath pathToAction) {
//        if(internalStateIsValid) return stateActions;

//        final GridPathfinder.Things currentlyAccessible = GridPathfinder.currentlyAccessibleTo(actingOnMe);

        switch(handlers.input().getMovementControlMode()) {
            case FREE_MOVE:
                stateActions.clear();
                break;

            case TURN_BASED:
//                if(!currentlyAccessible.actors().contains(this, true)) {
//                    return new Array<>();
//                }
        }

        final Array<InteractionType> types = new Array<>();
        types.addAll(staticDerivableInteractions);
        types.addAll(ephemeralDerivableInteractions);

        for(InteractionType type : types) {
            switch(type) {

                case WAIT:
                    if(Objects.equals(actingOnMe.getName(), getName())) {
                        stateActions.add(Interactions.Wait(this));
                    }
                    break;

                case TALK:
                    // check for talk trigger cutscenes loaded in handler,
                    // then check if this unit is associated with any.
                    // if so, generate a talk interaction to start the cutscene.
                    if(!actingOnMe.stats.canAct()) break;
                    break;

                case ATTACK:
                    Gdx.app.log("derive", actingOnMe.getName() + " attack " + getName() +"?" );
                    if(teamsAreAllied(actingOnMe.getTeamAlignment(), getTeamAlignment())) break;
                    if(!actingOnMe.stats.canAct()) break;
                    if(handlers.input().getMovementControlMode() == TURN_BASED) {
//                        if(!currentlyAccessible.actors().contains(this, true)) break;
                        stateActions.add(Interactions.Attack(actingOnMe, this).setInteractableDistance(actingOnMe.getReach()));
//                        stateActions.add(Interactions.Attack(actingOnMe, this).setPath(currentlyAccessible.pathTo(this)));
                    } else {
                        stateActions.add(Interactions.Attack(actingOnMe, this));
                    }
                    break;

                case EXAMINE:
                    stateActions.add(Interactions.Examine(this));
                    break;

                case MOUNT:
                    if(!actingOnMe.stats.canAct()) break;
                    // compare sizes, teams, and strength if not allied

                case CALL_MOUNT:
                    // check if unit owns mount

                case ABILITY_USE:
                    // check known abilities

//                case PROP_AIM:
//                case PROP_UNLOCK:
//                case PROP_ESCAPE:
//                case PROP_SEIZE:
//                case PROP_OPEN:
//                case PROP_CLOSE:
//                case PROP_LOCK:
//                case PROP_LOOT:
//                case PROP_PILOT:

                default:
                    break;
            }
        }

        return stateActions;
    }

    private boolean isSimilarEnough(WyrInteraction i1, WyrInteraction i2) {
        // TODO: abstract this to a submethod of interaction
        if(i1.getInteractType() != i2.getInteractType()) return false;
        if(i1.getSubject() != i2.getSubject()) return false;
        if(i1.hasObject()) {
            if(!i2.hasObject()) return false;
            if(i1.getSubject() != i2.getSubject()) return false;
        } else if(i2.hasSubject()) {
            return false;
        }
        if(i1.hasPrepositional()) {
            if(!i2.hasPrepositional()) return false;
            return i1.getPrepositional() == i2.getPrepositional();
        }
        return true;
    }

    private boolean isUnique(WyrInteraction interaction) {
        for(WyrInteraction i : stateActions) {
            if(isSimilarEnough(i, interaction)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCorporeal() { return  isCorporeal; }

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

    public boolean blocksOwnTeam() { return blocksOwnTeam && isCorporeal; }

    public WyrTile getOccupiedTile() { return occupiedTile; }

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
        protected Array<NaturalElement> reactiveTo = new Array<>(); // TODO: fold reactivity into Material
        protected Material material = null;

        public Prop(PropType type, @Null TextureRegion region) {
            super(type.toString(), RPGClassID.OBJECT);
            this.setDrawable(new TextureRegionDrawable(region));

            propType = type;
            actorType = ActorType.PROP;
            blocksOwnTeam = true;
            setTeamAlignment(TeamAlignment.APOLITICAL_BYSTANDER);

            setName(type.toString());
            setup();
        }

        public PropType getPropType() { return propType; }

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

            staticDerivableInteractions.add(InteractionType.TALK);

            idle();
            setup();
        }

        @Override
        protected void hoverOver() {

            super.hoverOver();
//            if(!canMoveOrAct()) return;
//            spotlighting = true;
//            handlers.map().clearAllHighlights();
//            for(WyrTile t : GridPathfinder.currentlyAccessibleTo(this).tiles().keySet()) {
//                switch(teamAlignment) {
//                    case ENEMY:
//                        t.highlight().red();
//                        break;
//                    default:
//                        t.highlight();
//                        break;
//                }
//            }

        }

        @Override
        protected void unHover() {
            super.unHover();
//            if(spotlighting) {
//                handlers.map().clearAllHighlights();
//                handlers.priority().parsePriority();
//            }
//            spotlighting = false;
        }


        @Override
        public void kill() {
            handlers.register().removeFromTurnOrder(this);
            Campaign.killCharacter(charID);
            handlers.cutscenes().checkDeathTriggers(charID);
            super.kill();
        }

        @Override
        public WyrActor.Unit ai(PersonalityType type) {
            personality.setPersonalityType(type);
            return this;
        }

        public Character.Name getCharacterID() { return charID; }

    }

}
