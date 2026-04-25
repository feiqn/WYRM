package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat;

import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator.RPGridAnimState.*;

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
        h.input().setInputMode(GridInputHandler.InputMode.LOCKED);

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
        return WyrType.RPGRIDWORLD;
    }

}
