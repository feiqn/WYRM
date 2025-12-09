package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.gridcombat;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.WyrCombatHandler;

public class GridCombatHandler extends WyrCombatHandler {

    public GridCombatHandler(WYRMGame root) {
        super(root, WyrType.GRIDWORLD);
    }

}
