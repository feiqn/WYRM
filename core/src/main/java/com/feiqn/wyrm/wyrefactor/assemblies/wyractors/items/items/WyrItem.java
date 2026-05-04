package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.ActorType;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

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
