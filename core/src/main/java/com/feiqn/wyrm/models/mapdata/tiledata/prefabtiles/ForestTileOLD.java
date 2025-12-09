package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.MovementType;

public class ForestTileOLD extends OLD_LogicalTile {
    public ForestTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.FOREST;

        visionReduction = 1;
        movementCost.put(MovementType.INFANTRY, 1.5f);
        movementCost.put(MovementType.CAVALRY, 2f);

    }

}
