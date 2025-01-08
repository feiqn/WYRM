package com.feiqn.wyrm.logic.screens.gamescreens;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class GridScreen_1A extends GridScreen {

    // Use this as an example / template going forward.

    public GridScreen_1A(WYRMGame game) {
        super(game, StageList.STAGE_1A);
    }

    @Override
    public void show() {
        super.show();

        teamHandler.setAllyTeamUsed();

        setUpVictCons();
    }

    @Override
    protected void setUpVictCons() {
        // TODO: Account for if player escapes north with Leif instead.
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        leifEscapeVictCon.setObjectiveText("Victory: Leif Escapes");
        leifEscapeVictCon.setMoreInfo("Leif can escape to the West, safely fleeing the assault.");
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);
//        HUD.addVictCon(leifEscapeVictCon);

        //optional, Antal escapes through the north tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinate(49, 25);
        antalEscapeVictCon.setObjectiveText("Optional: Antal Survives and Escapes");
        antalEscapeVictCon.setMoreInfo("The allied (green) knight, Antal, is trying to escape the assault with his life. To survive, he must reach the forest treeline by following the road north before he is killed by enemy soldiers.");
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);
//        HUD.addVictCon(antalEscapeVictCon);
    }

    @Override
    public void stageClear() {
        game.campaignHandler.setStageAsCompleted(StageList.STAGE_1A);

        if(conditionsHandler.victoryConditionIsSatisfied(1)) { // Antal survived.
            game.campaignHandler.setUnitAsRecruited(UnitRoster.ANTAL);
            game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2A);
        } else {
            game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2B);
        }

    }
}
