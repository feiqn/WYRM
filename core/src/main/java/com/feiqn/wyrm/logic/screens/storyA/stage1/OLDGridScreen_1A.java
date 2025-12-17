package com.feiqn.wyrm.logic.screens.storyA.stage1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.AIPersonality;
import com.feiqn.wyrm.logic.handlers.cutscene.OLD_CutscenePlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.during.*;
//import com.feiqn.wyrm.logic.handlers.cutscene.triggers.types.AreaTrigger;
//import com.feiqn.wyrm.logic.handlers.cutscene.triggers.types.CombatTrigger;
//import com.feiqn.wyrm.logic.handlers.cutscene.triggers.types.TurnTrigger;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_BallistaObject;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTileOLD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.models.unitdata.units.enemy.generic.SoldierUnitOLD;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_1A extends OLD_GridScreen {

    // Use this as an example / template going forward.

    OLD_SimpleUnit ballistaUnit;
    OLD_SimpleUnit enemyTarget1;
    OLD_SimpleUnit enemyTarget2;

    public OLDGridScreen_1A(WYRMGame game) {
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

        logicalMap = new AutoFillOLDWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {

            @Override
            public void setUpUnits() {
                final SoldierUnitOLD testEnemy = new SoldierUnitOLD(game);
                testEnemy.setColor(Color.RED);
                testEnemy.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy.setAIType(AIPersonality.AGGRESSIVE);
                testEnemy.characterName = "Evil Timn";
                placeUnitAtPositionXY(testEnemy, 29, 22);
                conditionsHandler.addToTurnOrder(testEnemy);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy);
                rootGroup.addActor(testEnemy);
                testEnemy.setCannotMove();

                final OLD_BallistaObject ballista = new OLD_BallistaObject(game);
                placeMapObjectAtPosition(ballista, 35, 27);
                ballistaObjects.add(ballista);
                rootGroup.addActor(ballista);

                ballistaUnit = new SoldierUnitOLD(game) {
//                    @Override
//                    public void kill() {
//                        startCutscene(new CutscenePlayer(game, new DScript_1A_BallistaUnit_Death(game)));
//                        super.kill();
//                    }
                };
                ballistaUnit.setTeamAlignment(TeamAlignment.ALLY);
                ballistaUnit.setName("Danial");
                ballistaUnit.setAIType(AIPersonality.STILL);
                ballistaUnit.setColor(Color.GREEN);
                ballistaUnit.applyDamage(-2);
                placeUnitAtPositionXY(ballistaUnit, 35,27);
                conditionsHandler.addToTurnOrder(ballistaUnit);
                conditionsHandler.teams().getAllyTeam().add(ballistaUnit);
                rootGroup.addActor(ballistaUnit);
                ballistaUnit.setCannotMove();
//                ballista.enterUnit(ballistaUnit);

                enemyTarget1 = new SoldierUnitOLD(game);
                enemyTarget1.setTeamAlignment(TeamAlignment.ENEMY);
                enemyTarget1.setAIType(AIPersonality.STILL);
                enemyTarget1.setColor(Color.RED);
                placeUnitAtPositionXY(enemyTarget1, 18, 21);
                conditionsHandler.addToTurnOrder(enemyTarget1);
                conditionsHandler.teams().getEnemyTeam().add(enemyTarget1);
                rootGroup.addActor(enemyTarget1);
                enemyTarget1.setCannotMove();

                enemyTarget2 = new SoldierUnitOLD(game);
                enemyTarget2.setTeamAlignment(TeamAlignment.ENEMY);
                enemyTarget2.setAIType(AIPersonality.STILL);
                enemyTarget2.setColor(Color.RED);
                placeUnitAtPositionXY(enemyTarget2, 19, 24);
                conditionsHandler.addToTurnOrder(enemyTarget2);
                conditionsHandler.teams().getEnemyTeam().add(enemyTarget2);
                rootGroup.addActor(enemyTarget2);
                enemyTarget2.setCannotMove();

                final SoldierUnitOLD testEnemy2 = new SoldierUnitOLD(game);
                testEnemy2.setColor(Color.RED);
                testEnemy2.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy2.setAIType(AIPersonality.STILL);
                testEnemy2.characterName = "Evil Tumn";
                placeUnitAtPositionXY(testEnemy2, 11, 23);
                conditionsHandler.addToTurnOrder(testEnemy2);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy2);
                rootGroup.addActor(testEnemy2);
                testEnemy2.setCannotMove();

                final SoldierUnitOLD testEnemy3 = new SoldierUnitOLD(game);
                testEnemy3.setColor(Color.RED);
                testEnemy3.setTeamAlignment(TeamAlignment.ENEMY);
                testEnemy3.setAIType(AIPersonality.STILL);
                testEnemy3.characterName = "Evil Tamn";
                placeUnitAtPositionXY(testEnemy3, 15, 25);
                conditionsHandler.addToTurnOrder(testEnemy3);
                conditionsHandler.teams().getEnemyTeam().add(testEnemy3);
                rootGroup.addActor(testEnemy3);
                testEnemy3.setCannotMove();

                final LeifUnitOLD testChar = new LeifUnitOLD(game);
                placeUnitAtPositionXY(testChar, 30, 28);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

            }

            @Override
            protected void setUpTiles() {
                super.setUpTiles();
                setLogicalTileToTypeXY(45,20, LogicalTileType.OBJECTIVE_ESCAPE);
                setLogicalTileToTypeXY(9,23, LogicalTileType.OBJECTIVE_ESCAPE);
                ((ObjectiveEscapeTileOLD) getTileAtPositionXY(9,23)).setObjectiveUnit(UnitRoster.ANTAL);

            }

        };
    }

    @Override
    protected void declareCutscenes() {
        // TODO: CS for if Leif fuckin dies (Do You Regret Your Decisions?)

        conditions().conversations().addCutscene(new DScript_1A_Leif_NeedToEscape(game));
        conditions().conversations().addCutscene(new DScript_1A_Leif_LeaveMeAlone(game));
        conditions().conversations().addCutscene(new DScript_1A_Leif_IneffectiveAttack(game));

        conditions().conversations().addCutscene(new DScript_1A_Leif_GettingInTheBallista(game));
        conditions().conversations().addCutscene(new DScript_1A_Leif_GotAKillWithTheBallista(game));


        conditions().conversations().addCutscene(new DScript_1A_Antal_HelpMe(game));
        conditions().conversations().addCutscene(new DScript_1A_Antal_EscapingAlive(game));


        conditions().conversations().addCutscene(new DScript_1A_Ballista_1(game));
        conditions().conversations().addCutscene(new DScript_1A_Ballista_2(game));
        conditions().conversations().addCutscene(new DScript_1A_BallistaLoop(game));
        conditions().conversations().addCutscene(new DScript_1A_BallistaUnit_Death(game));
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

        // Optional, Antal escapes through the west tile.
        final EscapeOneVictCon antalEscapeVictCon = new EscapeOneVictCon(game, UnitRoster.ANTAL,false);
        antalEscapeVictCon.setAssociatedCoordinateXY(9, 23);
        antalEscapeVictCon.setAssociatedFlag(CampaignFlags.STAGE_1A_ANTAL_ESCAPED);
        antalEscapeVictCon.setObjectiveText("[ORANGE]Optional:[] Antal Survives and Escapes");
        antalEscapeVictCon.setMoreInfo("The allied ([GREEN]green[]) knight, [GOLD]Antal[], is trying to escape the assault with his life. To survive, he must reach the western road before he is killed by enemy soldiers.");
        conditionsHandler.addVictoryCondition(antalEscapeVictCon);
    }

    @Override
    public void stageClear() {
        WYRMGame.campaign().setCampaignFlag(CampaignFlags.STAGE_1A_CLEARED);

        // TODO: switch based on whether Leif fled west vs east after
        //  helping Antal escape. East leads to ShouldFindAntal CS
        //  screen, west leads directly to FoundAntal CS screen.

        if(conditionsHandler.victoryConditionIsSatisfied(CampaignFlags.STAGE_1A_ANTAL_ESCAPED)) {
            WYRMGame.campaign().setCampaignFlag(CampaignFlags.ANTAL_RECRUITED);
            WYRMGame.campaign().setCampaignFlag(CampaignFlags.STAGE_2A_UNLOCKED);
            Gdx.app.log("stageClear", "antal escaped");

            startCutscene(new OLD_CutscenePlayer(game, new DScript_1A_Leif_SavedAntal(game)));

        } else { // Leif fled without saving Antal
            WYRMGame.campaign().setCampaignFlag(CampaignFlags.STAGE_2B_UNLOCKED);
            WYRMGame.campaign().setCampaignFlag(CampaignFlags.ANTAL_DIED);

            Gdx.app.log("stageClear", "leif fled alone");
            startCutscene(new OLD_CutscenePlayer(game, new DScript_1A_Leif_FleeingAlone(game)));
        }

    }
}
