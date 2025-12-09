package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class CoralReefTileOLD extends DeepWaterTileOLD {

    public CoralReefTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.CORAL_REEF;

        defenseValue = 1;

        movementCost.put(MovementType.SAILING, 2f);

    }

}
