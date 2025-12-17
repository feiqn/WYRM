package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrConditionsHandler extends Wyr {

    protected final WyrTeamManager       teamManager = new WyrTeamManager();
    protected final WyrConditionRegister register;

//    protected final WyrCombatHandler     combatHandler;


    protected WyrConditionsHandler(WyrType wyrType, WyrConditionRegister register) {
        super(wyrType);
        this.register = register;
//        this.combatHandler = combatHandler;
    }

//    public WyrCombatHandler combat() { return combatHandler; }

    // TODO:
    //  - return <UnitsOnTeam> extracted from UTO<GridUnit>

    protected abstract WyrConditionRegister register();
    public Array<TeamAlignment> teamsInPlay() {
        return null; // TODO: extract from team mng
    }

    protected static final class WyrTeamManager {
        // He's a Manager, not a Handler.
        // Entirely different pay-grade.

        private WyrTeamManager() {

        }

    }
}
