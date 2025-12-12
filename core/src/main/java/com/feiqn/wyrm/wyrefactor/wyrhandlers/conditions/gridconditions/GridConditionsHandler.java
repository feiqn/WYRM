package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public class GridConditionsHandler extends WyrConditionsHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridConditionsHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD, new GridConditionRegister());
        this.h = metaHandler;
    }

    public void declareVictoryAndFailureConditions() {

    }

    public void declareBattlers(Array<GridUnit> units) {

    }

    public GridUnit unitHoldingPriority() {
        return null; // TODO, refactor of whoseNextInLine
    }

    public Array<GridUnit> unifiedTurnOrder() {
        return null; // TODO return from register
    }



}
