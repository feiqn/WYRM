package com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;

public class DoorTile extends LogicalTile {

    public boolean isLocked;

    private Image doorImage;

    public DoorTile(WYRMGame game, float x, float y) {
        super(game, x, y);
        SharedInit();
    }

    public DoorTile(WYRMGame game, Vector2 coordinates) {
        super(game, coordinates);
        SharedInit();
    }

    private void SharedInit() {
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
