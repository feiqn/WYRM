package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionRegister;

import java.util.Comparator;

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
        // I'm not even gonna lie to you.
        // I am using a lame language model for this.

        unifiedTurnOrder.sort(new Comparator<GridUnit>() {
            @Override
            public int compare(GridUnit a, GridUnit b) {
                // 1) Speed, descending
                int speedDiff = b.modifiedStatValue(StatType.SPEED) - a.modifiedStatValue(StatType.SPEED);
                if (speedDiff != 0) return speedDiff;

                // 2) Team alignment priority
                return teamPriority(teamPriority(teamPriority(a.teamAlignment()) - teamPriority(b.teamAlignment())));
            }

            private TeamAlignment teamPriority(int i) {
                switch(i) {
                    case 0: return TeamAlignment.PLAYER;
                    case 1: return TeamAlignment.ENEMY;
                    case 2: return TeamAlignment.ALLY;
                    default: return TeamAlignment.OTHER;
                }
            }

            private int teamPriority(TeamAlignment ta) {
                switch (ta) {
                    case PLAYER: return 0;
                    case ENEMY:  return 1;
                    case ALLY:   return 2;
                    case OTHER:  return 3;
                    default:     return 4;
                }
            }
        });
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
