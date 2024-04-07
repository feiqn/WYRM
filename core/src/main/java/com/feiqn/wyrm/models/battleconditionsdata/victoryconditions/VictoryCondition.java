package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class VictoryCondition {

    protected WYRMGame game;

    public VictoryConditionType victConType;

    protected boolean terminal;

    protected UnitRoster unit;
    protected int turnGoal;

    protected Vector2 tileGoal;

    public VictoryCondition(WYRMGame game, VictoryConditionType type, boolean terminal) {
        this.game = game;
        this.unit = UnitRoster.MR_TIMN;
        victConType = type;
        this.terminal = terminal;
        turnGoal = 0;
        tileGoal = new Vector2(-1, -1);
    }

    public boolean isTerminal() { return  terminal; }

    public boolean conditionIsSatisfied(){ return false; }

}
