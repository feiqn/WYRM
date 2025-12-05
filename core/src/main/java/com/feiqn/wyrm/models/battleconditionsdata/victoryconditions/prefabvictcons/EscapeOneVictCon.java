package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;

public class EscapeOneVictCon extends VictoryCondition {

    public EscapeOneVictCon(WYRMGame game, UnitRoster escapee, boolean terminal) {
        super(game, VictoryConditionType.ESCAPE_ONE, terminal);
        this.associatedUnit = escapee;
    }

}
