package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

public class WyrCombatHandler extends WyrHandler {

    // Space to grow later.

    protected boolean inCombat = false;
    protected boolean combatQueued = false;

    protected final Array<SequenceAction> queuedCombat = new Array<>();

    public WyrCombatHandler() {}

    public WyrCombatHandler(MetaHandler metaHandler) {
        super(metaHandler);
    }

    public void queueCombat(WyrActor attacker, WyrActor defender) {}
    protected void visualizeCombat(WyrActor attacker, WyrActor defender) {}

    public boolean isBusy() { return inCombat; }

}
