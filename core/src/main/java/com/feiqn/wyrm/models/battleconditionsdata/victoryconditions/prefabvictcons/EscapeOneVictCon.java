package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class EscapeOneVictCon extends VictoryCondition {

    public EscapeOneVictCon(WYRMGame game, UnitRoster escapee, boolean terminal) {
        super(game, VictoryConditionType.ESCAPE_ONE, terminal);
        this.associatedUnit = escapee;
    }

    public void escapeUnit(UnitRoster escapee) {
        if(escapee == associatedUnit) {
            satisfied = true;
        }
    }

    @Override
    public boolean conditionIsSatisfied() {
        // Condition is satisfied by FieldActionPopUp escape label upon matching unit escape.

        return satisfied;
    }

}
