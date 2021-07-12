package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class FortressTile extends LogicalTile {
    public FortressTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        SharedInit();
    }

    public FortressTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        SharedInit();
    }

    private void SharedInit() {
        tileType = LogicalTileType.FORTRESS;

        defenseValue = 2;

        movementCost.put(MovementType.WHEELS, 1f);
    }
}
