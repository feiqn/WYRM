package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.TeamHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;

public class GridConditionsHandler extends WyrConditionsHandler {

// TODO:
//  remake these all in Wyr:
//    private final TeamHandler teamHandler;
//    private final CombatHandler combatHandler;

    private final GridConditionRegister conditions;

    protected GridConditionsHandler(WYRMGame root) {
        super(root, WyrType.GRIDWORLD);
        conditions = new GridConditionRegister(root);
    }

    public void declareVictoryAndFailureConditions() {

    }

    public void declareBattlers(Array<GridUnit> units) {

    }

    public GridUnit unitHoldingPriority() {
        return null; // TODO, refactor of whoseNextInLine
    }



}
