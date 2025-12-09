package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;

public class RoutVictCon extends VictoryCondition {

    public RoutVictCon(WYRMGame game) {
        super(game, VictoryConditionType.ROUT, true);
    }

    public RoutVictCon(WYRMGame game, boolean terminal) {
        super(game, VictoryConditionType.ROUT, terminal);
    }

    @Override
    public boolean conditionIsSatisfied() {
        // Condition is satisfied if all enemy units are dead.

        return game.activeOLDGridScreen.conditions().teams().getEnemyTeam().size <= 0;
    }

}
