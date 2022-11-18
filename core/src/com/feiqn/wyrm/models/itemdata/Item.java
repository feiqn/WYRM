package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class Item {

    public WYRMGame game;
    public String name;
    public ItemType itemType;

    public Item(WYRMGame game) {
//        super();
        this.game = game;
        sharedInit();
    }
//    public Item(WYRMGame game, Texture texture) {
//        super(texture);
//        this.game = game;
//        sharedInit();
//    }
//    public Item(WYRMGame game, TextureRegion region) {
//        super(region);
//        this.game = game;
//        sharedInit();
//    }

    private void sharedInit() {
        this.name = "";
        this.itemType = ItemType.UtilityItem;
    }
}
