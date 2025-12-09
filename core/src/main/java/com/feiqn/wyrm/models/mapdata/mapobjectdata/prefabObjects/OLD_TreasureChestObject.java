package com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;

public class OLD_TreasureChestObject extends MapObject {
    public OLD_TreasureChestObject(WYRMGame game) {
        super(game);
    }

    public OLD_TreasureChestObject(WYRMGame game, Texture texture) {
        super(game, texture);
    }

    public OLD_TreasureChestObject(WYRMGame game, TextureRegion region) {
        super(game, region);
    }

    private void sharedInit() {

    }
}
