package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridMovementType;

public class CoralReefTileOLD extends DeepWaterTileOLD {

    public CoralReefTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.CORAL_REEF;

        defenseValue = 1;

        movementCost.put(RPGridMovementType.SAILING, 2f);

    }

}
