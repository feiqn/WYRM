package com.feiqn.wyrm.logic.screens.storyA.stage1;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.post.DScript_1A_POST_LeifAntal_Campfire;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.mapdata.AutoFillOLDWyrMap;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.ally.recruitable.AntalUnitOLD;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;

public class OLDGridScreen_CUTSCENE_Leif_Antal_Campfire extends OLD_GridScreen {

    // After saving Antal in 1A, the pair head west towards shelter.
    // Along the way, they stop to rest for the night.

    public OLDGridScreen_CUTSCENE_Leif_Antal_Campfire(WYRMGame game) {
        super(game);
    }

    @Override
    protected void loadMap() {
        tiledMap = new TmxMapLoader().load("test/maps/roadside_campfire.tmx");
        final MapProperties properties = tiledMap.getProperties();
        logicalMap = new AutoFillOLDWyrMap(game, (int)properties.get("width"), (int)properties.get("height"), tiledMap) {
            @Override
            public void setUpUnits() {
                final LeifUnitOLD testChar = new LeifUnitOLD(game);
                placeUnitAtPositionXY(testChar, 22, 22);
                conditionsHandler.addToTurnOrder(testChar);
                conditionsHandler.teams().getPlayerTeam().add(testChar);
                rootGroup.addActor(testChar);
                testChar.setCannotMove();
                testChar.dismount();

                final AntalUnitOLD antalChar = new AntalUnitOLD(game);
                antalChar.setTeamAlignment(TeamAlignment.PLAYER);
                placeUnitAtPositionXY(antalChar, 20, 20);

                conditionsHandler.teams().getPlayerTeam().add(antalChar);
                conditionsHandler.addToTurnOrder(antalChar);

                rootGroup.addActor(antalChar);
                antalChar.setCannotMove();
            }
        };
    }

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
//        conditionsHandler.teams().setAllyTeamUsed();
        setInputMode(OLD_InputMode.LOCKED);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

    @Override
    public void show() {
        super.show();
        OLDInputMode = OLD_InputMode.LOCKED;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                conditions().conversations().startCutscene(new DScript_1A_POST_LeifAntal_Campfire(game));
            }
        }, 3);
    }

//    @Override
//    protected void buildConversations() {
//        Array<ConversationTrigger> array = new Array<>();
//
//        TurnTrigger trigger = new TurnTrigger(new DScript_1A_POST_Leif_Antal_Campfire(this), 1);
//        array.add(trigger);
//
//        conditionsHandler.loadConversations(array);
//    }


}
