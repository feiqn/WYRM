package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class RoadTile extends LogicalTile {

    public RoadTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        sharedInit();
    }

    public RoadTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        sharedInit();
    }

    private void sharedInit() {
        this.tileType = LogicalTileType.ROAD;

        movementCost.put(MovementType.CAVALRY, .5f);
        movementCost.put(MovementType.INFANTRY, .5f);
        movementCost.put(MovementType.WHEELS, 1f);
    }
}
