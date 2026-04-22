package com.feiqn.wyrm.OLD_DATA.models.battleconditionsdata.victoryconditions.prefabvictcons;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.OLD_DATA.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;

public class EscapeOneVictCon extends VictoryCondition {

    public EscapeOneVictCon(WYRMGame game, UnitIDRoster escapee, boolean terminal) {
        super(game, VictoryConditionType.ESCAPE_ONE, terminal);
        this.associatedUnit = escapee;
    }

}
