package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat;

import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrCombatHandler extends Wyr_DEPRECATED {

    // Space to grow later.

    protected boolean visualizing  = false; // vestigial?
    protected boolean combatQueued = false;

    public WyrCombatHandler(WyrType wyrType) {
        super(wyrType);
    }

    protected abstract void queueCombat();

}
