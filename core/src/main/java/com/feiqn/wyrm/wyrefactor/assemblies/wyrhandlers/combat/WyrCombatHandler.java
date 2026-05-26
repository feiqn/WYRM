package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.AnimationState.*;

public final class WyrCombatHandler extends WyrHandler {

    protected boolean inCombat = false;
    protected boolean combatQueued = false;


    public WyrCombatHandler() {

    }
    public void queueCombat(WyrActor.Unit attacker, WyrActor.Unit defender) {
        if(inCombat) {

        } else {
            visualizeCombat(attacker, defender);
        }
    }

    private void visualizeCombat(WyrActor.Unit attacker, WyrActor.Unit defender) {
        inCombat = true;
        handlers.input().setInputMode(InputState.LOCKED);

        final Runnable endCombat = new Runnable() {
            @Override
            public void run() {
                attacker.setAnimationState(IDLE);
                attacker.stats().spendAP();

                handlers.cutscenes().checkCombatEndTriggers(attacker.getCharacterID(), defender.getCharacterID());

                // TODO: check here for queued combats

                inCombat = false;
            }
        };

        handlers.cutscenes().checkCombatStartTriggers(attacker.getCharacterID(), defender.getCharacterID());

        // TODO: check for prop shenanigans

        // TODO: calculate attack rotations,
        //        build full sequence action,
        //        pass sequence to attacker.

    }

    public boolean isBusy() { return inCombat; }

}
