package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class FortressTileOLD extends OLD_LogicalTile {
    public FortressTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.FORTRESS;

        defenseValue = 2;

        movementCost.put(WyRPG.MovementType.WHEELS, 1f);
    }


}
