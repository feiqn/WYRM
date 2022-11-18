package com.feiqn.wyrm.models.itemdata;

import com.feiqn.wyrm.WYRMGame;

public class Inventory {

    private final WYRMGame game;

    public boolean isFull;

    public Item item1,
                item2,
                item3,
                item4,
                item5;

    public Inventory(WYRMGame game) {
        this.game = game;
        isFull = false;
        item1 = new Item(game);
        item2 = new Item(game);
        item3 = new Item(game);
        item4 = new Item(game);
        item5 = new Item(game);
    }

    public void addItem(Item item) {

    }

}
