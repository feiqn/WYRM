package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionRegister;
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

    public Array<GridUnit> unitsHoldingPriority(boolean recursed) {
        final Array<GridUnit> returnValue = new Array<>();
        int tick = -1;
        for(GridUnit unit : unifiedTurnOrder()) {
            if(tick == -1) {
                if(unit.canMove()) {
                    returnValue.add(unit);
                    tick = unit.modifiedStatValue(StatTypes.SPEED);
                }
            } else {
                if(unit.canMove()) {
                    if(unit.modifiedStatValue(StatTypes.SPEED) == tick) {
                        returnValue.add(unit);
                    } else {
                        break;
                    }
                }
            }
        }
        if(returnValue.size > 0) return returnValue;
        if(!recursed) {
            // I can imagine a situation in which
            // all units in battle have negative AP
            // and require several turns to restore
            // back, but the game nonetheless has not
            // hung.
            // I'll leave this for now as some training
            // wheels, then take it out later.
            handleTurnAdvance();
            return unitsHoldingPriority(true);
        }
        return null;
    }

    public Array<GridUnit> unifiedTurnOrder() {
        return register().unifiedTurnOrder();
    }

    private void handleTurnAdvance() {
        register().advanceTurn(); // prompts units to update
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update
        //  - call map to show options for turn
        //  - check if call to ai is needed
    }

    @Override
    protected GridConditionRegister register() {
        return (GridConditionRegister) register;
    }

}
