package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.ItemBank;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class WyrInventory implements Wyr {

    // defines and holds info for an actor's equipment
    // slots and loadout. Gear, inventory, etc.

    // probably replaces SimpleInventory

    protected WyrItem hammerSpace;

    public WyrInventory() {
        this.hammerSpace = ItemBank.Containers.Pocket();
    }

    public WyrItem items() {
        return hammerSpace;
    }

}
