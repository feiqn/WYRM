package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.WyrCombatHandler;

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
