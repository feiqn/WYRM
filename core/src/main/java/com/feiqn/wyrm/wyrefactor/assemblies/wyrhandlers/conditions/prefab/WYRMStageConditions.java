package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.prefab;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCondition;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public final class WYRMStageConditions {

    private WYRMStageConditions() {}

    public static Array<WyrWinCondition> forStage(WyrFrame.Campaign.StageID stageID) {
        switch (stageID) {

            case STAGE_1A:
                return Stage_1A();

            default:
                return null;
        }
    }

    public static Array<WyrWinCondition> Stage_1A() {



        return null;
    }

}
