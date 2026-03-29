package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridCombatHandler extends WyrCombatHandler<GridUnit> {

    private final GridMetaHandler h;

    public GridCombatHandler(GridMetaHandler metaHandler) {
        super();
        this.h = metaHandler;
    }

    @Override
    public void queueCombat(GridUnit attacker, GridUnit defender) {
        if(inCombat) {

        } else {
            visualizeCombat(attacker, defender);
        }
    }

    @Override
    protected void visualizeCombat(GridUnit attacker, GridUnit defender) {
        inCombat = true;

        final Runnable endCombat = new Runnable() {
            @Override
            public void run() {
                attacker.stats().spendAP();

                h.cutscenes().checkCombatEndTriggers(attacker.getRosterID(), defender.getRosterID());
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
        return WyrType.GRIDWORLD;
    }

}
