package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionRegister;

public final class GridConditionRegister extends WyrConditionRegister {

    private boolean fogOfWar     = false;
    private boolean ironModeBTW  = false;

    // TODO: two following variables converted to
    //  on-the-fly method calls.
//    protected boolean terminalVictoryConditionMet = false;
//    protected boolean terminalFailureConditionMet = false;

    private int currentTurnNumber = 0;

    private Array<GridUnit> unifiedTurnOrder = new Array<>();

//    protected Array<WyrVictoryCondition> victoryConditions = new Array<>();
//    public static Array<FailureCondition> failureConditions;

    private static CombatHandler.IronMode ironMode;

    public GridConditionRegister() {
        super(WyrType.GRIDWORLD);
    }

    public void advanceTurn() {
        currentTurnNumber++;
        for(GridUnit unit : unifiedTurnOrder) {
            unit.resetForNextTurn();
        }

    }
    public void addFog() { fogOfWar = true; }
    public void addToTurnOrder(GridUnit unit) {
        if(!unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.add(unit);
            calculateTurnOrder();
        }
    }
    public void removeFromTurnOrder(GridUnit unit) {
        if(unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.removeValue(unit,true);
            calculateTurnOrder();
        }
    }
    private void calculateTurnOrder() {
        final Array<GridUnit> roster = new Array<>();
        roster.addAll(unifiedTurnOrder);
        unifiedTurnOrder.clear();

        // TODO: build UTO here
    }

//    public void addVictoryCondition(WyrVictoryCondition condition) {}
//    public void addFailureCondition(WyrFailureCondition condition) {}

    public void satisfyVictoryCondition() {}
    // TODO: victory and failure conditions will be
    //  combined into one shared type.
    public void satisfyFailureCondition() {}

    public Array<GridUnit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
//    public int tickCount() { return 0; }
//    public Array<VictoryCondition> victoryConditions() { return victoryConditions; }
//    public boolean terminalFailureConditionMet() { return terminalFailureConditionMet; }
//    public boolean terminalVictoryConditionMet() { return terminalVictoryConditionMet; }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFogOfWar() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
}
