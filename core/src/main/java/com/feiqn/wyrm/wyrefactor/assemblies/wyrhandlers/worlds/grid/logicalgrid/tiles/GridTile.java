package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.MovementType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;

import java.util.HashMap;
import java.util.Objects;

public class GridTile implements Wyr {

    // refactor of LogicalTile

    public enum TileType {
        PLAINS,
        ROAD,
        FOREST,
        MOUNTAIN,
        ROUGH_HILLS,
        FORTRESS,
        IMPASSIBLE_WALL,
        LOW_WALL,
        SHALLOW_WATER,
        DEEP_WATER,
        CORAL_REEF,
        LAVA,
    }

    public enum AerialTileType { // "Weather"?
        CLEAR_SKY,
        STORM_CLOUDS,
    }

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

    protected final HashMap<MovementType, Float>   aerialMovementCosts = new HashMap<>();
    protected final HashMap<MovementType, Float>   movementCosts       = new HashMap<>();
    protected final HashMap<MovementType, Boolean> traversability      = new HashMap<>();
    protected final HashMap<MovementType, Boolean> groundHarms         = new HashMap<>();

    protected final Array<RPGridInteraction> ephemeralInteractions = new Array<>();
    protected final Array<RPGridInteraction> staticInteractions    = new Array<>();

    protected RPGridUnit occupier = null;
    protected RPGridProp prop     = null;

    protected RPGridUnit aerialOccupier = null;
    protected RPGridProp aerialProp     = null;

    protected RPGridHighlighter highlighter;

    protected final RPGridMetaHandler h;

    public GridTile(RPGridMetaHandler metaHandler, TileType tileType, int xColumn, int yRow) {
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;
        this.h = metaHandler;

        for(MovementType RPGridMovementType : MovementType.values()) {
            movementCosts.put(RPGridMovementType, 1f);
            traversability.put(RPGridMovementType, true);
            groundHarms.put(RPGridMovementType, false);
        }

        switch(tileType) {
            case PLAINS:
                traversability.put(MovementType.SAILING, false);
                movementCosts.put(MovementType.WHEELS, 1.5f);
                break;

            case ROAD:
                traversability.put(MovementType.SAILING, false);
                movementCosts.put(MovementType.CAVALRY, .5f);
                movementCosts.put(MovementType.INFANTRY, .5f);
                break;

            case SHALLOW_WATER:
                movementCosts.put(MovementType.INFANTRY, 2f);
                movementCosts.put(MovementType.CAVALRY, 2.5f);
                movementCosts.put(MovementType.SAILING, 1.5f);
                traversability.put(MovementType.WHEELS, false);
                break;

            case ROUGH_HILLS:
                movementCosts.put(MovementType.INFANTRY, 1.5f);
                movementCosts.put(MovementType.CAVALRY, 2f);
                movementCosts.put(MovementType.WHEELS, 2.5f);
                traversability.put(MovementType.SAILING, false);
                break;

            case MOUNTAIN:
                movementCosts.put(MovementType.INFANTRY, 2f);
                traversability.put(MovementType.CAVALRY, false);
                traversability.put(MovementType.WHEELS, false);
                traversability.put(MovementType.SAILING, false);
                break;


            case LOW_WALL:
                blocksLineOfSight = true;
                traversability.put(MovementType.CAVALRY, false);
                traversability.put(MovementType.INFANTRY, false);
                traversability.put(MovementType.WHEELS, false);
                traversability.put(MovementType.SAILING, false);
                break;

            case LAVA:
                groundHarms.put(MovementType.CAVALRY, true);
                groundHarms.put(MovementType.INFANTRY, true);
                groundHarms.put(MovementType.WHEELS, true);

                movementCosts.put(MovementType.INFANTRY, 1.5f);
                movementCosts.put(MovementType.WHEELS, 2f);
                movementCosts.put(MovementType.CAVALRY, 1.5f);
                movementCosts.put(MovementType.SAILING, 1.5f);
                break;

            case IMPASSIBLE_WALL:
                blocksLineOfSight = true;
                traversability.put(MovementType.CAVALRY, false);
                traversability.put(MovementType.INFANTRY, false);
                traversability.put(MovementType.WHEELS, false);
                traversability.put(MovementType.SAILING, false);
                traversability.put(MovementType.FLYING, false);
                break;

            case FORTRESS:
                defenseValue = 2;
                traversability.put(MovementType.SAILING, false);
                movementCosts.put(MovementType.WHEELS, 1f);
                movementCosts.put(MovementType.CAVALRY, .5f);
                movementCosts.put(MovementType.INFANTRY, .5f);
                break;

            case FOREST:
                visionReduction = 1;
                traversability.put(MovementType.SAILING, false);
                movementCosts.put(MovementType.WHEELS, 2.5f);
                movementCosts.put(MovementType.INFANTRY, 1.5f);
                movementCosts.put(MovementType.CAVALRY, 2f);
                break;

            case DEEP_WATER:
                traversability.put(MovementType.CAVALRY, false);
                traversability.put(MovementType.INFANTRY, false);
                traversability.put(MovementType.WHEELS, false);
                break;

            case CORAL_REEF:
                defenseValue = 1;
                movementCosts.put(MovementType.SAILING, 2f);
                traversability.put(MovementType.WHEELS, false);
                break;
        }

    }

    public void occupy(RPGridUnit occupier) {
        if(this.occupier == occupier) return;
        this.vacate();
        this.occupier = occupier;
        occupier.occupyTile(this);
    }
    public void occupyAirspace(RPGridUnit occupier) {
        if(this.aerialOccupier == occupier) return;
        this.occupier = occupier;
        occupier.occupyTile(this);
    }
    public void setProp(RPGridProp prop) {
        if(this.prop == prop) return;
        this.prop = prop;
        prop.occupyTile(this);
    }
    public void setAerialProp(RPGridProp prop) {
        if(this.aerialProp == prop) return;
        this.aerialProp = prop;
        prop.occupyTile(this);
    }

    public void highlight() {
        if(highlighted) return;
        highlighted = true;
        highlighter = new RPGridHighlighter(h, this);
        Objects.requireNonNull(h.screen()).getGameStage().addActor(highlighter);
        highlighter.setPosition(XColumn, YRow);
    }
    public void standardize() {
        clearEphemeralInteractables();
        if(!highlighted) return;
        highlighter.remove();
        highlighted = false;
        // I don't think this cares if it's actually there or not?
        // UPDATE: It does.
    }
    public void shadeHighlight(ShaderState state, TeamAlignment teamAlignment) {
        if(!highlighted) return;
        highlighter.shade(state, teamAlignment);
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
    public void addEphemeralInteractable(RPGridInteraction interaction) {
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
    public boolean getHarms(MovementType RPGridMovementType) { return groundHarms.get(RPGridMovementType); }
    public boolean isTraversableBy(RPGridUnit unit) { return this.isTraversableBy(unit.getMovementType()); }
    public boolean isTraversableBy(MovementType RPGridMovementType) { return traversability.get(RPGridMovementType); }
    public boolean blocksLineOfSight() { return blocksLineOfSight; }
    public boolean groundIsObstructed(RPGridUnit forUnit) { return groundIsObstructed(forUnit.getTeamAlignment(), forUnit.getMovementType()); }
    public Obstruction getObstruction (RPGridUnit forUnit) {
        if(isSolid || !isTraversableBy(forUnit.getMovementType())) return Obstruction.TERRAIN;
        if(occupier != null) {
            if(occupier.isSolid() || !GridPathfinder.teamCanPass(forUnit.getTeamAlignment(), occupier.getTeamAlignment())) return Obstruction.UNIT;
        }
        if(prop != null) if(prop.isSolid()) return Obstruction.PROP;
        return null;
    }
    public boolean groundIsObstructed(@Null TeamAlignment team, MovementType moveType) {
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
    public Float moveCostFor(MovementType RPGridMovementType) { return movementCosts.get(RPGridMovementType); }
    protected Array<RPGridInteraction> getEphemeralInteractions() { return ephemeralInteractions; }
    protected Array<RPGridInteraction> getStaticInteractions() {
        final Array<RPGridInteraction> returnValue = new Array<>();
        if(isOccupied()) returnValue.addAll(occupier.getGridInteractions());
        if(hasProp()) returnValue.addAll(prop.getGridInteractions());
        return returnValue;
    }
    public Array<RPGridInteraction> getAllInteractions() {
        final Array<RPGridInteraction> returnValue = new Array<>();
        returnValue.addAll(getEphemeralInteractions());
        returnValue.addAll(getStaticInteractions());
        return returnValue;
    }
    public RPGridUnit occupier() { return occupier; }
    public RPGridProp prop() { return prop; }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
