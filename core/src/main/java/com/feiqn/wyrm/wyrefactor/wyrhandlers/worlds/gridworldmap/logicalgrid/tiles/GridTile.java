package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;

import java.util.HashMap;

public class GridTile {

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

    protected final WYRMGame root;
    protected final TileType tileType;

    protected int defenseValue    = 0;
    protected int visionReduction = 0;

    protected final int XColumn;
    protected final int YRow;

    protected boolean blocksLineOfSight = false;
    protected boolean isSolid           = false;

    protected final HashMap<MovementType, Float>   movementCosts  = new HashMap<>();
    protected final HashMap<MovementType, Boolean> traversability = new HashMap<>();
    protected final HashMap<MovementType, Boolean> harms          = new HashMap<>();

    protected final Array<GridInteraction> interactables = new Array<>();

    protected GridUnit occupier;
    protected GridProp prop;



    public GridTile(WYRMGame root, TileType tileType, int xColumn, int yRow) {
        this.root     = root;
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;

        for(MovementType movementType : MovementType.values()) {
            movementCosts.put(movementType, 1f);
            traversability.put(movementType, true);
            harms.put(movementType, false);
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
                harms.put(MovementType.CAVALRY, true);
                harms.put(MovementType.INFANTRY, true);
                harms.put(MovementType.WHEELS, true);

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


    public void occupy(GridUnit occupier) {
        if(this.occupier == occupier) return;
        this.occupier = occupier;
        occupier.occupy(this);
    }
    public void setProp(GridProp prop) {
        if(this.prop == prop) return;
        this.prop = prop;
        prop.occupy(this);
    }

    public void addInteractable(GridInteraction interaction) {
        interactables.add(interaction);
    }
    public void vacate() { this.occupier = null; }
    public void removeProp() { this.prop = null; }

    public Vector2 getCoordinates() { return new Vector2(XColumn, YRow); }
    public int getXColumn() { return XColumn; }
    public int getYRow() { return  YRow; }
    public int getDefenseValue() { return  defenseValue; }
    public boolean isOccupied() { return  occupier != null; }
    public boolean hasProp() { return  prop != null; }
    public boolean getHarms(MovementType movementType) { return harms.get(movementType); }
    public boolean isTraversableBy(GridUnit unit) { return this.isTraversableBy(unit.getMovementType()); }
    public boolean isTraversableBy(MovementType movementType) { return traversability.get(movementType); }
    public boolean blocksLineOfSight() {return blocksLineOfSight; }
    public boolean isSolid() { return isSolid || occupier.isSolid() || prop.isSolid(); }
    public Float moveCostFor(MovementType movementType) { return movementCosts.get(movementType); }
    public Array<GridInteraction> getInteractables() { return interactables; }
}
