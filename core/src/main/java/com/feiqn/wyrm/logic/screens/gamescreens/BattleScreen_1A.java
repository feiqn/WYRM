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

    }

    @Override
    public void show() {
        super.show();

        allyTeamUsed = true;

        //TODO: this is where victory, failure, and other conditions can be declared.

        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL, false);
        antalEscapeVictCon.setAssociatedCoordinate(19, 25);
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);


        // TODO: victConInfoPanels declared here
    }

    @Override
    protected void stageClear() {
        // TODO: switch based on which victory / failure conditions were satisfied
        game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2A);
        game.campaignHandler.setStageAsCompleted(StageList.STAGE_1A);

        for(VictoryCondition victCon : conditionsHandler.victoryConditions()) {
            if(victCon.conditionIsSatisfied()) {
                if(victCon.associatedUnit() == UnitRoster.ANTAL) {
                    game.campaignHandler.setUnitAsRecruited(UnitRoster.ANTAL);
                }
            }
        }

    }
}
