package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class CampaignHandler {

    private final WYRMGame game;

    public Array<UnitRoster>  recruitedUnits,
                              deadUnits;

    public Array<StageList>  unlockedStages,
                             clearedStages;

    public CampaignHandler(WYRMGame game) {
        this.game = game;
        unlockedStages = new Array<>();
        clearedStages = new Array<>();
        recruitedUnits = new Array<>();
        deadUnits = new Array<>();
    }
    
}
