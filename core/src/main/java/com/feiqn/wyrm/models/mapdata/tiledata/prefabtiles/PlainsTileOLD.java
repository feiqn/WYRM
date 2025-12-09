package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;

public class PlainsTileOLD extends OLD_LogicalTile {
    // Technically, all the default values in LogicalTile are set for a standard Plain.
    // This class mostly exists for naming consistency.

    public PlainsTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);
    }
}
