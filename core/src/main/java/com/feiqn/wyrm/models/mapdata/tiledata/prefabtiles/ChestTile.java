package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class ChestTile extends LogicalTile {

    public boolean isLocked;

//    private InventoryItem containedItem;

    private Image lockedChestImage,
                  openedChestImage;

    public ChestTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        SharedInit();
    }

    public ChestTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        SharedInit();
    }

    private void SharedInit() {
        tileType = LogicalTileType.CHEST;

        isLocked = true;


    }

    public void unlockChest() {

    }
}
