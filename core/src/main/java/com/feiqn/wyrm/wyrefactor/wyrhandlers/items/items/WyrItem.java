package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.items;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.Examinable;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;

public class WyrItem extends WyrActor {

    boolean isContainer = false;
    int containerSize = 0;
    final Array<WyrItem> containedItems = new Array<>();

    final WyrStats stats = new WyrStats(this, GridActor.ActorType.ITEM);

    public WyrItem() {
        super(WyrType.AGNOSTIC, ActorType.ITEM);
    }

}
