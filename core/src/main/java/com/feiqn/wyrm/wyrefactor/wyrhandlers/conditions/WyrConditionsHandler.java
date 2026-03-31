package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;

public abstract class WyrConditionsHandler extends WyrHandler {

    protected final WyrTeamManager       teamManager = new WyrTeamManager();
    protected final WyrConditionRegister register;


    protected WyrConditionsHandler(WyrConditionRegister register) {
        this.register = register;
    }

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
