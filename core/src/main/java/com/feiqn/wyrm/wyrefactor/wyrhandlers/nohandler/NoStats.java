package com.feiqn.wyrm.wyrefactor.wyrhandlers.nohandler;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;

public final class NoStats extends WyrStats {

    private NoStats() {
        super(null);
    }

    @Override
    public Array statTypes() {
        return null;
    }
}
