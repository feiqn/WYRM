package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AerialTileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.TileType;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder.teamsAreAllied;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.FREE_MOVE;

public class WyrTile implements WyrFrame {

    // refactor of LogicalTile

    protected final Array<WyrActor> actorsOnGround = new Array<>();
    protected final Array<WyrActor> actorsInAirspace = new Array<>();
    protected final Array<WyrItem> itemsOnGround = new Array<>();

    protected final TileType tileType;
    protected AerialTileType airspaceType;

    protected int groundDefenseValue = 0;
    protected int groundVisionReduction = 0;

    private int internalStateTime = -1;

    protected final int XColumn;
    protected final int YRow;

    protected boolean airspaceHarms = false;
    protected boolean groundBlocksLoS = false; // Line of sight.
    protected boolean airBlocksLoS = false;
    protected boolean highlighted = false;
//    protected boolean internalStateIsValid = false;

    protected final HashMap<MobilityType, Float> airspaceMoveCosts = new HashMap<>();
    protected final HashMap<MobilityType, Float> groundMoveCosts = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> traversability = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> groundHarms = new HashMap<>();

    protected final Array<InteractionType> staticDerivableInteractions = new Array<>();
    protected final Array<InteractionType> ephemeralDerivableInteractions = new Array<>();

    protected final HashMap<WyrActor, Array<WyrInteraction>> stateMap = new HashMap<>();

    protected final Array<WyrInteraction> stateActions = new Array<>();

//    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();
//    protected final Array<WyrInteraction> staticInteractions    = new Array<>();

    protected final RPGridHighlighter highlighter = new RPGridHighlighter(this);

    public WyrTile(TileType tileType, int xColumn, int yRow) {
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;

        staticDerivableInteractions.add(MOVE_TO);

        for(MobilityType RPGridMovementType : MobilityType.values()) {
            groundMoveCosts.put(RPGridMovementType, 1f);
            traversability.put(RPGridMovementType, true);
            groundHarms.put(RPGridMovementType, false);
        }

        switch(tileType) {
            case PLAINS:
                traversability.put(MobilityType.SAILING, false);
                groundMoveCosts.put(MobilityType.WHEELS, 1.5f);
                break;

            case ROAD:
                traversability.put(MobilityType.SAILING, false);
                groundMoveCosts.put(MobilityType.CAVALRY, .5f);
                groundMoveCosts.put(MobilityType.INFANTRY, .5f);
                break;

            case SHALLOW_WATER:
                groundMoveCosts.put(MobilityType.INFANTRY, 2f);
                groundMoveCosts.put(MobilityType.CAVALRY, 2.5f);
                groundMoveCosts.put(MobilityType.SAILING, 1.5f);
                traversability.put(MobilityType.WHEELS, false);
                break;

            case ROUGH_HILLS:
                groundMoveCosts.put(MobilityType.INFANTRY, 1.5f);
                groundMoveCosts.put(MobilityType.CAVALRY, 2f);
                groundMoveCosts.put(MobilityType.WHEELS, 2.5f);
                traversability.put(MobilityType.SAILING, false);
                break;

            case MOUNTAIN:
                groundMoveCosts.put(MobilityType.INFANTRY, 2f);
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                break;


            case LOW_WALL:
                groundBlocksLoS = true;
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                break;

            case LAVA:
                groundHarms.put(MobilityType.CAVALRY, true);
                groundHarms.put(MobilityType.INFANTRY, true);
                groundHarms.put(MobilityType.WHEELS, true);

                groundMoveCosts.put(MobilityType.INFANTRY, 1.5f);
                groundMoveCosts.put(MobilityType.WHEELS, 2f);
                groundMoveCosts.put(MobilityType.CAVALRY, 1.5f);
                groundMoveCosts.put(MobilityType.SAILING, 1.5f);
                break;

            case IMPASSIBLE_WALL:
                groundBlocksLoS = true;
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                traversability.put(MobilityType.FLYING, false);
                break;

            case FORTRESS:
                groundDefenseValue = 2;
                traversability.put(MobilityType.SAILING, false);
                groundMoveCosts.put(MobilityType.WHEELS, 1f);
                groundMoveCosts.put(MobilityType.CAVALRY, .5f);
                groundMoveCosts.put(MobilityType.INFANTRY, .5f);
                break;

            case FOREST:
                groundVisionReduction = 1;
                traversability.put(MobilityType.SAILING, false);
                groundMoveCosts.put(MobilityType.WHEELS, 2.5f);
                groundMoveCosts.put(MobilityType.INFANTRY, 1.5f);
                groundMoveCosts.put(MobilityType.CAVALRY, 2f);
                break;

            case DEEP_WATER:
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                break;

            case CORAL_REEF:
                groundDefenseValue = 1;
                groundMoveCosts.put(MobilityType.SAILING, 2f);
                traversability.put(MobilityType.WHEELS, false);
                break;
        }

    }

    public void standardize() {
//        clearEphemeralInteractables();
        clearState();
//        invalidateTileAndActors();

        for(WyrActor actor : actorsOnGround) {
            actor.standardize();
        }

//        unHighlight();

//        if(!highlighted) return;
//        highlighter.kill();
//        highlighter.remove();
//        highlighted = false;
        // I don't think this cares if it's actually there or not?
        // UPDATE: It does.
    }

    public void unHighlight() {
        if(!highlighted) return;
//        highlighter.kill();
        highlighter.remove();
        highlighted = false;
    }
    public RPGridHighlighter highlight() {
//        if(highlighted) return highlighter;
        highlighted = true;
//        highlighter.reset();
        handlers.screen().getGameStage().addActor(highlighter);
        highlighter.setPosition(XColumn, YRow);
        return highlighter;
    }
    public void pulse(boolean pulse) {
        if(!highlighted) highlight();
        highlighter.pulse(pulse);
    }

//    public void addEphemeralInteractable(WyrInteraction interaction) {
//        if(!ephemeralInteractions.contains(interaction, true)) ephemeralInteractions.add(interaction);
//    }
//    public void clearEphemeralInteractables() { ephemeralInteractions.clear(); }

    public void placeOnGround(WyrActor actor) {
        if(actorsOnGround.contains(actor, true)) return;
        if(groundIsOccupied() && actor.blocksOwnTeam()) throw new GdxRuntimeException("2 solid 2 kk");
        actorsOnGround.add(actor);
    }

    public void vacateFromGround(WyrActor actor) {
        if(actorsOnGround.contains(actor, true)) actorsOnGround.removeValue(actor, true);
    }

    public Array<WyrActor> getActorsOnGround() { return actorsOnGround; }
    public Vector2 getCoordinates() { return new Vector2(XColumn, YRow); }
    public int getXColumn() { return XColumn; }
    public int getYRow() { return  YRow; }
    public int getGroundDefenseValue() { return groundDefenseValue; }
//    public boolean hasUnit() {
//        for(WyrActor actor : actorsOnGround) {
//            if(actor.getActorType() == ActorType.ENTITY) return true;
//        }
//        return false;
//    }
//    public boolean hasProp() {
//        for(WyrActor actor : actorsOnGround) {
//            if(actor.getActorType() == ActorType.PROP) return true;
//        }
//        return false;
//    }
    public boolean groundHarms(MobilityType RPGridMovementType) { return groundHarms.get(RPGridMovementType); }
    public boolean isTraversableBy(WyrActor unit) { return this.isTraversableBy(unit.stats().getMovementType()); }
    public boolean isTraversableBy(MobilityType RPGridMovementType) { return traversability.getOrDefault(RPGridMovementType, false); }
    public boolean blocksLineOfSight() { return groundBlocksLoS; }

    public boolean groundIsOccupied() { return getCorporealActor() != null; }
    public @Null WyrActor getCorporealActor() {
        for(WyrActor actor : actorsOnGround) {
            if(actor.isCorporeal()) return actor;
        }
        return null;
    }

    public boolean groundIsObstructed(WyrActor forUnit) { return groundIsObstructed(forUnit.getTeamAlignment(), forUnit.stats().getMovementType()); }
    public boolean groundIsObstructed(@Null TeamAlignment forTeam, MobilityType forMoveType) {
        // return whether unit is blocked from walking onto or across this tile (not stopping)
        if(!isTraversableBy(forMoveType)) return true;

        for(WyrActor actor : actorsOnGround) {
            if(actor.isCorporeal() && (actor.blocksOwnTeam() || !teamsAreAllied(forTeam, actor.getTeamAlignment()))) {
                return true;
            }
        }

        return false;
    }
//    public boolean airspaceIsObstructed(TeamAlignment alignment) { return aerialOccupier.blocksOwnTeam() || aerialProp.blocksOwnTeam(); }
    public TileType getTileType() { return tileType; }
    public AerialTileType getAirspaceType() { return airspaceType; }
    public Float moveCostFor(MobilityType RPGridMovementType) { return groundMoveCosts.get(RPGridMovementType); }

//    protected Array<WyrInteraction> getEphemeralInteractions() { return ephemeralInteractions; }
//    protected Array<WyrInteraction> getStaticInteractions() {
//        final Array<WyrInteraction> returnValue = new Array<>();
//        for(WyrActor actor : actorsOnGround) {
//            returnValue.addAll(actor.getInteractions());
//        }
//        return returnValue;
//    }
//    public Array<WyrInteraction> getAllInteractions() {
//        final Array<WyrInteraction> rV = new Array<>();
//        rV.addAll(getEphemeralInteractions());
//        rV.addAll(getStaticInteractions());
//        return rV;
//    }

    public Array<WyrInteraction> getStateActions() { return stateActions; }

    public Array<InteractionType> derivableInteractionTypes(WyrActor forActor) {
        final Array<InteractionType> types = new Array<>();
        types.addAll(staticDerivableInteractions);
        types.addAll(ephemeralDerivableInteractions);

        final Array<InteractionType> rV = new Array<>();

        for(InteractionType type : types) {
            switch(type) {
                case MOVE_TO:
                case FOLLOW_PATH:
                    rV.add(MOVE_TO);
                    break;
            }
        }

        return rV;
    }

    protected Array<WyrInteraction> deriveLocalInteractions(WyrActor forActor) {
        final Array<WyrInteraction> localInteractions = new Array<>();

        for(WyrTile tile : handlers.map().tilesWithinDistanceOf(forActor.getReach(), this)) {
            final Array<WyrInteraction> tileInteractions = tile.deriveInteractions(forActor, false);

            for(WyrInteraction i : tileInteractions) {
                if(i.interactableRange() <= forActor.getReach()) localInteractions.add(i);
            }
        }

        return localInteractions;
    }

//    public Array<WyrInteraction> deriveInteractions(Array<WyrActor> forActors) {
//        if(internalStateIsValid) return stateActions;
//        stateActions.clear();
//
//        for(WyrActor actor : forActors) {
//            stateActions.addAll(deriveInteractions(actor, true));
//        }
//
//        internalStateIsValid = true;
//        return stateActions;
//    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, boolean grabLocalReachable) {

//        final Array<WyrInteraction> tileInteractions = new Array<>();

        if(handlers.input().getMovementControlMode() == FREE_MOVE) {
            stateActions.clear();
        }

        if(isTraversableBy(forActor) && !groundIsOccupied()) {
            switch(handlers.input().getMovementControlMode()) {

                case TURN_BASED:
//                    if(!GridPathfinder.currentlyAccessibleTo(forActor).tiles().containsKey(this)) break;
                    final HashMap<WyrActor, GridPathfinder.Things> stateThings = handlers.priority().stateThings();
                    if(stateThings.containsKey(forActor)) {
                        if(stateThings.get(forActor).tiles().containsKey(this)) {
//                            stateActions.add(Interactions.PathToTile(forActor, this.getCoordinates()));
                            stateActions.add(
                                Interactions.FollowPath(
                                    forActor,
                                    stateThings.get(forActor).tiles().get(this)
                                )
                            );

                        }
                    }
                    break;

                case FREE_MOVE:
                    stateActions.add(Interactions.PathToTile(forActor, getCoordinates()));
                    break;
            }

            if(grabLocalReachable) {
                stateActions.addAll(deriveLocalInteractions(forActor));
            }
        }

        for(WyrActor actor : actorsOnGround) {
            stateActions.addAll(actor.deriveInteractions(forActor));
        }

        return stateActions;
    }

//    private boolean internalStatesValid() {
//        if(!internalStateIsValid) return false;
//        for(WyrActor actor : actorsOnGround) {
//            if(!actor.ins)
//        }
//    }

    public void clearState() {
//        if(ephemeralDerivableInteractions.isEmpty()) return;
        ephemeralDerivableInteractions.clear();
//        internalStateIsValid = false;
        stateActions.clear();
    }

}
