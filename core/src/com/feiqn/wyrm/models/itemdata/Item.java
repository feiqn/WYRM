package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.WYRMGame;

public class Item extends Actor {

    public WYRMGame game;
    public String name;
    public ItemType itemType;

    public Image image;

    public Item(WYRMGame game) {
        this.game = game;
        sharedInit();
    }

    private void sharedInit() {
        this.name = "";
        this.itemType = ItemType.UtilityItem;

        image = new Image();
    }
}
