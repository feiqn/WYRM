package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class LavaTile extends LogicalTile {

    public LavaTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = LogicalTileType.LAVA;

        damagesCavalry = true;
        damagesInfantry = true;
        damagesWheels = true;

        movementCost.put(MovementType.INFANTRY, 1.5f);
        movementCost.put(MovementType.WHEELS, 2f);
        movementCost.put(MovementType.CAVALRY, 1.5f);
    }
}
