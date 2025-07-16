package com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;

public class BreakableWallObject extends MapObject {

    public int health;

    public BreakableWallObject(WYRMGame game) {
        super(game);
        sharedInit();
    }

    public BreakableWallObject(WYRMGame game, Texture texture) {
        super(game, texture);
        sharedInit();
    }

    public BreakableWallObject(WYRMGame game, TextureRegion region) {
        super(game, region);
        sharedInit();
    }

    private void sharedInit() {
        name = "Wall";
        health = 20;

    }
}
