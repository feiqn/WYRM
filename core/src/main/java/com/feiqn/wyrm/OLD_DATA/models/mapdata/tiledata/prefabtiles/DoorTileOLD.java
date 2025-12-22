package com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.prefabtiles;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.tiledata.OLD_LogicalTile;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;

public class DoorTileOLD extends OLD_LogicalTile {

    public boolean isLocked;

    private Image doorImage;

    public DoorTileOLD(WYRMGame game, float x, float y) {
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
