package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.gridcombat;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.AnimationState.IDLE;


public final class RPGridCombatHandler extends WyrCombatHandler {

    public RPGridCombatHandler(RPGridMetaHandler metaHandler) {
        super(metaHandler);
    }

    public void queueCombat(RPGridUnit attacker, RPGridUnit defender) {
        if(inCombat) {

        } else {
            visualizeCombat(attacker, defender);
        }
    }

    private void visualizeCombat(RPGridUnit attacker, RPGridUnit defender) {
        inCombat = true;
        h().input().setInputMode(RPGridInputHandler.InputMode.LOCKED);

        final Runnable endCombat = new Runnable() {
            @Override
            public void run() {
                attacker.setAnimationState(IDLE);
                attacker.stats().spendAP();

                h().cutscenes().checkCombatEndTriggers(attacker.getCharacterID(), defender.getCharacterID());

                // TODO: check here for queued combats

                inCombat = false;
            }
        };

        h().cutscenes().checkCombatStartTriggers(attacker.getCharacterID(), defender.getCharacterID());

        // TODO: check for prop shenanigans

        // TODO: calculate attack rotations,
        //        build full sequence action,
        //        pass sequence to attacker.

    }



    @Override
    public RPGridMetaHandler h() {
        assert super.h() instanceof RPGridMetaHandler;
        return (RPGridMetaHandler) super.h();
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
