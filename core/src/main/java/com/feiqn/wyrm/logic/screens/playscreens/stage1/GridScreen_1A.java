package com.feiqn.wyrm.logic.screens.playscreens.stage1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIType;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.*;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.AreaTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.CombatTrigger;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.TurnTrigger;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillWyrMap;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTile;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
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
                placeUnitAtPositionXY(testEnemy, 29, 22);
                conditionsHandler.addToTurnOrder(testEnemy);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
                rootGroup.addActor(testEnemy);
                testEnemy.setCannotMove();

                final SoldierUnit testEnemy2 = new SoldierUnit(game);
                testEnemy2.setColor(Color.RED);
                testEnemy2.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy2.setAIType(AIType.STILL);
                testEnemy2.name = "Evil Tumn";
                placeUnitAtPositionXY(testEnemy2, 13, 23); // TODO: debug values here, X should be 11 when xRayRecursion works
                conditionsHandler.addToTurnOrder(testEnemy2);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy2);
                rootGroup.addActor(testEnemy2);
                testEnemy2.setCannotMove();

                final LeifUnit testChar = new LeifUnit(game);
                placeUnitAtPositionXY(testChar, 30, 28);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

//                final SoldierUnit testChar2 = new SoldierUnit(game);
//                placeUnitAtPositionXY(testChar2, 29, 20);
//                conditionsHandler.addToTurnOrder(testChar2);
//                testChar2.setTeamAlignment(TeamAlignment.PLAYER);
//                conditionsHandler.teams().getPlayerTeam().add(testChar2);
//                rootGroup.addActor(testChar2);
//                testChar2.setCannotMove();
            }

            @Override
            protected void setUpTiles() {
                super.setUpTiles();
                setLogicalTileToTypeXY(45,20, LogicalTileType.OBJECTIVE_ESCAPE);
                setLogicalTileToTypeXY(9,23, LogicalTileType.OBJECTIVE_ESCAPE);
                ((ObjectiveEscapeTile) getTileAtPositionXY(9,23)).setObjectiveUnit(UnitRoster.ANTAL);

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
        // but that gets a little messy.

        TurnTrigger triggerLeifNeedEscape = new TurnTrigger(new DScript_1A_Leif_NeedToEscape(game), 1);
        array.add(triggerLeifNeedEscape);

        CombatTrigger triggerLeifMeAlone = new CombatTrigger(EnumSet.of(UnitRoster.LEIF), new DScript_1A_Leif_LeaveMeAlone(game), CombatTrigger.When.AFTER);
        array.add(triggerLeifMeAlone);

        Set<Vector2> triggerTilesAntalHelpMe = new HashSet<>(Set.of(
            new Vector2(39, 28)
        ));

        for(int x = 39; x < 59; x++) {
            for(int y = 28; y > 0; y--){
                triggerTilesAntalHelpMe.add(new Vector2(x, y));
            }
        }

        AreaTrigger triggerAntalHelpMe = new AreaTrigger(EnumSet.of(UnitRoster.LEIF), triggerTilesAntalHelpMe, new DScript_1A_Antal_HelpMe(game));
        array.add(triggerAntalHelpMe);

        conditionsHandler.loadConversations(array);
    }

    @Override
    protected void setUpVictFailCons() {
        // Mandatory, Leif escapes
        final EscapeOneVictCon leifEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.LEIF, true);
        leifEscapeVictCon.setAssociatedCoordinateXY(45, 20);
        leifEscapeVictCon.setAssociatedFlag(CampaignFlags.STAGE_1A_CLEARED);
        leifEscapeVictCon.setObjectiveText("[GREEN]Victory:[] Leif Escapes");
        leifEscapeVictCon.setMoreInfo("Leif can escape to the southeast, safely fleeing the assault.");
        conditionsHandler.addVictoryCondition(leifEscapeVictCon);

        // optional, Antal escapes through the west tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinateXY(9, 23);
        antalEscapeVictCon.setAssociatedFlag(CampaignFlags.STAGE_1A_ANTAL_ESCAPED);
        antalEscapeVictCon.setObjectiveText("[ORANGE]Optional:[] Antal Survives and Escapes");
        antalEscapeVictCon.setMoreInfo("The allied ([GREEN]green[]) knight, [GOLD]Antal[], is trying to escape the assault with his life. To survive, he must reach the western road before he is killed by enemy soldiers.");
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);
    }

    @Override
    public void stageClear() {
        game.campaignHandler.setFlag(CampaignFlags.STAGE_1A_CLEARED);

        if(conditionsHandler.victoryConditionIsSatisfied(CampaignFlags.STAGE_1A_ANTAL_ESCAPED)) {
            game.campaignHandler.setFlag(CampaignFlags.ANTAL_RECRUITED);
            game.campaignHandler.setFlag(CampaignFlags.STAGE_2A_UNLOCKED);
            Gdx.app.log("stageClear", "antal escaped");

            startConversation(new Conversation(game, new DScript_1A_Leif_SavedAntal(game)));

        } else { // Leif fled without saving Antal
            game.campaignHandler.setFlag(CampaignFlags.STAGE_2B_UNLOCKED);
            game.campaignHandler.setFlag(CampaignFlags.ANTAL_DIED);

            Gdx.app.log("stageClear", "leif fled alone");
            startConversation(new Conversation(game, new DScript_1A_Leif_FledAlone(game)));
        }

    }
}
