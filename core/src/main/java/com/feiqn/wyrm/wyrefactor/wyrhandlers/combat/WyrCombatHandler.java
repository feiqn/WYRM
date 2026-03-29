package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrCombatHandler<T extends WyrActor> implements Wyr {

    // Space to grow later.

    protected boolean inCombat = false;
    protected boolean combatQueued = false;

    protected final Array<SequenceAction> queuedCombat = new Array<>();

    public WyrCombatHandler() {}

    public abstract void queueCombat(T attacker, T defender);
    protected abstract void visualizeCombat(T attacker, T defender);

}
