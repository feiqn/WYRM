package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_1a;

public class BattleScreen_1A extends BattleScreen {

    public BattleScreen_1A(WYRMGame game) {
        super(game, StageList.STAGE_1A);
    }

    @Override
    protected void stageClear() {
        // TODO: switch based on which victory / failure conditions were satisfied
        game.campaignHandler.unlockedStages.add(StageList.STAGE_2A);
    }
}
