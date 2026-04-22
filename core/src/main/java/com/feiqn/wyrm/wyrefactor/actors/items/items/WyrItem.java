package com.feiqn.wyrm.wyrefactor.actors.items.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;

public abstract class WyrItem<
        Animation extends WyrAnimator<?>,
        Interaction extends WyrInteraction<?,?>
            > extends WyrActor<Animation, Interaction> {

    protected boolean isContainer = false;
    protected int containerSize = 0;
    protected final Array<WyrItem<?,?>> containedItems = new Array<>();

    final protected WyrStats<?> stats = new WyrStats<>(this, WyrActor.ActorType.ITEM);

    protected WyrItem() {
        super(ActorType.ITEM);
        setup();
    }


    protected void setup() {
        stats.setAPRestoreRate(0);
    }

    public void addToContainer(WyrItem<?,?> item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeFromContainer(WyrItem<?,?> item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeContainerIndex(int index) {
        Gdx.app.log("TODO", "XD");
    }

    public boolean             isContainer()       { return this.isContainer; }
    public int                 getContainerSize()  { return this.containerSize; }
    public Array<WyrItem<?,?>> getContainedItems() { return this.containedItems; }
    public WyrStats<?>         getStats()          { return this.stats; }
}
