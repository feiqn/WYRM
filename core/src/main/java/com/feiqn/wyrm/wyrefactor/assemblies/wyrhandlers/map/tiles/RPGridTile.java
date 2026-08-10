package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AerialTileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.TileType;

import java.util.HashMap;
import java.util.Objects;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder.teamsAreAllied;

public class RPGridTile implements WyrFrame {

    // refactor of LogicalTile

    protected final Array<WyrActor> actorsOnGround = new Array<>();
    protected final Array<WyrActor> actorsInAirspace = new Array<>();
    protected final Array<WyrItem> itemsOnGround = new Array<>();

    protected final TileType tileType;
    protected AerialTileType airspaceType;

    protected int groundDefenseValue = 0;
    protected int groundVisionReduction = 0;

    protected final int XColumn;
    protected final int YRow;

    protected boolean airspaceHarms = false;
    protected boolean groundBlocksLoS = false; // Line of sight.
    protected boolean airBlocksLoS = false;
    protected boolean highlighted = false;

    protected final HashMap<MobilityType, Float> airspaceMoveCosts = new HashMap<>();
    protected final HashMap<MobilityType, Float> groundMoveCosts = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> traversability = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> groundHarms = new HashMap<>();

    protected final Array<InteractionType> staticDerivableInteractions = new Array<>();
    protected final Array<InteractionType> ephemeralDerivableInteractions = new Array<>();

    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();
    protected final Array<WyrInteraction> staticInteractions    = new Array<>();

    protected RPGridHighlighter highlighter;

    public RPGridTile(TileType tileType, int xColumn, int yRow) {
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;

        staticDerivableInteractions.add(InteractionType.MOVE_TO);

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
        clearEphemeralInteractables();
        if(!highlighted) return;
        highlighter.kill();
        highlighted = false;
        // I don't think this cares if it's actually there or not?
        // UPDATE: It does.
    }

    public void highlight() {
        if(highlighted) return;
        highlighted = true;
        highlighter = new RPGridHighlighter(this);
        Objects.requireNonNull(handlers.screen()).getGameStage().addActor(highlighter);
        highlighter.setPosition(XColumn, YRow);
    }
    public void shadeHighlight(ShaderState state, TeamAlignment teamAlignment) {
        if(!highlighted) return;
        if(teamAlignment == TeamAlignment.ENEMY) highlighter.setColor(Color.RED);
//        highlighter.shade(state, teamAlignment);
    }

    public void hideHighlight() {
        if(!highlighted) return;
        highlighter.setVisible(false);
    }
    public void unhideHighlight() {
        if(!highlighted) return;
        highlighter.setVisible(true);
    }
    public void pulse(boolean pulse) {
        if(!highlighted) return;
        highlighter.pulse(pulse);
    }

    public void addEphemeralInteractable(WyrInteraction interaction) {
        if(!ephemeralInteractions.contains(interaction, true)) ephemeralInteractions.add(interaction);
    }
    public void clearEphemeralInteractables() { ephemeralInteractions.clear(); }

    public void placeOnGround(WyrActor actor) {
        if(actorsOnGround.contains(actor, true)) return;
        if(groundIsOccupied() && actor.blocksOwnTeam()) throw new GdxRuntimeException("2 solid 2 kk");
        actorsOnGround.add(actor);
    }

    public void vacateGround(WyrActor actor) {
        if(actorsOnGround.contains(actor, true)) actorsOnGround.removeValue(actor, true);
    }

    public Vector2 getCoordinates() { return new Vector2(XColumn, YRow); }
    public int getXColumn() { return XColumn; }
    public int getYRow() { return  YRow; }
    public int getGroundDefenseValue() { return groundDefenseValue; }
    public boolean hasUnit() {
        for(WyrActor actor : actorsOnGround) {
            if(actor.getActorType() == ActorType.ENTITY) return true;
        }
        return false;
    }
    public boolean hasProp() {
        for(WyrActor actor : actorsOnGround) {
            if(actor.getActorType() == ActorType.PROP) return true;
        }
        return false;
    }
    public boolean groundHarms(MobilityType RPGridMovementType) { return groundHarms.get(RPGridMovementType); }
    public boolean isTraversableBy(WyrActor unit) { return this.isTraversableBy(unit.stats().getMovementType()); }
    public boolean isTraversableBy(MobilityType RPGridMovementType) { return traversability.get(RPGridMovementType); }
    public boolean blocksLineOfSight() { return groundBlocksLoS; }
    public boolean groundIsOccupied() {
        for(WyrActor actor : actorsOnGround) {
            if(actor.getActorType() == ActorType.ENTITY || actor.blocksOwnTeam()) return true;
        }
        return false;
    }
    public boolean groundIsObstructed(WyrActor forUnit) { return groundIsObstructed(forUnit.getTeamAlignment(), forUnit.stats().getMovementType()); }
    public boolean groundIsObstructed(@Null TeamAlignment team, MobilityType moveType) {
        // return whether unit can currently walk onto or across this tile
        if(!isTraversableBy(moveType)) return true;

        for(WyrActor actor : actorsOnGround) {
            if(actor.blocksOwnTeam() || !teamsAreAllied(team, actor.getTeamAlignment())) {
                return true;
            }
        }

        return false;
    }
//    public boolean airspaceIsObstructed(TeamAlignment alignment) { return aerialOccupier.blocksOwnTeam() || aerialProp.blocksOwnTeam(); }
    public TileType getTileType() { return tileType; }
    public AerialTileType getAirspaceType() { return airspaceType; }
    public Float moveCostFor(MobilityType RPGridMovementType) { return groundMoveCosts.get(RPGridMovementType); }
    protected Array<WyrInteraction> getEphemeralInteractions() { return ephemeralInteractions; }
    protected Array<WyrInteraction> getStaticInteractions() {
        final Array<WyrInteraction> returnValue = new Array<>();
//        if(hasUnit()) returnValue.addAll(occupier.getInteractions());
//        if(hasProp()) returnValue.addAll(prop.getInteractions());
        return returnValue;
    }
    public Array<WyrInteraction> getAllInteractions() {
        final Array<WyrInteraction> rV = new Array<>();
        rV.addAll(getEphemeralInteractions());
        rV.addAll(getStaticInteractions());
        return rV;
    }

}
