package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.MobilityType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.TileType;

import java.util.HashMap;
import java.util.Objects;

public class RPGridTile implements Wyr {

    // refactor of LogicalTile

    public enum Obstruction {
        UNIT,
        PROP,
        TERRAIN,
    }

    protected final TileType tileType;

    protected int defenseValue    = 0;
    protected int visionReduction = 0;

    protected final int XColumn;
    protected final int YRow;

    protected boolean blocksLineOfSight = false;
    protected boolean isSolid           = false;
    protected boolean airspaceIsSolid   = false;
    protected boolean airspaceHarms     = false;
    protected boolean highlighted       = false;

    protected final HashMap<MobilityType, Float>   aerialMovementCosts = new HashMap<>();
    protected final HashMap<MobilityType, Float>   movementCosts       = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> traversability      = new HashMap<>();
    protected final HashMap<MobilityType, Boolean> groundHarms         = new HashMap<>();

    protected final Array<WyrInteraction> ephemeralInteractions = new Array<>();
    protected final Array<WyrInteraction> staticInteractions    = new Array<>();

    protected WyrActor.Unit occupier = null;
    protected WyrActor.Prop prop     = null;

    protected WyrActor.Unit aerialOccupier = null;
    protected WyrActor.Prop aerialProp     = null;

    protected RPGridHighlighter highlighter;

    public RPGridTile(TileType tileType, int xColumn, int yRow) {
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;

        for(MobilityType RPGridMovementType : MobilityType.values()) {
            movementCosts.put(RPGridMovementType, 1f);
            traversability.put(RPGridMovementType, true);
            groundHarms.put(RPGridMovementType, false);
        }

        switch(tileType) {
            case PLAINS:
                traversability.put(MobilityType.SAILING, false);
                movementCosts.put(MobilityType.WHEELS, 1.5f);
                break;

            case ROAD:
                traversability.put(MobilityType.SAILING, false);
                movementCosts.put(MobilityType.CAVALRY, .5f);
                movementCosts.put(MobilityType.INFANTRY, .5f);
                break;

            case SHALLOW_WATER:
                movementCosts.put(MobilityType.INFANTRY, 2f);
                movementCosts.put(MobilityType.CAVALRY, 2.5f);
                movementCosts.put(MobilityType.SAILING, 1.5f);
                traversability.put(MobilityType.WHEELS, false);
                break;

            case ROUGH_HILLS:
                movementCosts.put(MobilityType.INFANTRY, 1.5f);
                movementCosts.put(MobilityType.CAVALRY, 2f);
                movementCosts.put(MobilityType.WHEELS, 2.5f);
                traversability.put(MobilityType.SAILING, false);
                break;

            case MOUNTAIN:
                movementCosts.put(MobilityType.INFANTRY, 2f);
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                break;


            case LOW_WALL:
                blocksLineOfSight = true;
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                break;

            case LAVA:
                groundHarms.put(MobilityType.CAVALRY, true);
                groundHarms.put(MobilityType.INFANTRY, true);
                groundHarms.put(MobilityType.WHEELS, true);

                movementCosts.put(MobilityType.INFANTRY, 1.5f);
                movementCosts.put(MobilityType.WHEELS, 2f);
                movementCosts.put(MobilityType.CAVALRY, 1.5f);
                movementCosts.put(MobilityType.SAILING, 1.5f);
                break;

            case IMPASSIBLE_WALL:
                blocksLineOfSight = true;
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                traversability.put(MobilityType.SAILING, false);
                traversability.put(MobilityType.FLYING, false);
                break;

            case FORTRESS:
                defenseValue = 2;
                traversability.put(MobilityType.SAILING, false);
                movementCosts.put(MobilityType.WHEELS, 1f);
                movementCosts.put(MobilityType.CAVALRY, .5f);
                movementCosts.put(MobilityType.INFANTRY, .5f);
                break;

            case FOREST:
                visionReduction = 1;
                traversability.put(MobilityType.SAILING, false);
                movementCosts.put(MobilityType.WHEELS, 2.5f);
                movementCosts.put(MobilityType.INFANTRY, 1.5f);
                movementCosts.put(MobilityType.CAVALRY, 2f);
                break;

            case DEEP_WATER:
                traversability.put(MobilityType.CAVALRY, false);
                traversability.put(MobilityType.INFANTRY, false);
                traversability.put(MobilityType.WHEELS, false);
                break;

            case CORAL_REEF:
                defenseValue = 1;
                movementCosts.put(MobilityType.SAILING, 2f);
                traversability.put(MobilityType.WHEELS, false);
                break;
        }

    }

    public void occupy(WyrActor.Unit occupier) {
        if(this.occupier == occupier) return;
        this.vacate();
        this.occupier = occupier;
        occupier.occupyTile(this);
    }
    public void occupyAirspace(WyrActor.Unit occupier) {
        if(this.aerialOccupier == occupier) return;
        this.occupier = occupier;
        occupier.occupyTile(this);
    }
    public void setProp(WyrActor.Prop prop) {
        if(this.prop == prop) return;
        this.prop = prop;
        prop.occupyTile(this);
    }
    public void setAerialProp(WyrActor.Prop prop) {
        if(this.aerialProp == prop) return;
        this.aerialProp = prop;
        prop.occupyTile(this);
    }

    public void highlight() {
        if(highlighted) return;
        highlighted = true;
        highlighter = new RPGridHighlighter(this);
        Objects.requireNonNull(handlers.screen()).getGameStage().addActor(highlighter);
        highlighter.setPosition(XColumn, YRow);
    }
    public void standardize() {
        clearEphemeralInteractables();
        if(!highlighted) return;
        highlighter.kill();
        highlighted = false;
        // I don't think this cares if it's actually there or not?
        // UPDATE: It does.
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

    public void vacate() {
        if(this.occupier == null) return;
        this.occupier = null;
    }
    public void removeProp() { this.prop = null; }

    public Vector2 getCoordinates() { return new Vector2(XColumn, YRow); }
    public int getXColumn() { return XColumn; }
    public int getYRow() { return  YRow; }
    public int getDefenseValue() { return  defenseValue; }
    public boolean isSolid() { return isSolid; }
    public boolean isOccupied() { return  occupier != null; }
    public boolean hasProp() { return  prop != null; }
    public boolean getHarms(MobilityType RPGridMovementType) { return groundHarms.get(RPGridMovementType); }
    public boolean isTraversableBy(WyrActor unit) { return this.isTraversableBy(unit.getMobilityType()); }
    public boolean isTraversableBy(MobilityType RPGridMovementType) { return traversability.get(RPGridMovementType); }
    public boolean blocksLineOfSight() { return blocksLineOfSight; }
    public boolean groundIsObstructed(WyrActor.Unit forUnit) { return groundIsObstructed(forUnit.getTeamAlignment(), forUnit.getMobilityType()); }
    public Obstruction getObstruction (WyrActor.Unit forUnit) {
        if(isSolid || !isTraversableBy(forUnit.getMobilityType())) return Obstruction.TERRAIN;
        if(occupier != null) {
            if(occupier.isSolid() || !GridPathfinder.teamCanPass(forUnit.getTeamAlignment(), occupier.getTeamAlignment())) return Obstruction.UNIT;
        }
        if(prop != null) if(prop.isSolid()) return Obstruction.PROP;
        return null;
    }
    public boolean groundIsObstructed(@Null TeamAlignment team, MobilityType moveType) {
        if(groundIsSolid()) return true;
        if(!isTraversableBy(moveType)) return true;
        if(occupier != null) { return !GridPathfinder.teamCanPass(team, occupier.getTeamAlignment()); }
        return false;
    }
    public boolean groundIsSolid() {
        if(isSolid)                                 return true;
        if(occupier != null) if(occupier.isSolid()) return true;
        if(prop     != null) return prop.isSolid();
        return false;
        // This used to be a disgustingly long, stacked-ternary. You're welcome.
    }
    public boolean airspaceIsObstructed(TeamAlignment alignment) { return airspaceIsSolid || aerialOccupier.isSolid() || aerialProp.isSolid(); }
    public TileType getTileType() { return tileType; }
    public Float moveCostFor(MobilityType RPGridMovementType) { return movementCosts.get(RPGridMovementType); }
    protected Array<WyrInteraction> getEphemeralInteractions() { return ephemeralInteractions; }
    protected Array<WyrInteraction> getStaticInteractions() {
        final Array<WyrInteraction> returnValue = new Array<>();
        if(isOccupied()) returnValue.addAll(occupier.getInteractions());
        if(hasProp()) returnValue.addAll(prop.getInteractions());
        return returnValue;
    }
    public Array<WyrInteraction> getAllInteractions() {
        final Array<WyrInteraction> returnValue = new Array<>();
        returnValue.addAll(getEphemeralInteractions());
        returnValue.addAll(getStaticInteractions());
        return returnValue;
    }
    public WyrActor.Unit occupier() { return occupier; }
    public WyrActor prop() { return prop; }

}
