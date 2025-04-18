package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class ObjectiveEscapeTile extends LogicalTile {

    public VictoryCondition associatedVictCon;

    public UnitRoster requiredUnit;

    public ObjectiveEscapeTile(WYRMGame game, float column, float row) {
        super(game, column, row);
        SharedInit();
    }

    public ObjectiveEscapeTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        SharedInit();
    }

    public ObjectiveEscapeTile(WYRMGame game, float column, float row, UnitRoster req) {
        super(game, column, row);
        requiredUnit = req;
        tileType = LogicalTileType.OBJECTIVE_ESCAPE;
    }

    private void SharedInit() {

        tileType = LogicalTileType.OBJECTIVE_ESCAPE;
        requiredUnit = null;
    }

    @Override
    public void setObjectiveUnit(UnitRoster unit) {
        requiredUnit = unit;
    }




}
