package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.TileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class LavaTileOLD extends OLD_LogicalTile {

    public LavaTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
        tileType = TileType.LAVA;

        damagesCavalry = true;
        damagesInfantry = true;
        damagesWheels = true;

        movementCost.put(WyRPG.MovementType.INFANTRY, 1.5f);
        movementCost.put(WyRPG.MovementType.WHEELS, 2f);
        movementCost.put(WyRPG.MovementType.CAVALRY, 1.5f);
    }
}
