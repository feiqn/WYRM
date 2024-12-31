package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.infopanels.VictConInfoPanel;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.mapdata.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.awt.*;

public class BattleScreen_1A extends BattleScreen {

    // Use this as an example / template going forward.

    public BattleScreen_1A(WYRMGame game) {
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
        // TODO: I think eventually I can set up a fairly clean automator function for this
        //       process, but right now it seems more trouble than it's worth.

        // index 0, terminal, Leif escapes through the south-west tile. TODO: Account for if player escapes north with Leif instead.
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(18, 0);
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        final VictConInfoPanel leifEscapesPanel = new VictConInfoPanel(game);
        leifEscapesPanel.setObjectiveLabelText("Victory: Leif Escapes");
        leifEscapesPanel.setMoreInfoLabelText("Leif can escape to the West, safely fleeing the assault.");
        leifEscapesPanel.setIndex(0);
        addVictConPanel(leifEscapesPanel);

        // index 1, optional, Antal escapes through the north tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinate(49, 25);
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);

        final VictConInfoPanel antalEscapesPanel = new VictConInfoPanel(game);
        antalEscapesPanel.setObjectiveLabelText("Optional: Antal Survives and Escapes");
        antalEscapesPanel.setMoreInfoLabelText("The allied (green) knight, Antal, is trying to escape the assault with his life. To survive, he must reach the forest treeline by following the road north before he is killed by enemy soldiers.");
        antalEscapesPanel.setIndex(1);
        addVictConPanel(antalEscapesPanel);
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
