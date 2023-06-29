package com.feiqn.wyrm.models.mapobjectdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class MapObject extends Image {

    private WYRMGame game;

    public String name;

    private final MapObject self = this;

    public MapObject(WYRMGame game) {
        super();
        this.game = game;
        sharedInit();
    }

    public MapObject(WYRMGame game, Texture texture) {
        super(texture);
        this.game = game;
        sharedInit();
    }

    public MapObject(WYRMGame game, TextureRegion region) {
        super(region);
        this.game = game;
        sharedInit();
    }

    private void sharedInit() {
        name = "Interactable Object";
        setSize(1,1);
    }
}
