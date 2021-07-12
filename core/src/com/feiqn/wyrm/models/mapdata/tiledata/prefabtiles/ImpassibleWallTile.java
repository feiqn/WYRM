package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class ImpassibleWallTile extends LogicalTile {

    public ImpassibleWallTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        SharedInit();
    }

    public ImpassibleWallTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        SharedInit();
    }

    private void SharedInit() {
        tileType = LogicalTileType.IMPASSIBLE_WALL;

        isTraversableByCavalry = false;
        isTraversableByFlyers = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;
    }
}
