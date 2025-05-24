package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;

public class PlainsTile extends LogicalTile {
    // Technically, all the default values in LogicalTile are set for a standard Plain.
    // This class mostly exists for naming consistency.

    public PlainsTile(WYRMGame game, float x, float y) {
        super(game, x, y);
    }
}
