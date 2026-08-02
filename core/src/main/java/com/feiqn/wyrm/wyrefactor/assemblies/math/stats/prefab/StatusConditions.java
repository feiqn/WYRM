package com.feiqn.wyrm.wyrefactor.assemblies.math.stats.prefab;

import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatusConditionID.*;

public final class StatusConditions {

    private StatusConditions() {}

    public static WyrStatusCondition pierceDefenseFull() {
        return new WyrStatusCondition(PIERCE_DEFENSE_FULL_ON_HIT);
    }

    public static WyrStatusCondition slow() {
        return new WyrStatusCondition(SLOW_ON_HIT);
    }
}
