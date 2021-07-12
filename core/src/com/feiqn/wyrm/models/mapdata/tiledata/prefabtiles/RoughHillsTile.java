package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class RoughHillsTile extends LogicalTile {

    public RoughHillsTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        sharedInit();
    }

    public RoughHillsTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        sharedInit();
    }

    public void sharedInit() {
        tileType = LogicalTileType.ROUGH_HILLS;

        movementCost.put(MovementType.INFANTRY, 1.5f);
        movementCost.put(MovementType.CAVALRY, 2f);
        movementCost.put(MovementType.WHEELS, 2.5f);
    }
}
