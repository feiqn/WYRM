package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionRegister;

public class GridConditionRegister extends WyrConditionRegister {

    protected boolean fogOfWar                    = false;
    // TODO: two following variables converted to
    //  on-the-fly method calls.
//    protected boolean terminalVictoryConditionMet = false;
//    protected boolean terminalFailureConditionMet = false;
    protected boolean ironModeBTW                 = false;

    protected int currentTurnNumber = 0;

    protected Array<GridUnit> unifiedTurnOrder          = new Array<>();
    protected Array<GridUnit> battleRoster              = new Array<>();

//    protected Array<WyrVictoryCondition> victoryConditions = new Array<>();
//    public static Array<FailureCondition> failureConditions;

    protected static CombatHandler.IronMode ironMode;

    public GridConditionRegister(WYRMGame root) {
        super(root, WyrType.GRIDWORLD);
    }

    public void advanceTurn() { currentTurnNumber++; }
    public void addFog() { fogOfWar = true; }
    public void addToTurnOrder(GridUnit unit) {
        if(!battleRoster.contains(unit, true)) {
            battleRoster.add(unit);
            calculateTurnOrder();
        }
    }
    public void removeFromTurnOrder(GridUnit unit) {

    }
//    public void addVictoryCondition(WyrVictoryCondition condition) {}

//    public void addFailureCondition(WyrFailureCondition condition) {}

    public void satisfyVictoryCondition() {}
    // TODO: victory and failure conditions will be
    //  combined into one shared type.
    public void satisfyFailureCondition() {}

    private void calculateTurnOrder() {

    }

    public int turnCount() { return currentTurnNumber; }
    public int tickCount() { return 0; // TODO
    }
    public Array<GridUnit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public Array<GridUnit> battleRoster() { return battleRoster; }
//    public Array<VictoryCondition> victoryConditions() { return victoryConditions; }
//    public boolean terminalFailureConditionMet() { return terminalFailureConditionMet; }
//    public boolean terminalVictoryConditionMet() { return terminalVictoryConditionMet; }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFogOfWar() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
}
