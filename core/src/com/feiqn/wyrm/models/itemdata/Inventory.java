package com.feiqn.wyrm.models.itemdata;

import com.badlogic.gdx.utils.Array;
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
        item1 = new Item(game, ItemType.UtilityItem);
        item2 = new Item(game, ItemType.UtilityItem);
        item3 = new Item(game, ItemType.UtilityItem);
        item4 = new Item(game, ItemType.UtilityItem);
        item5 = new Item(game, ItemType.UtilityItem);
    }

    public void addItem(Item item) {
        // if item 1-5 .name == "" itemX = item
        // if item 1-5 .name != "" goto items Full
    }

    public void removeItem(Item item) {

    }

    public void removeItem(int itemNumber) {

    }

    public Array<Item> items() {
        final Array<Item> items = new Array<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        return items;
    }

}
