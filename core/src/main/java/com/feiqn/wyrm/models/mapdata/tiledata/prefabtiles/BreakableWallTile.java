package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class BreakableWallTile extends LogicalTile {

    public int hp;

    private Image wallImage;

    public BreakableWallTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        SharedInit();
    }

    private void SharedInit() {
        tileType = LogicalTileType.BREAKABLE_WALL;

        hp = 15;

        isTraversableByCavalry = false;
        isTraversableByFlyers = false;
        isTraversableByInfantry = false;
        isTraversableByWheels = false;

        blocksLineOfSight = true;

//        wallImage = new Image();
    }

    public void breakWall() {
        wallImage.remove();

        isTraversableByCavalry = true;
        isTraversableByWheels = true;
        isTraversableByInfantry = true;
        isTraversableByFlyers = true;

        blocksLineOfSight = false;
    }
}
