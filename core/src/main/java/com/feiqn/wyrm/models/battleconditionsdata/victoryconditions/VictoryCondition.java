package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class VictoryCondition {

    protected WYRMGame game;

    public VictoryConditionType victConType;

    protected boolean terminal,
                      satisfied;

    protected UnitRoster associatedUnit;
    protected int turnGoal;

    protected Vector2 associatedCoordinate;

    public VictoryCondition(WYRMGame game, VictoryConditionType type, boolean terminal) {
        this.game = game;
        this.associatedUnit = UnitRoster.MR_TIMN;
        victConType = type;
        this.terminal = terminal;
        turnGoal = 0;
        satisfied = false;
        associatedCoordinate = new Vector2(-1, -1);
    }

    public boolean isTerminal() { return  terminal; }

    public void setAssociatedCoordinate(int up, int right) {
        associatedCoordinate = new Vector2(up, right);
    }

    public void satisfy() {
        satisfied = true;
    }

    public boolean conditionIsSatisfied(){ return satisfied; }

    public void updateInternalValues() {
        // used by child classes to set flags for internal game state
    }

    public UnitRoster associatedUnit() {
        return associatedUnit;
    }

}
