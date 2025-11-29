package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.gridmap.tiles.LogicalTileType;

public class DoorTile extends LogicalTile {

    public boolean isLocked;

    private Image doorImage;

    public DoorTile(WYRMGame game, float x, float y) {
        super(game, x, y);

        tileType = LogicalTileType.DOOR;

        isLocked = true;

        // doorImage = new Image("DOOR IMAGE HERE");

    }

    public void ShowDoor() {
//        game.activeBattleScreen.stage.addActor(doorImage);
    }

    public void HideDoor() {
        doorImage.remove();
    }
}
