package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.gridcombat;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.grid.RPGridAnimator.RPGridAnimState.IDLE;

public final class GridCombatHandler extends WyrCombatHandler<RPGridUnit> {

    private final RPGridMetaHandler h;

    public GridCombatHandler(RPGridMetaHandler metaHandler) {
        super();
        this.h = metaHandler;
    }

    @Override
    public void queueCombat(RPGridUnit attacker, RPGridUnit defender) {
        if(inCombat) {

        } else {
            visualizeCombat(attacker, defender);
        }
    }

    @Override
    protected void visualizeCombat(RPGridUnit attacker, RPGridUnit defender) {
        inCombat = true;
        h.input().setInputMode(RPGridInputHandler.InputMode.LOCKED);

        final Runnable endCombat = new Runnable() {
            @Override
            public void run() {
                attacker.setAnimationState(IDLE);
                attacker.stats().spendAP();

                h.cutscenes().checkCombatEndTriggers(attacker.getRosterID(), defender.getRosterID());

                // TODO: check here for queued combats

                inCombat = false;
            }
        };

        h.cutscenes().checkCombatStartTriggers(attacker.getRosterID(), defender.getRosterID());

        // TODO: check for prop shenanigans

        // TODO: calculate attack rotations,
        //        build full sequence action,
        //        pass sequence to attacker.

    }



    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

}
