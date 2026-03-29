package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;

public abstract class WyrItem extends WyrActor {

    protected boolean isContainer = false;
    protected int containerSize = 0;
    protected final Array<WyrItem> containedItems = new Array<>();

    final protected WyrStats stats = new WyrStats(this, WyrActor.ActorType.ITEM);

    protected WyrItem() {
        super(WyrType.AGNOSTIC, ActorType.ITEM);
        setup();
    }

    protected WyrItem(WyrType wyrType) {
        super(wyrType, ActorType.ITEM);
        setup();
    }

    protected void setup() {
        stats.setAPRestoreRate(0);
    }

    public void addToContainer(WyrItem item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeFromContainer(WyrItem item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeContainerIndex(int index) {
        Gdx.app.log("TODO", "XD");
    }

    public boolean        isContainer()       { return this.isContainer; }
    public int            getContainerSize()  { return this.containerSize; }
    public Array<WyrItem> getContainedItems() { return this.containedItems; }
    public WyrStats       getStats()          { return this.stats; }
}
