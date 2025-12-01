package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class ChestTile extends LogicalTile {

    public boolean isLocked;

//    private InventoryItem containedItem;

    private Image lockedChestImage,
                  openedChestImage;

    public ChestTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.CHEST;

        isLocked = true;

    }


    public void unlockChest() {

    }
}
