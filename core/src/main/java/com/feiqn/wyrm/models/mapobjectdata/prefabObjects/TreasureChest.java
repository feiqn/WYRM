package com.feiqn.wyrm.models.mapobjectdata.prefabObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapobjectdata.MapObject;

public class TreasureChest extends MapObject {
    public TreasureChest(WYRMGame game) {
        super(game);
    }

    public TreasureChest(WYRMGame game, Texture texture) {
        super(game, texture);
    }

    public TreasureChest(WYRMGame game, TextureRegion region) {
        super(game, region);
    }

    private void sharedInit() {

    }
}
