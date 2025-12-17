package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat;

import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatSequences;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrCombatHandler extends Wyr {

    // Space to grow later.

    protected boolean visualizing  = false; // vestigial?
    protected boolean combatQueued = false;

    private CombatSequences sequences;

    public WyrCombatHandler(WyrType wyrType) {
        super(wyrType);
    }

    protected abstract void queueCombat();

}
