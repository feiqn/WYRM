package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.Interactions;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AerialTileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.TileType;
import org.jetbrains.annotations.NotNull;

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

        unHighlight();

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
        highlighter.kill();
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

    public Array<WyrInteraction> getStateActions() {
//        final Array<WyrInteraction> ephemeralState = new Array<>();
//        ephemeralState.addAll(stateActions);

//        for(WyrTile tile : GridPathfinder.tilesTouchableFromTile(this, .getReach).tiles().keySet()) {
//                    for(WyrInteraction interaction : tile.deriveInteractions(forActor, this, pathToAction)) {
//                        if(isUnique(interaction)) newState.add(interaction);
//                    }
//                 //   stateActions.addAll(tile.deriveInteractions(forActor, this, pathToAction));
//                }

//        return ephemeralState;
         return stateActions;
    }

//    public Array<InteractionType> derivableInteractionTypes(WyrActor forActor) {
//        final Array<InteractionType> types = new Array<>();
//        types.addAll(staticDerivableInteractions);
//        types.addAll(ephemeralDerivableInteractions);
//
//        final Array<InteractionType> rV = new Array<>();
//
//        for(InteractionType type : types) {
//            switch(type) {
//                case MOVE_TO:
//                case FOLLOW_PATH:
//                    rV.add(MOVE_TO);
//                    break;
//            }
//        }
//
//        return rV;
//    }
//    public Array<WyrInteraction> deriveTouchableFromTile(WyrActor forActor, WyrTile fromTile, @Null GridPath pathToFromTile) {
//        // derives actions which will be duplicated across this tile and fromTile, where the actor
//        // moves to fromTile but acts on this tile.
//
//        return null;
//    }

//    protected Array<WyrInteraction> deriveLocalInteractions(WyrActor forActor) {
//        final Array<WyrInteraction> localInteractions = new Array<>();
//
//        for(WyrTile tile : handlers.map().tilesWithinDistanceOf(forActor.getReach(), this)) {
//            final Array<WyrInteraction> tileInteractions = tile.deriveInteractions(forActor, false);
//
//            for(WyrInteraction i : tileInteractions) {
//                if(i.interactableRange() <= forActor.getReach()) localInteractions.add(i);
//            }
//        }
//
//        return localInteractions;
//    }

    public Array<WyrInteraction> deriveInteractions(@NotNull Array<WyrActor> forActors) {
        stateActions.clear();

        for(WyrActor actor : forActors) {
            deriveInteractions(actor);
        }


        return stateActions;
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor) {
        return deriveInteractions(forActor, this); // What can this actor do while standing on this tile?
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, WyrTile fromTile) {
        return deriveInteractions(forActor, fromTile, null,null);
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, WyrTile fromTile, Array<WyrTile> reachableTilesFromActingTile) {
        return deriveInteractions(forActor, fromTile, null, reachableTilesFromActingTile);
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, WyrTile fromTile, GridPath pathToAction) {
        return deriveInteractions(forActor,fromTile, pathToAction, null);
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, GridPath pathToAction, Array<WyrTile> reachableTilesFromActingTile) {
        return deriveInteractions(forActor, pathToAction.lastTile(), pathToAction, reachableTilesFromActingTile);
    }

    public Array<WyrInteraction> deriveInteractions(WyrActor forActor, WyrTile fromTile, @Null GridPath pathToAction, @Null Array<WyrTile> reachableTilesFromTile) {

        final Array<WyrInteraction> newState = new Array<>();

        if(handlers.input().getMovementControlMode() == FREE_MOVE) {
            stateActions.clear();
        }

        if(fromTile == null) {
            if(pathToAction != null) {
                fromTile = pathToAction.lastTile();
            } else {
                fromTile = this;
            }
        }

        final int distanceFromOrigin = handlers.map().distanceBetweenTiles(fromTile.getCoordinates(), getCoordinates());

        if(fromTile == this && isTraversableBy(forActor) && !groundIsOccupied()) {
            if(pathToAction == null) {
//
//                switch(handlers.input().getMovementControlMode()) {
//
//                    case TURN_BASED:
////                    if(!GridPathfinder.currentlyAccessibleTo(forActor).tiles().containsKey(this)) break;
//                        final HashMap<WyrActor, GridPathfinder.Things> stateThings = handlers.priority().stateThings();
//                        if(stateThings.containsKey(forActor)) {
//                            if(stateThings.get(forActor).tiles().containsKey(this)) {
////                            stateActions.add(Interactions.PathToTile(forActor, this.getCoordinates()));
//                                stateActions.add(
//                                    Interactions.FollowPath(
//                                        forActor,
//                                        stateThings.get(forActor).tiles().get(this)
//                                    )
//                                );
//
//                            }
//                        }
//                        break;
//
//                    case FREE_MOVE:
                newState.add(Interactions.PathToTile(forActor, getCoordinates()));
//                        break;
//                }
            } else {
                newState.add(Interactions.FollowPath(forActor, pathToAction));
            }



//            if(fromTile == this) {
                for(WyrTile tile : GridPathfinder.tilesTouchableFromTile(this, forActor).tiles().keySet()) {
                    for(WyrInteraction interaction : tile.deriveInteractions(forActor, this, pathToAction)) {
                        if(isUnique(interaction)) newState.add(interaction);
                    }
                 //   stateActions.addAll(tile.deriveInteractions(forActor, this, pathToAction));
                }
//            }

        }

        for(WyrActor actor : actorsOnGround) {
            for(WyrInteraction interaction : actor.deriveInteractions(forActor)) {
                if(isUnique(interaction)) newState.add(interaction);
            }
//            stateActions.addAll(actor.deriveInteractions(forActor));
        }

        for(WyrInteraction i : newState) {
            if(i.interactableRange() <= distanceFromOrigin) {
                stateActions.add(i);
            }
        }

        if(fromTile != this) return newState;

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
        // TODO: abstract to StateInteraction interface
        for(WyrInteraction i : stateActions) {
            if(isSimilarEnough(i, interaction)) {
                return false;
            }
        }
        return true;
    }

    public void clearState() {
        ephemeralDerivableInteractions.clear();
        stateActions.clear();
    }

}
