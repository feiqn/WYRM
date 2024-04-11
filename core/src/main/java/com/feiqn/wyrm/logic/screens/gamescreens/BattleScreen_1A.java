package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.prefabLogicalMaps.stage_1a;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class BattleScreen_1A extends BattleScreen {

    public BattleScreen_1A(WYRMGame game) {
        super(game, StageList.STAGE_1A);

        //TODO: this is where victory, failure, and other conditions can be declared.

        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);
    }

    @Override
    protected void stageClear() {
        // TODO: switch based on which victory / failure conditions were satisfied
        game.campaignHandler.unlockedStages.add(StageList.STAGE_2A);
    }
}
