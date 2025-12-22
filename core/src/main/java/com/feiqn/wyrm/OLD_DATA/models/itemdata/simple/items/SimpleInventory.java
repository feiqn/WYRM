package com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.items;

import com.badlogic.gdx.utils.Array;

public class SimpleInventory {

    private final Array<SimpleItem> items;

    public SimpleInventory() {
        items = new Array<>();
    }

    public void addItem(SimpleItem item) {
        items.add(item);
    }

    public void removeItem(SimpleItem item) {
        if(items.contains(item,true)) items.removeValue(item, true);
    }

    public Array<SimpleItem> items() {
        return items;
    }
}
