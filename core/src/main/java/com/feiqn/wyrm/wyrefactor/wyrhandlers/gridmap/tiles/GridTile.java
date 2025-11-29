package com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;

import java.util.HashMap;

public class GridTile {

    // refactor of LogicalTile

    public enum Type {
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
        OBJECTIVE_SEIZE, // TODO: make these props instead
        OBJECTIVE_ESCAPE,
        OBJECTIVE_DESTROY,
    }

    protected final WYRMGame root;

    protected final Type tileType;

    private final GridTile self = this;

    protected int defenseValue;
    protected int visionReduction;

    protected final int XColumn;
    protected final int YRow;

    protected boolean blocksLineOfSight;

    protected final HashMap<MovementType, Float>   movementCosts;
    protected final HashMap<MovementType, Boolean> traversability;
    protected final HashMap<MovementType, Boolean> harms;

    protected GridUnit occupier;
    protected GridProp prop;

    public GridTile(WYRMGame root, Type tileType, int xColumn, int yRow) {
        this.root     = root;
        this.tileType = tileType;
        this.XColumn  = xColumn;
        this.YRow     = yRow;

        movementCosts  = new HashMap<>();
        traversability = new HashMap<>();
        harms          = new HashMap<>();

        defenseValue    = 0;
        visionReduction = 0;

        blocksLineOfSight = false;

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


    public void occupy(GridUnit occupier) { this.occupier = occupier; }
    public void setProp(GridProp prop) { this.prop = prop; }

    public void vacate() { this.occupier = null; }
    public void removeProp() { this.prop = null; }

    public int getXColumn() { return XColumn; }
    public int getYRow() { return  YRow; }
    public int getDefenseValue() { return  defenseValue; }
    public boolean isOccupied() { return  occupier != null; }
    public boolean hasProp() { return  prop != null; }
    public boolean getHarms(MovementType movementType) { return harms.get(movementType); }
    public boolean isTraversableBy(MovementType movementType) { return traversability.get(movementType); }
    public boolean blocksLineOfSight() {return blocksLineOfSight; }
//    public boolean isSolid() {  }
    public Float moveCostFor(MovementType movementType) { return movementCosts.get(movementType); }
}
