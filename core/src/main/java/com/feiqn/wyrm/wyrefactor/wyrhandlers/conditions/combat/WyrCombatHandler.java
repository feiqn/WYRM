package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrCombatHandler extends Wyr {

    // Space to grow later.

    protected boolean visualizing  = false;
    protected boolean combatQueued = false;

    public WyrCombatHandler(WyrType wyrType) {
        super(wyrType);
    }

    protected abstract void queueCombat();

}
