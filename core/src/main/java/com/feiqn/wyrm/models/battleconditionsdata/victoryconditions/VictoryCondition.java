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

    protected String objectiveText;
    protected String moreInfo;

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

    public void setObjectiveText(String sequence) {
        objectiveText = sequence;
    }

    public void setMoreInfo(String string) {
        moreInfo = string;
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

    public Vector2 getAssociatedCoordinate() { return  associatedCoordinate; }

    public String getObjectiveText() {
        return objectiveText;
    }

    public int getTurnGoal() {
        return turnGoal;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public UnitRoster getAssociatedUnit() {
        return associatedUnit;
    }

    public VictoryConditionType getVictConType() {
        return victConType;
    }

}
