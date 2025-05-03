package com.feiqn.wyrm.logic.screens.gamescreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.DScript_1A_Antal_HelpMe;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.DScript_1A_Leif_LeaveMeAlone;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.AreaTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.CombatTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.TurnTrigger;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.logic.screens.StageList;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnit;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnit;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

import java.util.*;

public class GridScreen_1A extends GridScreen {

    // Use this as an example / template going forward.

    public GridScreen_1A(WYRMGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        conditionsHandler.teams().setAllyTeamUsed();
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/1A_v0.tmx");

        final MapProperties properties = tiledMap.getProperties();

        logicalMap = new AutoFillWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {

            @Override
            public void setUpUnits() {
//                final Ballista ballista = new Ballista(game);
//                placeMapObjectAtPosition(ballista, 19, 10);
//                ballistaObjects.add(ballista);
//                rootGroup.addActor(ballista);

                final SoldierUnit testEnemy = new SoldierUnit(game);
                testEnemy.setColor(Color.RED);
                testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy.setAIType(AIType.AGGRESSIVE);
                testEnemy.name = "Evil Timn";
                placeUnitAtPositionROWCOLUMN(testEnemy, 22, 29);
                conditionsHandler.addToTurnOrder(testEnemy);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
                rootGroup.addActor(testEnemy);
                testEnemy.setCannotMove();

                final SoldierUnit testEnemy2 = new SoldierUnit(game);
                testEnemy2.setColor(Color.RED);
                testEnemy2.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy2.setAIType(AIType.STILL);
                testEnemy2.name = "Evil Tumn";
                placeUnitAtPositionROWCOLUMN(testEnemy2, 23, 11);
                conditionsHandler.addToTurnOrder(testEnemy2);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy2);
                rootGroup.addActor(testEnemy2);
                testEnemy2.setCannotMove();

                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionROWCOLUMN(testChar, 28, 50);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

//                final SoldierUnit testChar2 = new SoldierUnit(game);
//                placeUnitAtPositionROWCOLUMN(testChar2, 20, 29);
//                conditionsHandler.addToTurnOrder(testChar2);
//                testChar2.setTeamAlignment(TeamAlignment.PLAYER);
//                conditionsHandler.teams().getPlayerTeam().add(testChar2);
//                rootGroup.addActor(testChar2);
//                testChar2.setCannotMove();
            }

            @Override
            protected void setUpTiles() {
                super.setUpTiles();
                setLogicalTileToType(20,45, LogicalTileType.OBJECTIVE_ESCAPE);
                setLogicalTileToType(23,9, LogicalTileType.OBJECTIVE_ESCAPE);
            }

        };
    }

    @Override
    protected void buildConversations() {
        // build cutscene listeners here then add

        Array<ConversationTrigger> array = new Array<>();

        // first build the conversations by adding prefab script
        // then build trigger metadata; roster list, vector list
        // then build triggers with conversations and metadata
        // then add triggers to handlers

        // You could also build the conversations here directly,
        // but that seems a little messy.
        // Let's do it here anyway to prove the point.
        // Maybe it's fine for one or two line cutscenes, I only worry
        // about it becoming confusing what is stored where for future
        // editing. (Spaghetti code.)

        TurnTrigger triggerLeifNeedEscape = new TurnTrigger(new DialogScript() {
            @Override
            public void setSeries() {
                choreographLinger();
                set(CharacterExpression.LEIF_WINCING, "I've got to get out of here...");
            }
        }, 1);
        array.add(triggerLeifNeedEscape);

        CombatTrigger triggerLeifMeAlone = new CombatTrigger(EnumSet.of(UnitRoster.LEIF), new DScript_1A_Leif_LeaveMeAlone(game), CombatTrigger.When.AFTER);
        array.add(triggerLeifMeAlone);

        Set<Vector2> triggerTilesAntalHelpMe = Set.of(
            new Vector2(28, 30), // [ROW][COLUMN] (x/y reversed)
            new Vector2(28, 29),
            new Vector2(27, 30),
            new Vector2(27, 29)
        );

        AreaTrigger triggerAntalHelpMe = new AreaTrigger(EnumSet.of(UnitRoster.LEIF), triggerTilesAntalHelpMe, new DScript_1A_Antal_HelpMe(game));
        array.add(triggerAntalHelpMe);

        conditionsHandler.loadConversations(array);
    }

    @Override
    protected void setUpVictFailCons() {
        // TODO: Account for if player escapes north with Leif instead.
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinate(20, 45);
        leifEscapeVictCon.setObjectiveText("[GREEN]Victory:[] Leif Escapes");
        leifEscapeVictCon.setMoreInfo("Leif can escape to the southeast, safely fleeing the assault.");
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        // optional, Antal escapes through the north tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinate(23, 9);
        antalEscapeVictCon.setAssociatedFlag(CampaignFlags.STAGE_1A_ANTAL_ESCAPED);
        antalEscapeVictCon.setObjectiveText("[ORANGE]Optional:[] Antal Survives and Escapes");
        antalEscapeVictCon.setMoreInfo("The allied ([GREEN]green[]) knight, [GOLD]Antal[], is trying to escape the assault with his life. To survive, he must reach the western road before he is killed by enemy soldiers.");
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);
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
