package com.feiqn.wyrm.models.mapdata.tiledata;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.MovementType;

import java.util.HashMap;

public class LogicalTile extends Actor {

    // Something something tiledmap.properties something something toby fox

    private final WYRMGame game;

    // --UNITS--
    public Unit occupyingUnit;

    // --FLOATS--
    public float defenseValue,
                 visionReduction;

    // --ENUMS--
    public LogicalTileType tileType;

    // --HASHMAPS--
    protected HashMap<MovementType, Float> movementCost;

    // --INTS--
    public final int row,
                     column;

    // --VECTORS--
    public Vector2 coordinates;

    // --BOOLEANS--
    public boolean isOccupied,
                   isTraversableByBoats,
                   damagesBoats,
                   isTraversableByInfantry,
                   damagesInfantry,
                   isTraversableByFlyers,
                   damagesFlyers,
                   isTraversableByCavalry,
                   damagesCavalry,
                   isTraversableByWheels,
                   damagesWheels,
                   blocksLineOfSight;

    public LogicalTile(WYRMGame game, float column, float row) {
        super();
        this.game = game;
        this.row = (int)row;
        this.column = (int)column;
        this.coordinates = new Vector2(column,row);
        sharedInit();
    }

    public LogicalTile(WYRMGame game, Vector2 coordinates) {
        super();
        this.game = game;
        this.coordinates = coordinates;

        this.row = (int)coordinates.y;
        this.column = (int)coordinates.x;
        sharedInit();
    }

    private void sharedInit() {
        tileType = LogicalTileType.PLAINS;

        isTraversableByBoats = false;
        damagesBoats = false;
        isTraversableByCavalry = true;
        damagesCavalry = false;
        isTraversableByWheels = true;
        damagesWheels = false;
        isOccupied = false;
        isTraversableByInfantry = true;
        isTraversableByFlyers = true;
        damagesInfantry = false;
        damagesFlyers = false;
        blocksLineOfSight = false;
        defenseValue = 0;
        visionReduction = 0;



        movementCost = new HashMap<>();

        movementCost.put(MovementType.INFANTRY, 1f);
        movementCost.put(MovementType.FLYING, 1f);
        movementCost.put(MovementType.CAVALRY, 1f);
        movementCost.put(MovementType.WHEELS, 1.5f);
        movementCost.put(MovementType.SAILING, 999f);
    }

    public boolean isTraversableByUnitType(MovementType type) {
        switch(type) {
            case FLYING:
                return isTraversableByFlyers;
            case WHEELS:
                return isTraversableByWheels;
            case CAVALRY:
                return isTraversableByCavalry;
            case SAILING:
                return isTraversableByBoats;
            case INFANTRY:
                return isTraversableByInfantry;
        }
        return false;
    }

    public float getMovementCostForMovementType(MovementType type){
        switch(type) {
            case INFANTRY:
                return movementCost.get(MovementType.INFANTRY);
            case SAILING:
                return movementCost.get(MovementType.SAILING);
            case CAVALRY:
                return movementCost.get(MovementType.CAVALRY);
            case WHEELS:
                return movementCost.get(MovementType.WHEELS);
            case FLYING:
                return movementCost.get(MovementType.FLYING);
            default:
                return 1f;
        }
    }

    public void highlightCanMove() {
        // add a blue highlight image with data and touch listener
    }

    public void highlightCanAttack() {
        // similar to above
    }

    public void highlightCanSupport() {

    }

    public void clearHighlight() {

    }
}
