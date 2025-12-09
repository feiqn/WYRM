package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class ChestTileOLD extends OLD_LogicalTile {

    public boolean isLocked;

//    private InventoryItem containedItem;

    private Image lockedChestImage,
                  openedChestImage;

    public ChestTileOLD(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.CHEST;

        isLocked = true;

    }


    public void unlockChest() {

    }
}
