package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridMovementType;

public class LavaTileOLD extends OLD_LogicalTile {

    public LavaTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = LogicalTileType.LAVA;

        damagesCavalry = true;
        damagesInfantry = true;
        damagesWheels = true;

        movementCost.put(RPGridMovementType.INFANTRY, 1.5f);
        movementCost.put(RPGridMovementType.WHEELS, 2f);
        movementCost.put(RPGridMovementType.CAVALRY, 1.5f);
    }
}
