package com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.MapObject;

public class BreakableWall extends MapObject {

    public int health;

    public BreakableWall(WYRMGame game) {
        super(game);
        sharedInit();
    }

    public BreakableWall(WYRMGame game, Texture texture) {
        super(game, texture);
        sharedInit();
    }

    public BreakableWall(WYRMGame game, TextureRegion region) {
        super(game, region);
        sharedInit();
    }

    private void sharedInit() {
        name = "Wall";
        health = 20;

    }
}
