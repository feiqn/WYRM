package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

public abstract class WyrItem extends WyrActor {

    protected boolean isContainer = false;
    protected int containerSize = 0;
    protected final Array<WyrItem> containedItems = new Array<>();

    protected WyrItem() {
        super(ActorType.ITEM);
        setup();
    }

    protected void setup() {}

    public boolean addedToContainer(WyrItem item) {
        if(containedItems.size >= containerSize) return false;
        if(containedItems.contains(item, true)) return false;
        containedItems.add(item);
        return true;
    }
    public void removeFromContainer(WyrItem item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeContainerIndex(int index) {
        Gdx.app.log("TODO", "XD");
    }

    public boolean        isContainer()       { return this.isContainer;    }
    public int            getContainerSize()  { return this.containerSize;  }
    public Array<WyrItem> getContainedItems() { return this.containedItems; }
}
