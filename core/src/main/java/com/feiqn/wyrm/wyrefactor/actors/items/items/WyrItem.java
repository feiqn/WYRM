package com.feiqn.wyrm.wyrefactor.actors.items.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrItem<
        Animation   extends WyrAnimator<?,?,?>,
        Interaction extends WyrInteraction<?,?>,
        MetaHandle  extends MetaHandler<?,?,?,?,?,?,?,?,?,?>,
        Stats       extends WyrStats<?,?,?,?,?,?>
            > extends WyrActor<Animation, Interaction, MetaHandle, Stats> {

    protected boolean isContainer = false;
    protected int containerSize = 0;
    protected final Array<WyrItem<?,?,?,?>> containedItems = new Array<>();

    protected Stats stats;

    protected WyrItem() {
        super(ActorType.ITEM);
        setup();
    }

    abstract protected void setup();

    public void addToContainer(WyrItem<?,?,?,?> item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeFromContainer(WyrItem<?,?,?,?> item) {
        Gdx.app.log("TODO", "XD");
    }
    public void removeContainerIndex(int index) {
        Gdx.app.log("TODO", "XD");
    }

    public boolean                 isContainer()       { return this.isContainer;    }
    public int                     getContainerSize()  { return this.containerSize;  }
    public Array<WyrItem<?,?,?,?>> getContainedItems() { return this.containedItems; }
    public Stats                   getStats()          { return this.stats;          }
}
