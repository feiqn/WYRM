package com.feiqn.wyrm.wyrefactor;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class BattleConditionRegister {

    public boolean fogOfWar;
    public boolean terminalVictoryConditionMet;
    public boolean terminalFailureConditionMet;
    public boolean ironModeBTW;

    public int currentTurnNumber;

    public Array<SimpleUnit> unifiedTurnOrder;
    public Array<SimpleUnit> battleRoster;
    public Array<VictoryCondition> victoryConditions;
//    public static Array<FailureCondition> failureConditions;

    public static CombatHandler.IronMode ironMode;

    public BattleConditionRegister() {
        fogOfWar = false;
        terminalVictoryConditionMet = false;
        terminalFailureConditionMet = false;
        ironModeBTW = false;

        currentTurnNumber = 0;

        battleRoster = new Array<>();
        unifiedTurnOrder = new Array<>();
        victoryConditions = new Array<>();
    }
}
