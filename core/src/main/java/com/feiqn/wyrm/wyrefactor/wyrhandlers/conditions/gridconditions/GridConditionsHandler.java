package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.gridcombat.GridCombatHandler;

public class GridConditionsHandler extends WyrConditionsHandler {

    public GridConditionsHandler(WYRMGame root) {
        super(root, WyrType.GRIDWORLD, new GridConditionRegister(root), new GridCombatHandler(root));

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
