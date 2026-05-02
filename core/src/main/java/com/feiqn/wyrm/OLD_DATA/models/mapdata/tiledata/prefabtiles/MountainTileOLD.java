package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridMovementType;

public class MountainTileOLD extends OLD_LogicalTile {
    public MountainTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.MOUNTAIN;

        isTraversableByCavalry = false;
        isTraversableByWheels = false;

        movementCost.put(RPGridMovementType.INFANTRY, 2f);
    }

}
