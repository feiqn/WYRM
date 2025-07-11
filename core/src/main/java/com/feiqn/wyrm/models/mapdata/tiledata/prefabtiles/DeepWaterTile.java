package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class DeepWaterTile extends LogicalTile {

    public DeepWaterTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.DEEP_WATER;

        isTraversableByCavalry = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;
        isTraversableByBoats = true;

    }
}
