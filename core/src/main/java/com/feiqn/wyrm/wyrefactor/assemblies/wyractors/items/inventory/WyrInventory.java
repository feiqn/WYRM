package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory;

import com.badlogic.gdx.utils.Array;import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrInventory implements Wyr {

    // TODO: interface

    // defines and holds info for an actor's equipment
    // slots and loadout. Gear, inventory, etc.

    // probably replaces SimpleInventory

    protected int  capacity = 0;
    private final Array<WyrItem<?,?,?,?>> heldItems  = new Array<>();

    public WyrInventory() {}

    public boolean addItem(WyrItem<?,?,?,?> i) {
        if(heldItems.size >= capacity) return false;
        if(heldItems.contains(i, true)) return false;
        heldItems.add(i);
        return true;
    }

    public Array<WyrItem<?,?,?,?>> getHeldItems() {
        return heldItems;
    }
//    public Array<Item> getContainers() { return containers; }


}
