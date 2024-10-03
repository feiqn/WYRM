package com.feiqn.wyrm.logic.screens.gamescreens;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.VictConInfoPanel;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.logic.screens.stagelist.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class BattleScreen_1A extends BattleScreen {

    // Use this as an example / template going forward.

    public BattleScreen_1A(WYRMGame game) {
        super(game, StageList.STAGE_1A);

    }

    @Override
    public void show() {
        super.show();

        allyTeamUsed = true;

        //TODO: this is where victory, failure, and other conditions can be declared.

        // index 0
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        // index 1
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL, false);
        antalEscapeVictCon.setAssociatedCoordinate(49, 25);
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);

        // TODO: wrapper function for constructing victCons with associated tiles in WyrMap

        // TODO: victConInfoPanels declared here

        final VictConInfoPanel leifEscapesPanel = new VictConInfoPanel(game);
        leifEscapesPanel.setObjectiveLabelText("Victory: Leif Escapes");
        leifEscapesPanel.setMoreInfoLabelText("Leif can escape to the West, safely fleeing the assault.");
        leifEscapesPanel.setIndex(0);
        hudStage.addActor(leifEscapesPanel);

//        leifEscapesPanel.setPosition(1, Gdx.graphics.getHeight() - leifEscapesPanel.getHeight());

        final VictConInfoPanel antalEscapesPanel = new VictConInfoPanel(game);
        antalEscapesPanel.setObjectiveLabelText("Optional: Antal Survives and Escapes");
        antalEscapesPanel.setMoreInfoLabelText("The allied (green) knight, Antal, is trying to escape the assault with his life. To survive, he must reach the forest treeline by following the road north before he is killed by enemy soldiers.");
        antalEscapesPanel.setIndex(1);
    }

    @Override
    protected void stageClear() {
        // TODO: switch based on which victory / failure conditions were satisfied
        game.campaignHandler.setStageAsUnlocked(StageList.STAGE_2A);
        game.campaignHandler.setStageAsCompleted(StageList.STAGE_1A);

        for(VictoryCondition victCon : conditionsHandler.getVictoryConditions()) {
            if(victCon.conditionIsSatisfied()) {
                if(victCon.associatedUnit() == UnitRoster.ANTAL) {
                    game.campaignHandler.setUnitAsRecruited(UnitRoster.ANTAL);
                }
            }
        }

    }
}
